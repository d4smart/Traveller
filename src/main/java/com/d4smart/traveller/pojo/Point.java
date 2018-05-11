package com.d4smart.traveller.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Point {
    private Integer id;

    private Integer trailId;

    private Integer sequence;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String description;

    private Date createTime;

    private Date updateTime;

    public Point(Integer id, Integer trailId, Integer sequence, BigDecimal longitude, BigDecimal latitude, String description, Date createTime, Date updateTime) {
        this.id = id;
        this.trailId = trailId;
        this.sequence = sequence;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Point() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrailId() {
        return trailId;
    }

    public void setTrailId(Integer trailId) {
        this.trailId = trailId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
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