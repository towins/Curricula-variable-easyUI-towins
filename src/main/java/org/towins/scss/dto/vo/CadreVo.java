package org.towins.scss.dto.vo;

import org.hibernate.validator.constraints.Length;

public class CadreVo {
    private long id;
    @Length(min = 2,max = 30,message = "姓名长度应该在{min}~{max}之间")
    private String name;
    private String empCard;
    private String tel;
    private String gender;
    private String email;

    public CadreVo() {
    }

    public CadreVo(long id, String name, String empCard, String tel, String gender, String email) {
        this.id = id;
        this.name = name;
        this.empCard = empCard;
        this.tel = tel;
        this.gender = gender;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdStr() {
        return id + "";
    }

    @Override
    public String toString() {
        return "CadreVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", empCard='" + empCard + '\'' +
                ", tel='" + tel + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
