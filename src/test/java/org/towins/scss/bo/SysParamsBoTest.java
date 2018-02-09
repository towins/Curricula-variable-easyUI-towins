package org.towins.scss.bo;

import org.towins.BaseTest;
import org.towins.scss.entity.SysParams;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class SysParamsBoTest extends BaseTest {
    @Resource
    private SysParamsBo bo;

    @Test
    public void testQueryAll(){
        List<SysParams> list = bo.queryAll();

        list.forEach(System.out::println);
        System.out.println("-------------------------------");
        list = bo.queryAll();
        list.forEach(System.out::println);
    }

    @Test
    public void testGetValue(){
        String value = bo.getValue("COUNT_BEGIN_DATE");
        System.out.println(value);
        System.out.println("-------------------------------");
        value = bo.getValue("COUNT_BEGIN_DATE");
        System.out.println(value);
    }
}