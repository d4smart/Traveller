package com.d4smart.traveller.pojo;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer userId;

    private Integer guideId;

    private String content;

    private Integer likes;

    private Date createTime;

    private Date updateTime;

    public Comment(Integer id, Integer userId, Integer guideId, String content, Integer likes, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.guideId = guideId;
        this.content = content;
        this.likes = likes;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Comment() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
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