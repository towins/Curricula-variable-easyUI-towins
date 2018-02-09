package org.towins.scss.dto.vo;

public class NameListVo {
    private String name;
    private String empCard;
    private String tel;

    public NameListVo() {

    }

    public NameListVo(String name, String empCard, String tel) {
        this.name = name;
        this.empCard = empCard;
        this.tel = tel;
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

    @Override
    public String toString() {
        return "NameListVo{" +
                "name='" + name + '\'' +
                ", empCard='" + empCard + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
