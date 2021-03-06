package com.mambure.myapplication.dependencyinjection;

import com.mambure.myapplication.data.remote.GoogleDocApi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Modules installed in the {@link ActivityRetainedComponent} will
 * Will be scoped to the host Activity and will provide the same
 * instances of the dependencies for as long as the Activity lives
 */
@Module
@InstallIn(ActivityComponent.class)
public abstract class GoogleApiModule {

    @Provides
    static GoogleDocApi providesGoogleDocApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(GoogleDocApi.class);
    }
}
