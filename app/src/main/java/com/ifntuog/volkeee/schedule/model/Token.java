package com.ifntuog.volkeee.schedule.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by volkeee on 10/29/17.
 */

public class Token extends BaseModel implements Serializable, BaseModelInterface {
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

    public Token() {
    }

    public Token(Integer id, String token, Integer userId, Date createdAtDate, Time createdAtTime, Date updatedAtDate, Time updatedAtTime, String deviceManufacturer, String deviceModel, String androidVersion, Boolean validity) {
        super(id, null);
        this.token = token;
        this.userId = userId;
        this.createdAtDate = createdAtDate;
        this.createdAtTime = createdAtTime;
        this.updatedAtDate = updatedAtDate;
        this.updatedAtTime = updatedAtTime;
        this.deviceManufacturer = deviceManufacturer;
        this.deviceModel = deviceModel;
        this.androidVersion = androidVersion;
        this.validity = validity;
    }

    public Token(Token token) {
        this.id = token.getId();
        this.token = token.getToken();
        this.userId = token.getUserId();
        this.createdAtDate = token.getCreatedAtDate();
        this.createdAtTime = token.getCreatedAtTime();
        this.updatedAtDate = token.getUpdatedAtDate();
        this.updatedAtTime = token.getUpdatedAtTime();
        this.deviceManufacturer = token.getDeviceManufacturer();
        this.deviceModel = token.getDeviceModel();
        this.androidVersion = token.getAndroidVersion();
        this.validity = token.getValidity();
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

    public static ArrayList parseJSON(String data) throws JSONException {
        return null;
    }

    public void parseJSON(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.token = jsonObject.getString("token");
        this.userId = jsonObject.getInt("user_id");
        this.deviceManufacturer = jsonObject.getString("device_manufacturer");
        this.deviceModel = jsonObject.getString("device_model");
        this.androidVersion = jsonObject.getString("os_version");
        this.validity = jsonObject.getBoolean("validity");
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

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", id)
                .put("token", token)
                .put("user_id", userId)
                .put("device_manufacturer", deviceManufacturer)
                .put("device_model", deviceModel)
                .put("os_version", androidVersion)
                .put("validity", validity);
    }
}
