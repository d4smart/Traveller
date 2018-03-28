package com.d4smart.traveller.pojo;

import java.util.Date;

public class User {
    private Integer id;

    private String username;

    private String password;

    private String gender;

    private Integer phone;

    private String avatar;

    private String signature;

    private Byte canLogin;

    private Byte canPublish;

    private Byte isAdmin;

    private Date createTime;

    private Date updateTime;

    public User(Integer id, String username, String password, String gender, Integer phone, String avatar, String signature, Byte canLogin, Byte canPublish, Byte isAdmin, Date createTime, Date updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.avatar = avatar;
        this.signature = signature;
        this.canLogin = canLogin;
        this.canPublish = canPublish;
        this.isAdmin = isAdmin;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Byte getCanLogin() {
        return canLogin;
    }

    public void setCanLogin(Byte canLogin) {
        this.canLogin = canLogin;
    }

    public Byte getCanPublish() {
        return canPublish;
    }

    public void setCanPublish(Byte canPublish) {
        this.canPublish = canPublish;
    }

    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
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