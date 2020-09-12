package com.mambure.myapplication.dependencyinjection;

import com.mambure.myapplication.data.GoogleDocApi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(ActivityRetainedComponent.class)
public abstract class GoogleApiModule {
    @Provides
    static GoogleDocApi providesGoogleDocApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(GoogleDocApi.class);
    }
}
