package org.towins.scss.action;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.forten.dto.Message;
import org.forten.utils.common.*;
import org.forten.utils.common.DateUtil;
import org.forten.utils.system.ValidateException;
import org.towins.scss.bo.CourseBo;
import org.towins.scss.dto.qo.CourseQoForTeacher;
import org.towins.scss.dto.ro.PagedRoForEasyUI;
import org.towins.scss.dto.vo.*;
import org.towins.scss.entity.Course;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CourseAction {
    @Resource
    private CourseBo bo;

    @PostMapping("/course")
    public Message save(@RequestBody Course course) {
        try {
            return bo.doSave(course);
        } catch (ValidateException e) {
            return Message.error(StringUtil.join(e.getMessages(), "<br>"));
        }
    }

    @PutMapping("/course")
    public Message update(@RequestBody CourseUpdateForTeacher vo) {
        try {
            return bo.doUpdate(vo);
        } catch (ValidateException e) {
            return Message.error(StringUtil.join(e.getMessages(), "<br>"));
        }
    }

    @GetMapping("/course/finished")
    public List<CourseForTeacher> getFinished() {
        return bo.queryFinised();
    }

    @GetMapping("/course/attendance/{courseId}")
    public List<AttendanceVo> getScInfoForAttendance(@PathVariable long courseId) {
        return bo.queryForAttendance(courseId);
    }

    @PostMapping("/course/query")
    public PagedRoForEasyUI<CourseForTeacher> listPage(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String status,
                                                       @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin,
                                                       @RequestParam(defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int rows) {
        CourseQoForTeacher qo = new CourseQoForTeacher();
        qo.setName(name);
        qo.setEnd(end);
        qo.setBegin(begin);
        qo.setStatus(status);
        qo.setPage(page);
        qo.setRows(rows);
        PagedRoForEasyUI<CourseForTeacher> ro = bo.queryBy(qo);
        return ro;
    }

    @PostMapping("/course/export")
    public void exportData(CourseQoForTeacher qo, HttpServletResponse response) {
        List<CourseForTeacher> list = bo.queryForExport(qo);
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("课程信息");

            Row head = sheet.createRow(0);
            Cell c1 = head.createCell(0);
            Cell c2 = head.createCell(1);
            Cell c3 = head.createCell(2);
            Cell c4 = head.createCell(3);
            Cell c5 = head.createCell(4);
            Cell c6 = head.createCell(5);
            Cell c7 = head.createCell(6);
            Cell c8 = head.createCell(7);
            Cell c9 = head.createCell(8);
            Cell c10 = head.createCell(9);
            Cell c11 = head.createCell(10);
            Cell c12 = head.createCell(11);
            Cell c13 = head.createCell(12);
            Cell c14 = head.createCell(13);
            Cell c15 = head.createCell(14);

            // 向表头行中的六个单元格加入文本
            c1.setCellValue("ID");
            c2.setCellValue("名称");
            c3.setCellValue("讲师");
            c4.setCellValue("教室");
            c5.setCellValue("班主任");
            c6.setCellValue("班主任电话");
            c7.setCellValue("开始上课时间");
            c8.setCellValue("结束上课时间");
            c9.setCellValue("开始选课时间");
            c10.setCellValue("结束选课时间");
            c11.setCellValue("学分");
            c12.setCellValue("课程介绍");
            c13.setCellValue("容量");
            c14.setCellValue("当前已选人数");
            c15.setCellValue("状态");

            for (int i = 0; i < list.size(); i++) {
                CourseForTeacher c = list.get(i);
                Row dataRow = sheet.createRow(i + 1);

                dataRow.createCell(0).setCellValue(c.getId());
                dataRow.createCell(1).setCellValue(c.getName());
                dataRow.createCell(2).setCellValue(c.getTeacher());
                dataRow.createCell(3).setCellValue(c.getClassroom());
                dataRow.createCell(4).setCellValue(c.getServiceTeacher());
                dataRow.createCell(5).setCellValue(c.getServiceTeacherTel());
                dataRow.createCell(6).setCellValue(c.getBeginTeachTimeStr());
                dataRow.createCell(7).setCellValue(c.getEndTeachTimeStr());
                dataRow.createCell(8).setCellValue(c.getBeginSelectTimeStr());
                dataRow.createCell(9).setCellValue(c.getEndSelectTimeStr());
                dataRow.createCell(10).setCellValue(c.getCredit());
                dataRow.createCell(11).setCellValue(c.getIntro());
                dataRow.createCell(12).setCellValue(c.getMaxAmount());
                dataRow.createCell(13).setCellValue(c.getCurrentAmount());
                dataRow.createCell(14).setCellValue(c.getStatus());
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=course-list.xlsx");
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/course/exportNameList")
    public void exportNameList(CourseVoForNameList vo, HttpServletResponse response) {
        List<NameListVo> list = bo.queryNameList(vo.getId());
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("学员名单");

            sheet.createRow(0).createCell(0).setCellValue(vo.getName());
            Row second = sheet.createRow(1);
            second.createCell(0).setCellValue("讲师：" + vo.getTeacher());
            second.createCell(1).setCellValue("上课时间" + vo.getBeginTeachTime());
            second.createCell(2).setCellValue("学分" + vo.getCredit());

            for (int i = 0; i < list.size(); i++) {
                NameListVo nameVo = list.get(i);
                Row dataRow = sheet.createRow(i + 2);
                dataRow.createCell(0).setCellValue(nameVo.getEmpCard());
                dataRow.createCell(1).setCellValue(nameVo.getName());
                dataRow.createCell(2).setCellValue(nameVo.getTel());
            }

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=name-list.xlsx");
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/course/importData")
    public void importData(MultipartFile file) {
        List<Course> list = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                String beginTeachTime = row.getCell(5).getStringCellValue();
                String endTeachTime = row.getCell(6).getStringCellValue();
                String beginSelectTime = row.getCell(7).getStringCellValue();
                String endSelectTime = row.getCell(8).getStringCellValue();

                Course c = new Course();
                c.setName(row.getCell(0).getStringCellValue());
                c.setTeacher(row.getCell(1).getStringCellValue());
                c.setClassroom(row.getCell(2).getStringCellValue());
                c.setServiceTeacher(row.getCell(3).getStringCellValue());
                c.setServiceTeacherTel(row.getCell(4).getStringCellValue());
                c.setBeginTeachTime(DateUtil.convertStringToDate(beginTeachTime, "yyyy-MM-dd HH:mm"));
                c.setEndTeachTime(DateUtil.convertStringToDate(endTeachTime, "yyyy-MM-dd HH:mm"));
                c.setBeginSelectTime(DateUtil.convertStringToDate(beginSelectTime, "yyyy-MM-dd HH:mm"));
                c.setEndSelectTime(DateUtil.convertStringToDate(endSelectTime, "yyyy-MM-dd HH:mm"));
                c.setCredit((int) row.getCell(9).getNumericCellValue());
                c.setIntro(row.getCell(10).getStringCellValue());
                c.setMaxAmount((int) row.getCell(11).getNumericCellValue());
                c.setStatus(row.getCell(12).getStringCellValue());

                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bo.doBatchSave(list.toArray(new Course[list.size()]));
    }

    @PutMapping("/course/attendance")
    public Message changeAttendance(@RequestBody AttendanceVo vo){
        try{
            bo.doChangeAttendance(vo);
            return Message.info("考勤维护成功！");
        }catch(Exception e){
            return Message.error("考勤维护失败！");
        }
    }

    @GetMapping("/course/pie/{courseId}")
    public CoursePie getPie(@PathVariable long courseId){
        return bo.queryForPie(courseId);
    }
}
