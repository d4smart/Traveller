package com.d4smart.traveller.pojo;

import java.util.Date;

public class Message {
    private Integer id;

    private Integer from;

    private Integer to;

    private String content;

    private Boolean read;

    private Date createTime;

    private Date updateTime;

    public Message(Integer id, Integer from, Integer to, String content, Boolean read, Date createTime, Date updateTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.content = content;
        this.read = read;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Message() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
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