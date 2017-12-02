package com.ifntuog.volkeee.schedule.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by volkeee on 10/6/17.
 */

public class User extends BaseModel implements Serializable, BaseModelInterface {
    public static String JSON_KEY_ID = "id",
            JSON_KEY_NAME = "name",
            JSON_KEY_EMAIL = "email";
    private String email;
    private String origin;
    private Token token;

    public User() {
    }

    public User(Integer id, String name, String email) {
        super(id, name);
        this.email = email;
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

    public static ArrayList parseJSON(String data) {
        return null;
    }

    @Override
    public void parseJSON(JSONObject jsonObject) throws JSONException {

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
