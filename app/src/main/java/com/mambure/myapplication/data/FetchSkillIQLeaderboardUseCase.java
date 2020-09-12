package com.mambure.myapplication.data;

import android.util.Log;

import com.mambure.myapplication.models.SkillsIQItem;
import com.mambure.myapplication.data.remote.LeaderboardApi;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Response;

public class FetchSkillIQLeaderboardUseCase {
    private static final String TAG = FetchSkillIQLeaderboardUseCase.class.getSimpleName();

    public void setSkillIQListener(SkillIQListener skillIQListener) {
        this.skillIQListener = skillIQListener;
    }

    public interface SkillIQListener {
        void onSkillIqsLeadersFetched(List<SkillsIQItem> skillsIQItems);

        void onError();
    }

    private SkillIQListener skillIQListener;
    private final LeaderboardApi leaderboardApi;
    private final ExecutorService executorService;
    private boolean isBusy;

    // Dependencies injected by Hilt
    @Inject
    public FetchSkillIQLeaderboardUseCase(LeaderboardApi leaderboardApi) {
        this.leaderboardApi = leaderboardApi;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void fetchSkillIqLeaders() {
        if(isBusy) return;

        executorService.submit(() -> {
            try {
                Response<List<SkillsIQItem>> response = leaderboardApi.getSkillIqLeaderboard().execute();
                processSkillIqResponse(response);
            } catch (IOException e) {
                Log.e(TAG, "Error fetching skilliq leaderboard data from network", e);
                notifyError();
            }
        });
    }

    private void processSkillIqResponse(Response<List<SkillsIQItem>> response) {
        List<SkillsIQItem> data = response.body();
        if (response.isSuccessful() && data != null) {
            // Save data to local database
            notifySuccess(data);
        } else {
            notifyError();
        }
    }

    private void notifyError() {
        if (skillIQListener != null) skillIQListener.onError();
        isBusy = false;
    }

    private void notifySuccess(List<SkillsIQItem> data) {
        if(skillIQListener != null) skillIQListener.onSkillIqsLeadersFetched(data);
        isBusy = false;
    }

    public void removeListener() {
        skillIQListener = null;
    }
}
