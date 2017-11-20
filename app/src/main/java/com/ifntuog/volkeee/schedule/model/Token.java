package com.ifntuog.volkeee.schedule.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by volkeee on 10/29/17.
 */

public class Token implements Serializable {
    private Integer id;
    private String token;
    private Integer userId;
    private Date createdAtDate;
    private Time createdAtTime;
    private Date updatedAtDate;
    private Time updatedAtTime;
    private String deviceManufacturer;
    private String deviceModel;
    private String androidVersion;
    private Boolean validity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public Time getCreatedAtTime() {
        return createdAtTime;
    }

    public void setCreatedAtTime(Time createdAtTime) {
        this.createdAtTime = createdAtTime;
    }

    public Date getUpdatedAtDate() {
        return updatedAtDate;
    }

    public void setUpdatedAtDate(Date updatedAtDate) {
        this.updatedAtDate = updatedAtDate;
    }

    public Time getUpdatedAtTime() {
        return updatedAtTime;
    }

    public void setUpdatedAtTime(Time updatedAtTime) {
        this.updatedAtTime = updatedAtTime;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public Boolean getValidity() {
        return validity;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                ", createdAtDate=" + createdAtDate +
                ", createdAtTime=" + createdAtTime +
                ", updatedAtDate=" + updatedAtDate +
                ", updatedAtTime=" + updatedAtTime +
                ", deviceManufacturer='" + deviceManufacturer + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                ", validity=" + validity +
                '}';
    }
}
