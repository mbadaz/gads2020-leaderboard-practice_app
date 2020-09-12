package com.mambure.myapplication.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mambure.myapplication.models.HourItem;
import com.mambure.myapplication.models.SkillsIQItem;

@Database(entities = {HourItem.class, SkillsIQItem.class}, version = 2, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract HourItemDao getHourItemDao();

    public abstract SkillIqItemDao getSkillIqItemDao();
}
