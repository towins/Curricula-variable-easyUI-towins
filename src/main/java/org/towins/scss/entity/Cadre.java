package org.towins.scss.entity;

import org.forten.utils.system.CurrentTimeKeyBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Cadre implements Serializable{
    @Id
    private long id;
    @Column
    @Length(min = 2,max = 30,message = "姓名长度应该在{min}~{max}之间")
    private String name;
    @Column
    private String password;
    @Column(name="emp_card")
    private String empCard;
    @Column
    private String gender;
    @Column
    private String tel;
    @Column
    private String email;

    public Cadre() {
        this.id = CurrentTimeKeyBuilder.getInstance().nextPK();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpCard() {
        return empCard;
    }

    public void setEmpCard(String empCard) {
        this.empCard = empCard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cadre cadre = (Cadre) o;

        return id == cadre.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Cadre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", empCard='" + empCard + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
