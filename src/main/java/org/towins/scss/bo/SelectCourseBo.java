package org.towins.scss.bo;

import com.sun.mail.util.MailSSLSocketFactory;
import freemarker.template.Template;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.towins.dao.HibernateDao;
import org.towins.dao.MybatisDao;
import org.towins.dto.Message;
import org.towins.scss.dao.SelectCourseDao;
import org.towins.scss.dto.qo.CreditQoForCount;
import org.towins.scss.dto.vo.CourseVoForSelect;
import org.towins.scss.dto.vo.SelectInfoVoForWrite;
import org.towins.scss.entity.SysParams;
import org.towins.utils.common.DateUtil;
import org.towins.utils.common.NumberUtil;
import org.towins.utils.common.StringUtil;
import org.towins.utils.system.PropertiesFileReader;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.security.GeneralSecurityException;
import java.util.*;

import static org.towins.utils.system.PropertiesFileReader.getValue;

@Service
public class SelectCourseBo {
    @Resource
    private MybatisDao mybatisDao;
    @Resource
    private HibernateDao dao;
    @Resource
    private FreeMarkerConfigurer fmc;

    @Transactional(readOnly = true)
    public List<CourseVoForSelect> queryForSelect(long cadreId) {
        return getSelectCourseDao().queryForSelect(cadreId);
    }

    @Transactional(readOnly = true)
    public List<CourseVoForSelect> queryForCancel(long cadreId) {
        return getSelectCourseDao().queryForCancel(cadreId);
    }

    @Transactional(readOnly = true)
    public List<CourseVoForSelect> querySelectedCourse(long cadreId) {
        return getSelectCourseDao().querySelectedCourse(cadreId);
    }

    @Transactional
    public Message doSelectCourse(SelectInfoVoForWrite vo) {
        long cadreId = vo.getCadreId();
        int maxCredit = dao.loadById(SysParams.class, "MAX_CREDIT").getIntValue();
        if (getCurrentCredit(cadreId) + getNotBeginCredit(cadreId) + vo.getCredit() > maxCredit) {
            return Message.warn("因已经超过学分上限，您暂时不可以选择此课程。");
        }
        try {
            getSelectCourseDao().selectCourse(vo);
            if (vo.getOptType().equals("XK")) {
                getSelectCourseDao().addOneCurrentAmount(vo.getCourseId());
                return Message.info("选课操作成功!");
            } else if (vo.getOptType().equals("PD")) {
                return Message.info("此课程已达到选课人数上限，您目前处理排队状态!");
            } else {
                return Message.warn("未知操作！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Message.error("选课操作失败!");
        }
    }

    @Transactional
    public Message doCancelCourse(long cadreId, long courseId) {
        SelectCourseDao selectCourseDao = getSelectCourseDao();
        try {
            selectCourseDao.cancelCourse(cadreId, courseId);

            Long toXKCadreId = selectCourseDao.queryPD2XK(courseId);
            if (toXKCadreId == null) {
                selectCourseDao.subOneCurrentAmount(courseId);
            } else {
                SelectInfoVoForWrite vo = new SelectInfoVoForWrite();
                vo.setCadreId(toXKCadreId);
                vo.setCourseId(courseId);
                vo.setOptType("XK");
                selectCourseDao.selectCourse(vo);
            }
            return Message.info("退课操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Message.error("退课操作失败!");
        }
    }

    @Transactional(readOnly = true)
    public Message queryCreditForCount(long cadreId) {
        int minCredit = dao.loadById(SysParams.class, "MIN_CREDIT").getIntValue();
        int maxCredit = dao.loadById(SysParams.class, "MAX_CREDIT").getIntValue();
        String beginDate = dao.loadById(SysParams.class, "COUNT_BEGIN_DATE").getValue();
        String endDate = dao.loadById(SysParams.class, "COUNT_END_DATE").getValue();

        int c = getCurrentCredit(cadreId);
        int nc = getNotBeginCredit(cadreId);

        String msg = "目前您已经学习课程的学分总数为：" + c + "分，未学习课程学分为：" + nc + "分<br>在" + beginDate + "至" + endDate + "时间段内，您应该修习的学分为" + minCredit + "~" + maxCredit + "分";
        return Message.info(msg);
    }

    private int getNotBeginCredit(long cadreId) {
        Integer c = getSelectCourseDao().queryCreditForNotBegin(cadreId);
        if (c == null) {
            c = 0;
        }

        return c;
    }

    @Transactional(readOnly = true)
    public void sendRemindEmail() {
        SelectCourseDao dao = getSelectCourseDao();
        List<CourseVoForSelect> voList = dao.findWillTeached();
        SimpleEmail email = new SimpleEmail();
        try {
            email.setAuthentication(getValue("system/email", "USERNAME"), getValue("system/email", "PASSWORD"));
            email.setCharset("UTF-8");
            email.setFrom(getValue("system/email", "FROM"));
            email.setSSLOnConnect(true);
            email.setHostName(getValue("system/email", "HOST"));
            email.setSmtpPort(NumberUtil.parseNumber(getValue("system/email", "PORT"), Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (CourseVoForSelect vo : voList) {
            List<String> emails = dao.findEmails(vo.getId());

            try {
                email.addTo(emails.toArray(new String[emails.size()]));
                email.setSubject("<" + vo.getName() + ">开课通知");

                Template template = fmc.getConfiguration().getTemplate("email.ftl");
                Map<String, Object> data = new HashMap<>();
                data.put("name", vo.getName());
                data.put("begin", DateUtil.convertDateToString(vo.getBeginTeachTime(), "MM月dd日HH:mm"));
                data.put("classroom", vo.getClassroom());

                String msg = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
                System.out.println(msg);
                email.setMsg(msg);

                email.send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getCurrentCredit(long cadreId) {
        Date begin = dao.loadById(SysParams.class, "COUNT_BEGIN_DATE").getTimeValue();
        Date end = dao.loadById(SysParams.class, "COUNT_END_DATE").getTimeValue();

        CreditQoForCount qo = new CreditQoForCount();
        qo.setBegin(begin);
        qo.setEnd(end);
        qo.setCadreId(cadreId);

        Integer c = getSelectCourseDao().queryCreditForCount(qo);
        if (c == null) {
            c = 0;
        }

        return c;
    }

    private SelectCourseDao getSelectCourseDao() {
        return mybatisDao.openSession().getMapper(SelectCourseDao.class);
    }
}
