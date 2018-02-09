package org.towins.scss.bo;

import org.towins.BaseTest;
import org.towins.dto.Message;
import org.towins.dto.PagedRo;
import org.towins.scss.dto.qo.CourseQoForTeacher;
import org.towins.scss.dto.ro.PagedRoForEasyUI;
import org.towins.scss.dto.vo.CourseForTeacher;
import org.towins.scss.dto.vo.CoursePie;
import org.towins.scss.dto.vo.CourseUpdateForTeacher;
import org.towins.scss.entity.Course;
import org.towins.utils.common.DateUtil;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CourseBoTest extends BaseTest {
    @Resource
    private CourseBo bo;

    @Test
    public void testDoSave() {
        Course course = new Course();
        course.setName("论共产主义实现");
        course.setBeginSelectTime(DateUtil.getCommonTime(2017, 2, 1, 8, 0, 0));
        course.setEndSelectTime(DateUtil.getCommonTime(2017, 2, 10, 17, 0, 0));
        course.setBeginTeachTime(DateUtil.getCommonTime(2017, 2, 15, 10, 0, 0));
        course.setEndTeachTime(DateUtil.getCommonTime(2017, 2, 15, 12, 0, 0));
        course.setClassroom("主楼214");
        course.setCredit(5);
        course.setTeacher("钱小黄");
        course.setIntro("论共产主义实现吗？");
        course.setServiceTeacher("刘丰");
        course.setServiceTeacherTel("1332828889");
        course.setMaxAmount(55);

        Message m = bo.doSave(course);

        assertNotNull(m);
        assertEquals("info", m.getTypeDes());
    }

    @Test
    public void testDoUpdate(){
        CourseUpdateForTeacher course = new CourseUpdateForTeacher();
        course.setId(1);
        course.setName("new Course");
        course.setBeginSelectTime(DateUtil.getCommonTime(2018, 2, 1, 8, 0, 0));
        course.setEndSelectTime(DateUtil.getCommonTime(2018, 2, 10, 17, 0, 0));
        course.setBeginTeachTime(DateUtil.getCommonTime(2018, 2, 15, 10, 0, 0));
        course.setEndTeachTime(DateUtil.getCommonTime(2018, 2, 15, 12, 0, 0));
        course.setClassroom("主楼214");
        course.setCredit(5);
        course.setTeacher("钱小黄");
        course.setIntro("论共产主义实现吗？");
        course.setServiceTeacher("刘丰");
        course.setServiceTeacherTel("1332828889");
        course.setMaxAmount(1000);
        course.setStatus("AC");

        Message m = bo.doUpdate(course);

        assertEquals("info",m.getTypeDes());
        assertEquals("课程修改成功！",m.getMessageText());
    }

    @Test
    public void testQueryForTeacher(){
        Date begin = DateUtil.getCommonTime(2018,1,1);
        Date end = DateUtil.getCommonTime(2018,12,1);
        CourseQoForTeacher qo = new CourseQoForTeacher();
        qo.setBegin(begin);
        qo.setEnd(end);
        qo.setPage(1);

        PagedRoForEasyUI<CourseForTeacher> ro = bo.queryBy(qo);
        assertEquals(false,ro.isEmptyData());
        assertEquals(10,ro.getRows().size());

        ro.getRows().forEach(System.out::println);

        qo.setName("no name");
        ro = bo.queryBy(qo);
        assertEquals(true,ro.isEmptyData());
        assertEquals(0,ro.getRows().size());
    }

    @Test
    public void testQueryForExport(){
        Date begin = DateUtil.getCommonTime(2018,1,1);
        Date end = DateUtil.getCommonTime(2018,12,1);
        CourseQoForTeacher qo = new CourseQoForTeacher();
        qo.setName("");
        qo.setStatus("");
        qo.setBegin(null);
        qo.setEnd(null);

        List<CourseForTeacher> list = bo.queryForExport(qo);
        assertEquals(20,list.size());

        list.forEach(System.out::println);

//        qo.setName("no name");
//        ro = bo.queryBy(qo);
//        assertEquals(true,ro.isEmptyData());
//        assertEquals(0,ro.getRows().size());
    }

    @Test
    public void testPie(){
        CoursePie pie = bo.queryForPie(4);
        System.out.println(pie);
    }
}