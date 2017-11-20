package com.ifntuog.volkeee.schedule.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by volkeee on 10/6/17.
 */

public class User implements Serializable {
    public static String JSON_KEY_ID = "id",
            JSON_KEY_NAME = "name",
            JSON_KEY_EMAIL = "email";
    private Integer id;
    private String name;
    private String email;
    private String origin;
    private Token token;

    public User() {
    }

    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(JSONObject jsonObject) {
        try {
            this.fromJson(jsonObject);
        } catch (JSONException e) {
            Log.e("PARSE_ERROR", e.getMessage());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static User parseJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setId(jsonObject.getInt(JSON_KEY_ID));
        user.setName(jsonObject.getString(JSON_KEY_NAME));
        user.setEmail(jsonObject.getString(JSON_KEY_EMAIL));
        return user;
    }

    private void fromJson(JSONObject jsonObject) throws JSONException {
        this.setId(jsonObject.getInt(JSON_KEY_ID));
        this.setName(jsonObject.getString(JSON_KEY_NAME));
        this.setEmail(jsonObject.getString(JSON_KEY_EMAIL));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", origin='" + origin + '\'' +
                ", token=" + token +
                '}';
    }

    public JSONObject toJson() throws JSONException {
        return new JSONObject().put("name", getName()).put("email", getEmail()).put("origin", getOrigin());
    }
}
