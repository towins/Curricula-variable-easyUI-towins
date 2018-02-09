package org.towins.scss.dao;

import org.towins.scss.dto.qo.CourseQoForTeacher;
import org.towins.scss.dto.vo.AttendanceVo;
import org.towins.scss.dto.vo.CourseForTeacher;
import org.towins.scss.dto.vo.NameListVo;
import org.towins.scss.dto.vo.ScInfoPie;

import java.util.List;
import java.util.Set;

public interface CourseDao {
    long queryCountForTeacher(CourseQoForTeacher qo);

    List<CourseForTeacher> queryForTeacher(CourseQoForTeacher qo);

    List<CourseForTeacher> queryFinished();

    List<CourseForTeacher> queryForExport(CourseQoForTeacher qo);

    List<NameListVo> queryNameList(long courseId);

    List<AttendanceVo> queryForAttendance(long courseId);

    void changeAttendance(AttendanceVo vo);

    Set<ScInfoPie> findForPie(long courseId);
}
