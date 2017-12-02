package com.ifntuog.volkeee.schedule.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by volkeee on 10/10/17.
 */

public class Group extends BaseModel implements Serializable, BaseModelInterface {
    private Boolean hasSubgroups;

    public Group() {
    }

    public Group(Integer id, String name, Boolean has_subgroups) {
        super(id, name);
        this.hasSubgroups = has_subgroups;
    }

    public Boolean getHasSubgroups() {
        return hasSubgroups;
    }

    public void setHasSubgroups(Boolean hasSubgroups) {
        this.hasSubgroups = hasSubgroups;
    }

    public static ArrayList parseJSON(String data) throws JSONException {
        ArrayList<Group> groups = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (Integer i = 0; i < jsonArray.length(); i++) {
            JSONObject groupJson = jsonArray.getJSONObject(i);
            Group group = new Group();
            group.parseJSON(groupJson);

            groups.add(group);
        }

        return groups;
    }

    public void parseJSON(JSONObject jsonObject) throws JSONException {
        this.setId(jsonObject.getInt("id"));
        this.setName(jsonObject.getString("name"));
        this.setHasSubgroups(jsonObject.getBoolean("has_subgroups"));
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + this.id +
                ", name='" + name + '\'' +
                ", hasSubgroups=" + hasSubgroups +
                '}';
    }

    public JSONObject toJson() throws JSONException {
        return new JSONObject()
                .put("id", getId())
                .put("name", getName());
    }
}
