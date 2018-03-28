package com.d4smart.traveller.pojo;

import java.util.Date;

public class Image {
    private Integer id;

    private Integer userId;

    private String path;

    private Date createTime;

    private Date updateTime;

    public Image(Integer id, Integer userId, String path, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.path = path;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Image() {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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