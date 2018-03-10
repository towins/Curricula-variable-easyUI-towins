package org.towins.scss.bo;

import org.apache.ibatis.session.SqlSession;
import org.forten.dto.Message;
import org.forten.dto.PageInfo;
import org.forten.dto.PagedRo;
import org.forten.utils.system.BeanPropertyUtil;
import org.towins.dao.HibernateDao;
import org.towins.dao.MybatisDao;
import org.towins.scss.dao.CourseDao;
import org.towins.scss.dto.qo.CourseQoForTeacher;
import org.towins.scss.dto.ro.PagedRoForEasyUI;
import org.towins.scss.dto.vo.*;
import org.towins.scss.entity.Course;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@Service("courseBo")
public class CourseBo {
    @Resource
    private HibernateDao dao;
    @Resource
    private MybatisDao mybatisDao;

    @Transactional
    @Valid
    public Message doSave(Course course) {
        try {
            dao.save(course);
            return Message.info("课程保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Message.error("课程保存失败！");
        }
    }

    @Transactional
    @Valid
    public Message doUpdate(CourseUpdateForTeacher vo) {
        try {
            Course course = dao.loadById(Course.class, vo.getId());
            BeanPropertyUtil.copy(course, vo);

            return Message.info("课程修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Message.error("课程修改失败！");
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "testCache", key = "'courseBo.queryBy'")
    public PagedRoForEasyUI<CourseForTeacher> queryBy(CourseQoForTeacher qo) {
        CourseDao courseDao = getCourseDao();

        long count = courseDao.queryCountForTeacher(qo);
        if (count == 0) {
            return new PagedRoForEasyUI(new PagedRo());
        }

        PageInfo page = PageInfo.getInstance(qo.getPage(), qo.getRows(), (int) count);

        qo.setFirst(page.getFirst());

        List<CourseForTeacher> list = courseDao.queryForTeacher(qo);

        return new PagedRoForEasyUI<>(new PagedRo<>(list, page));
    }

    @Transactional(readOnly = true)
    public List<CourseForTeacher> queryForExport(CourseQoForTeacher qo) {
        List<CourseForTeacher> list = getCourseDao().queryForExport(qo);
        return list;
    }

    @Transactional
    public void doBatchSave(Course... courses) {
        dao.save(courses);
    }

    @Transactional(readOnly = true)
    public List<NameListVo> queryNameList(long courseId) {
        return getCourseDao().queryNameList(courseId);
    }

    @Transactional(readOnly = true)
    public List<CourseForTeacher> queryFinised() {
        return getCourseDao().queryFinished();
    }

    @Transactional(readOnly = true)
    public List<AttendanceVo> queryForAttendance(long coruseId) {
        return getCourseDao().queryForAttendance(coruseId);
    }

    @Transactional
    public void doChangeAttendance(AttendanceVo vo) {
        getCourseDao().changeAttendance(vo);
    }

    @Transactional(readOnly = true)
    public CoursePie queryForPie(long courseId) {
        String hql = "SELECT name FROM Course WHERE id=:cId";
        Map<String, Object> params = new HashMap<>();
        params.put("cId",courseId);

        String name = dao.findOneBy(hql, params);

        Set<ScInfoPie> set = getCourseDao().findForPie(courseId);

        return new CoursePie(name, set);
    }

    private CourseDao getCourseDao() {
        SqlSession session = mybatisDao.openSession();
        return session.getMapper(CourseDao.class);
    }
}