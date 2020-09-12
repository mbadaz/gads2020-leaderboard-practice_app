package com.mambure.myapplication.dependencyinjection;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;

/**
 * This module provides the {@link OkHttpClient} to all components that
 * declare that declare {@link OkHttpClient} as a dependency. In this case
 * {@link GoogleApiModule#providesGoogleDocApi(OkHttpClient)} and
 * {@link LeaderboardApiModule#providesRemoteRepositoryApi(OkHttpClient)}
 * will be provided the {@link OkHttpClient} by this module
 *
 * The {@link InstallIn(ApplicationComponent)} annotation tells Hilt that
 * that the dependencies provided by a module will have application scope
 * and will provide the same instances of components for as long as
 * the application is running. In this case the same instance of
 * {@link OkHttpClient} will be provided to
 * {@link GoogleApiModule#providesGoogleDocApi(OkHttpClient)} and
 * {@link LeaderboardApiModule#providesRemoteRepositoryApi(OkHttpClient)}
 */

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {

    /**
     * The {@link Provides} annotation declares that the method provides
     * the component specified as the return type. In this case it declaring (to Hilt)
     * that it provides the {@link OkHttpClient} and hilt will use it to cunstruct
     * objects that declare it as a dependency
     * @return
     */
    @Provides
    OkHttpClient providesHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }
}
