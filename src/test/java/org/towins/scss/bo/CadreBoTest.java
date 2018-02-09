package org.towins.scss.bo;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.towins.BaseTest;
import org.towins.dto.Message;
import org.towins.scss.dto.qo.CadreQo;
import org.towins.scss.dto.ro.PagedRoForEasyUI;
import org.towins.scss.dto.vo.CadreVo;
import org.towins.scss.entity.Cadre;
import org.junit.Test;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;

public class CadreBoTest extends BaseTest{
    @Resource
    private CadreBo bo;

    @Test(expected = ConstraintViolationException.class)
    public void testDoSave(){
        Cadre c = new Cadre();
        c.setName("test");
        c.setPassword("1");
        c.setEmail("email");
        c.setEmpCard("321654987");
        c.setGender("女");
        c.setTel("1332223231");

        Message m = bo.doSave(c);
        assertNotNull(m);
        assertEquals("info",m.getTypeDes());

        c.setName("");
        m = bo.doSave(c);
        assertEquals("error",m.getTypeDes());
    }

    @Test
    public void testQueryBy(){
        CadreQo qo = new CadreQo();
        PagedRoForEasyUI<CadreVo> ro = bo.queryBy(qo);
        assertNotNull(ro);
        assertEquals(10,ro.getRows().size());
        ro.getRows().forEach(System.out::println);

        qo.setPage(2);
        ro = bo.queryBy(qo);
        assertNotNull(ro);
        assertEquals(5,ro.getRows().size());
        ro.getRows().forEach(System.out::println);

        qo = new CadreQo();
        qo.setName("刘");
        ro = bo.queryBy(qo);
        assertNotNull(ro);
        assertEquals(3,ro.getRows().size());
        ro.getRows().forEach(System.out::println);

        qo = new CadreQo();
        qo.setEmpCard("112");
        ro = bo.queryBy(qo);
        assertNotNull(ro);
        assertEquals(5,ro.getRows().size());
        ro.getRows().forEach(System.out::println);
    }
}