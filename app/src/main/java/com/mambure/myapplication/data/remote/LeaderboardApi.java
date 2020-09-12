package com.mambure.myapplication.data.remote;

import com.mambure.myapplication.models.HourItem;
import com.mambure.myapplication.models.SkillsIQItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LeaderboardApi {
    @GET("api/hours")
    Call<List<HourItem>> getHoursLeaderboard();

    @GET("api/skilliq")
    Call<List<SkillsIQItem>> getSkillIqLeaderboard();
}
