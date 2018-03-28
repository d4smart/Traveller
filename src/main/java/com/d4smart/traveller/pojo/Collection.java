package com.d4smart.traveller.pojo;

import java.util.Date;

public class Collection {
    private Integer id;

    private Integer userId;

    private Integer guideId;

    private Date createTime;

    private Date updateTime;

    public Collection(Integer id, Integer userId, Integer guideId, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.guideId = guideId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Collection() {
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

    public Integer getGuideId() {
        return guideId;
    }

    public void setGuideId(Integer guideId) {
        this.guideId = guideId;
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