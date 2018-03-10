package org.towins.scss.action;

import org.forten.dto.Message;
import org.towins.scss.bo.SelectCourseBo;
import org.towins.scss.dto.vo.CourseVoForSelect;
import org.towins.scss.dto.vo.SelectInfoVoForWrite;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SelectCourseAction {
    @Resource
    private SelectCourseBo bo;

    @GetMapping("/sc/courseForSelectList")
    public List<CourseVoForSelect> listCourseForSelect(){
        // TODO 干部ID应该从安全上下文中获取
        long cadreId = 1;

        return bo.queryForSelect(cadreId);
    }

    @GetMapping("/sc/courseForCancelList")
    public List<CourseVoForSelect> listCourseForCancel(){
        // TODO 干部ID应该从安全上下文中获取
        long cadreId = 1;

        return bo.queryForCancel(cadreId);
    }

    @GetMapping("/sc/selectedCourse")
    public List<CourseVoForSelect> listSelectedCourse(){
        // TODO 干部ID应该从安全上下文中获取
        long cadreId = 1;

        return bo.querySelectedCourse(cadreId);
    }

    @PostMapping("/sc/select")
    public Message selectCourse(@RequestBody SelectInfoVoForWrite vo){
        // 此时的vo中暂无cadreId，而已经存在courseId和optType
        // TODO 干部ID应该从安全上下文中获取
        long cadreId = 1;

        // 把cadreId填充到vo对象里
        vo.setCadreId(cadreId);

        return bo.doSelectCourse(vo);
    }

    @PutMapping("/sc/cancel/{courseId}")
    public Message cancelCourse(@PathVariable long courseId){
        // TODO 干部ID应该从安全上下文中获取
        long cadreId = 1;

        return bo.doCancelCourse(cadreId,courseId);
    }

    @GetMapping("/sc/credit")
    public Message getCreditMsg(){
        // TODO 干部ID应该从安全上下文中获取
        long cadreId = 1;

        return bo.queryCreditForCount(cadreId);
    }
}
