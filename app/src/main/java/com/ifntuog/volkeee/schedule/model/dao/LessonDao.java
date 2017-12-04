package com.ifntuog.volkeee.schedule.model.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ifntuog.volkeee.schedule.model.Lesson;

import java.util.ArrayList;

/**
 * Created by volkeee on 12/4/17.
 */

public interface LessonDao {
    @Query("SELECT * FROM lesson")
    ArrayList<Lesson> getAll();

    @Query("SELECT * FROM lesson WHERE week = :week")
    ArrayList<Lesson> loadAllForWeek(Integer week);

    @Insert
    void insertAll(Lesson... lessons);

    @Delete
    void delete(Lesson lesson);
}
