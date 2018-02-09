package org.towins.scss.dto.vo;

public class AttendanceVo {
    private long cadreId;
    private long courseId;
    private String cadreName;
    private String empCard;
    private String tel;
    private String attendance;

    public AttendanceVo() {
    }

    public long getCadreId() {
        return cadreId;
    }

    public void setCadreId(long cadreId) {
        this.cadreId = cadreId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCadreName() {
        return cadreName;
    }

    public void setCadreName(String cadreName) {
        this.cadreName = cadreName;
    }

    public String getEmpCard() {
        return empCard;
    }

    public void setEmpCard(String empCard) {
        this.empCard = empCard;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "AttendanceVo{" +
                "cadreId=" + cadreId +
                ", courseId=" + courseId +
                ", cadreName='" + cadreName + '\'' +
                ", empCard='" + empCard + '\'' +
                ", tel='" + tel + '\'' +
                ", attendance='" + attendance + '\'' +
                '}';
    }
}
