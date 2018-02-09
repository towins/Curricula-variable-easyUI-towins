package org.towins.scss.dao;

import org.apache.ibatis.annotations.Param;
import org.towins.scss.dto.qo.CreditQoForCount;
import org.towins.scss.dto.vo.CourseVoForSelect;
import org.towins.scss.dto.vo.SelectInfoVoForWrite;

import java.util.List;

public interface SelectCourseDao {
    List<CourseVoForSelect> queryForSelect(@Param("cadreId") long cadreId);

    List<CourseVoForSelect> queryForCancel(@Param("cadreId") long cadreId);

    List<CourseVoForSelect> querySelectedCourse(@Param("cadreId") long cadreId);

    void selectCourse(SelectInfoVoForWrite vo);

    void addOneCurrentAmount(@Param("courseId") long courseId);

    void cancelCourse(@Param("cadreId") long cadreId, @Param("courseId") long courseId);

    void subOneCurrentAmount(@Param("courseId") long courseId);

    Long queryPD2XK(@Param("courseId") long courseId);

    Integer queryCreditForCount(CreditQoForCount qo);

    Integer queryCreditForNotBegin(long cadreId);

    List<CourseVoForSelect> findWillTeached();

    List<String> findEmails(@Param("courseId") long courseId);
}
