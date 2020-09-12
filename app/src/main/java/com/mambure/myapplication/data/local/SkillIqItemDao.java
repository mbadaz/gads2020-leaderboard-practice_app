package com.mambure.myapplication.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mambure.myapplication.models.SkillsIQItem;

import java.util.List;

@Dao
public interface SkillIqItemDao {
    @Insert
    void saveSkillIqItems(List<SkillsIQItem> skillsIQItems);

    @Query("SELECT * FROM SkillsIQItems")
    LiveData<List<SkillsIQItem>> getAllSkillIqItems();
}
