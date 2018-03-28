package com.d4smart.traveller.pojo;

import java.util.Date;

public class Trail {
    private Integer id;

    private Integer userId;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

    public Trail(Integer id, Integer userId, String name, String description, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Trail() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}