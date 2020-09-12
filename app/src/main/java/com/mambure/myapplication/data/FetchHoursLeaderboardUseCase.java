package com.mambure.myapplication.data;

import android.util.Log;

import com.mambure.myapplication.models.HourItem;
import com.mambure.myapplication.data.remote.RemoteRepositoryApi;

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
    private final RemoteRepositoryApi remoteRepositoryApi;
    private final ExecutorService executorService;
    private boolean isBusy;

    // Dependencies injected by Hilt
    @Inject
    public FetchHoursLeaderboardUseCase(RemoteRepositoryApi remoteRepositoryApi) {
        this.remoteRepositoryApi = remoteRepositoryApi;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void fetchHoursLeaders() {
        if (isBusy) return;
        // perform network and disk operations in background
        isBusy = true;
        executorService.submit(() -> {
            try {
                Response<List<HourItem>> response = remoteRepositoryApi.getHoursLeaderboard().execute();
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
