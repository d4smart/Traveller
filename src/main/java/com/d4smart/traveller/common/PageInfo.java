package com.d4smart.traveller.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

/**
 * Created by d4smart on 2018/4/2 9:26
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PageInfo<T> implements Serializable {

    private List<T> list;

    private Integer pageNum;

    private Integer pageSize;

    private Integer totalPage;

    private Integer totalRow;

    public PageInfo(Integer pageNum, Integer pageSize, Integer totalRow) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
        this.totalRow = totalRow;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getTotalRow() {
        return totalRow;
    }
}
