package com.mambure.myapplication.data;

import android.util.Log;

import com.mambure.myapplication.data.remote.LeaderboardApi;
import com.mambure.myapplication.models.HourItem;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Response;

public class FetchHoursLeaderboardUseCase {
    private static final String TAG = FetchHoursLeaderboardUseCase.class.getSimpleName();

    public void setHourLeadersListener(HourLeadersListener hourLeadersListener) {
        this.hourLeadersListener = hourLeadersListener;
    }

    public interface HourLeadersListener {
        void onHourLeadersFetched(List<HourItem> hourItems);

        void onError();
    }

    private HourLeadersListener hourLeadersListener;
    private final LeaderboardApi leaderboardApi;
    private final ExecutorService executorService;
    private boolean isBusy;

    // Dependencies injected by Hilt

    /**
     * The {@link Inject} annotation tells Hilt how to construct this object
     * In this case Hilt will get the required dependency from
     * {@link com.mambure.myapplication.dependencyinjection.LeaderboardApiModule}
     * @param leaderboardApi
     */
    @Inject
    public FetchHoursLeaderboardUseCase(LeaderboardApi leaderboardApi) {
        this.leaderboardApi = leaderboardApi;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void fetchHoursLeaders() {
        if (isBusy) return;
        // perform network and disk operations in background
        isBusy = true;
        executorService.submit(() -> {
            try {
                Response<List<HourItem>> response = leaderboardApi.getHoursLeaderboard().execute();
                processHourItemResponse(response);
            } catch (IOException e) {
                Log.e(TAG, "Error fetching hours leaderboard data from network", e);
                notifyError();
            }
        });
    }

    private void processHourItemResponse(Response<List<HourItem>> response) {
        List<HourItem> data = response.body();
        if (response.isSuccessful() && data != null) {
            // Save data to local database
            notifySuccess(data);
        } else {
            notifyError();
        }
    }

    private void notifyError() {
        if (hourLeadersListener != null) hourLeadersListener.onError();
        isBusy = false;
    }

    private void notifySuccess(List<HourItem> data) {
        if(hourLeadersListener != null) hourLeadersListener.onHourLeadersFetched(data);
        isBusy = false;
    }

    public void removeListener() {
        hourLeadersListener = null;
    }
}
