package org.towins.scss.action;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.towins.dto.Message;
import org.towins.scss.bo.CadreBo;
import org.towins.scss.dto.qo.CadreQo;
import org.towins.scss.dto.qo.CourseQoForTeacher;
import org.towins.scss.dto.ro.PagedRoForEasyUI;
import org.towins.scss.dto.vo.CadreVo;
import org.towins.scss.dto.vo.CourseForTeacher;
import org.towins.scss.entity.Cadre;
import org.towins.scss.entity.Course;
import org.towins.utils.collection.CollectionUtil;
import org.towins.utils.common.DateUtil;
import org.towins.utils.common.StringUtil;
import org.towins.utils.security.MD5Util;
import org.towins.utils.system.ValidateException;
import org.towins.utils.system.ValidateUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CadreAction {
    @Resource
    private CadreBo bo;

    @PostMapping("/cadre/query")
    public PagedRoForEasyUI<CadreVo> list(@RequestParam(defaultValue = "") String name,
                                          @RequestParam(defaultValue = "") String empCard,
                                          @RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int rows) {
        CadreQo qo = new CadreQo(name, empCard, page, rows);
        PagedRoForEasyUI<CadreVo> ro = bo.queryBy(qo);

        return ro;
    }

    @PostMapping("/cadre")
    public Message save(@RequestBody Cadre cadre) {
        try {
            return bo.doSave(cadre);
        } catch (ValidateException e) {
            return Message.error(StringUtil.join(e.getMessages(), "<br>"));
        }


    }

    @PutMapping("/cadre")
    public Message save(@RequestBody CadreVo vo) {
        try {
            return bo.doUpdate(vo);
        } catch (ValidateException e) {
            return Message.error(StringUtil.join(e.getMessages(), "<br>"));
        }

    }

    @PostMapping("/cadre/export")
    public void exportData(CadreQo qo, HttpServletResponse response) {
        List<CadreVo> list = bo.queryForExport(qo);

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("干部信息");

            Row head = sheet.createRow(0);
            head.createCell(0).setCellValue("姓名");
            head.createCell(1).setCellValue("性别");
            head.createCell(2).setCellValue("工作证号");
            head.createCell(3).setCellValue("电话");
            head.createCell(4).setCellValue("Email");

            for (int i = 0; i < list.size(); i++) {
                CadreVo c = list.get(i);
                Row dataRow = sheet.createRow(i + 1);

                dataRow.createCell(0).setCellValue(c.getName());
                dataRow.createCell(1).setCellValue(c.getGender());
                dataRow.createCell(2).setCellValue(c.getEmpCard());
                dataRow.createCell(3).setCellValue(c.getTel());
                dataRow.createCell(4).setCellValue(c.getEmail());
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=cadre-list.xlsx");
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/cadre/importData")
    public Message importData(MultipartFile file) {
        List<String> errorMsgs = new ArrayList<>();

        List<Cadre> list = new ArrayList<>();
        try {
            InputStream in = file.getInputStream();
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                // 从excel文件中得到工作证号，以准备进行加密
                String empCard = row.getCell(2).getStringCellValue();

                Cadre c = new Cadre();
                c.setName(row.getCell(0).getStringCellValue());
                c.setGender(row.getCell(1).getStringCellValue());
                c.setEmpCard(empCard);
                c.setTel(row.getCell(3).getNumericCellValue() + "");
                c.setEmail(row.getCell(4).getStringCellValue());

                // 取工作证号的后4位，进行MD5加密
                String pwd = empCard.substring(empCard.length() - 4);
                System.out.println(pwd);

                c.setPassword(MD5Util.encrypt(pwd));

                // 每次校验的消息结果会放到oneObjMsgs中
                List<String> oneObjMsgs = ValidateUtil.validate(c);
                // 如果当前校验结果出现了问题，则会把消息放到总校验失败结果的List中
                // 并且加入其所在excel中的行号
                if (!CollectionUtil.isEmpty(oneObjMsgs)) {
                    for (String msg : oneObjMsgs) {
                        errorMsgs.add("[第" + i + "行]" + msg);
                    }
                }

                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 只有在总的校验失败信息List中没有任何错误消息时，才进行数据的导入
        // 否则会返回校验失败消息的类型为error的Message对象
        if (errorMsgs.size() == 0) {
            bo.doBatchSave(list.toArray(new Cadre[list.size()]));
            return Message.info("数据导入成功！");
        } else {
            return Message.error(StringUtil.join(errorMsgs, "<br>"));
        }
    }
}