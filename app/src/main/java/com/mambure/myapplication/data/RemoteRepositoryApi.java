package com.mambure.myapplication.data;

import com.mambure.myapplication.data.models.HourItem;
import com.mambure.myapplication.data.models.SkillsIQItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RemoteRepositoryApi {
    @GET("/api/hours")
    Call<HourItem> getHoursLeaderboard();

    @GET("/api/skilliq")
    Call<SkillsIQItem> getSkillIqLeaderboard();
}
