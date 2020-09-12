package com.mambure.myapplication.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mambure.myapplication.models.HourItem;

import java.util.List;

@Dao
public interface HourItemDao {
    @Insert
    void saveHourItems(List<HourItem> hourItems);

    @Query("SELECT * FROM HourItems")
    LiveData<List<HourItem>> getAllHourItems();
}
