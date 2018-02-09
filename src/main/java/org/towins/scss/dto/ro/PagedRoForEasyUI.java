package org.towins.scss.dto.ro;

import org.towins.dto.PagedRo;

import java.util.Collection;

public class PagedRoForEasyUI<T> {
    private PagedRo<T> ro;

    public PagedRoForEasyUI(PagedRo<T> ro) {
        this.ro = ro;
    }

    public Collection<T> getRows() {
        return this.ro.getData();
    }

    public long getTotal() {
        return this.ro.getPage().getAmount();
    }

    public boolean isEmptyData() {
        return this.ro.isEmptyData();
    }

    @Override
    public String toString() {
        return "PagedRoForEasyUI{" +
                "ro=" + ro.toString() +
                '}';
    }
}
