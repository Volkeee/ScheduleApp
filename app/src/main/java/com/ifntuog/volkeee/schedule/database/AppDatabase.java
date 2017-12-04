package com.ifntuog.volkeee.schedule.database;

import android.arch.persistence.room.RoomDatabase;

import com.ifntuog.volkeee.schedule.model.dao.LessonDao;

/**
 * Created by volkeee on 12/4/17.
 */

public abstract class AppDatabase extends RoomDatabase {
    public abstract LessonDao lessonDao();
}
