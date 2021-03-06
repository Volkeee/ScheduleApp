package com.ifntuog.volkeee.schedule.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by volkeee on 12/1/17.
 */

@Entity
public class Lesson implements Serializable, BaseModelInterface {
    public static final String KEY_PERIOD = "period";
    public static final String KEY_WEEK = "week";
    public static final String KEY_SUBGROUP = "subgroup";
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_TEACHER = "teacher";
    public static final String KEY_AUDITORY = "room";

    @PrimaryKey
    private String name;

    @ColumnInfo(name = "period")
    private Integer period;

    @ColumnInfo(name = "week")
    private Integer week;

    @ColumnInfo(name = "subgroup")
    private Integer subgroup;

    @ColumnInfo(name ="type")
    private String type;

    @ColumnInfo(name = "teacher")
    private String teacher;

    @ColumnInfo(name = "auditory")
    private String auditory;

    @ColumnInfo(name = "day")
    private Integer day;

    public Lesson() {
    }

    public Lesson(String name, Integer period, Integer week, Integer subgroup, String type, String teacher, String auditory) {
        this.name = name;
        this.period = period;
        this.week = week;
        this.subgroup = subgroup;
        this.type = type;
        this.teacher = teacher;
        this.auditory = auditory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(Integer subgroup) {
        this.subgroup = subgroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public static ArrayList parseJSON(String data) throws JSONException {
        ArrayList<Lesson> lessons = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        for (Integer i = 0; i < jsonArray.length(); i++) {
            JSONObject lessonsJSON = jsonArray.getJSONObject(i);

            JSONArray lessonsArray = lessonsJSON.getJSONArray("lessons");
            for (int iterator = 0; iterator < lessonsArray.length(); iterator++) {
                Lesson lesson = new Lesson();
                lesson.parseJSON(lessonsArray.getJSONObject(iterator));


                lesson.day = lessonsJSON.getInt("day");
                lessons.add(lesson);
            }
        }

        return lessons;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", period=" + period +
                ", week=" + week +
                ", subgroup=" + subgroup +
                ", type='" + type + '\'' +
                ", teacher='" + teacher + '\'' +
                ", auditory='" + auditory + '\'' +
                ", day=" + day +
                '}';
    }

    @Override
    public void parseJSON(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString(KEY_NAME);
        this.period = jsonObject.getInt(KEY_PERIOD);
        this.week = jsonObject.getInt(KEY_WEEK);
        this.subgroup = jsonObject.getInt(KEY_SUBGROUP);
        this.type = jsonObject.getString(KEY_TYPE);
        this.teacher = jsonObject.getString(KEY_TEACHER);
        this.auditory = jsonObject.getString(KEY_AUDITORY);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return new JSONObject().put(KEY_NAME, this.getName())
                .put(KEY_PERIOD, this.getPeriod()).put(KEY_WEEK, this.getWeek())
                .put(KEY_SUBGROUP, this.getSubgroup()).put(KEY_TYPE, this.getType())
                .put(KEY_TEACHER, this.getTeacher()).put(KEY_AUDITORY, this.getAuditory());
    }
}
