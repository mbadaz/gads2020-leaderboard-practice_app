package com.mambure.myapplication.dependencyinjection;

import com.mambure.myapplication.data.remote.LeaderboardApi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(ActivityComponent.class)
public abstract class LeaderboardApiModule {
    @Provides
    static LeaderboardApi providesRemoteRepositoryApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(LeaderboardApi.class);
    }
}
