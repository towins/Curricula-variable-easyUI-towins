package org.towins.scss.bo;

import org.towins.BaseTest;
import org.towins.dto.Message;
import org.towins.scss.dto.vo.CourseVoForSelect;
import org.towins.scss.dto.vo.SelectInfoVoForWrite;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class SelectCourseBoTest extends BaseTest{
    @Resource
    private SelectCourseBo bo;

    @Test
    public void testQueryForSelect(){
        List<CourseVoForSelect> list = bo.queryForSelect(1);
        assertNotNull(list);
        assertEquals(5,list.size());

        list.forEach(System.out::println);

        list = bo.queryForSelect(2);
        assertNotNull(list);
        assertEquals(7,list.size());

        list.forEach(System.out::println);
    }

    @Test
    public void testQueryForCancel(){
        List<CourseVoForSelect> list = bo.queryForCancel(1);

        list.forEach(System.out::println);
    }

    @Test
    public void testQueryCreditForCount(){
        Message m = bo.queryCreditForCount(1);
        assertNotNull(m);
        System.out.println(m.getMessageText());
    }

    @Test
    public void testSendRemindEmail(){
        bo.sendRemindEmail();
    }

    @Test
    public void testDoSelectCourse(){
        SelectInfoVoForWrite vo = new SelectInfoVoForWrite(1,20,"XK",3);
        Message m = bo.doSelectCourse(vo);

        assertNotNull(m);
        assertEquals("info",m.getTypeDes());

        vo = new SelectInfoVoForWrite(1,20,"PD",3);
        m = bo.doSelectCourse(vo);
    }
}