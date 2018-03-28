package com.d4smart.traveller.pojo;

import java.util.Date;

public class Guide {
    private Integer id;

    private String title;

    private Integer authorId;

    private String places;

    private String thumbnail;

    private String content;

    private Integer reads;

    private Integer likes;

    private Integer comments;

    private Integer score;

    private Byte isPublished;

    private Date createTime;

    private Date updateTime;

    public Guide(Integer id, String title, Integer authorId, String places, String thumbnail, String content, Integer reads, Integer likes, Integer comments, Integer score, Byte isPublished, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.places = places;
        this.thumbnail = thumbnail;
        this.content = content;
        this.reads = reads;
        this.likes = likes;
        this.comments = comments;
        this.score = score;
        this.isPublished = isPublished;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Guide() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places == null ? null : places.trim();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getReads() {
        return reads;
    }

    public void setReads(Integer reads) {
        this.reads = reads;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Byte getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Byte isPublished) {
        this.isPublished = isPublished;
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