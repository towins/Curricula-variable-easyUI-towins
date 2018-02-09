package org.towins.scss.dto.qo;

import java.util.Date;

public class CreditQoForCount {
    private long cadreId;
    private Date begin;
    private Date end;

    public CreditQoForCount() {
    }

    public long getCadreId() {
        return cadreId;
    }

    public void setCadreId(long cadreId) {
        this.cadreId = cadreId;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "CreditQoForCount{" +
                "cadreId=" + cadreId +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
