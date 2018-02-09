package org.towins.scss.dto.qo;

public class CadreQo {
    private String name;
    private String empCard;

    private int first;
    private int page;
    private int rows;

    public CadreQo() {
        page = 1;
        rows = 10;
    }

    public CadreQo(String name, String empCard, int page, int rows) {
        this.name = name;
        this.empCard = empCard;
        this.page = page;
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getEmpCard() {
        return empCard;
    }

    public void setEmpCard(String empCard) {
        this.empCard = empCard;
    }

    @Override
    public String toString() {
        return "CadreQo{" +
                "name='" + name + '\'' +
                ", empCard='" + empCard + '\'' +
                ", first=" + first +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
