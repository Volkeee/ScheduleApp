package com.ifntuog.volkeee.schedule.model;

import java.io.Serializable;

/**
 * Created by volkeee on 12/1/17.
 */

public class BaseModel implements Serializable {
    public Integer id;
    public String name;

    public BaseModel() {
    }

    public BaseModel(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
