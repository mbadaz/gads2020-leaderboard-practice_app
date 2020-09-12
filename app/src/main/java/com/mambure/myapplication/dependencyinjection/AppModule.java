package com.mambure.myapplication.dependencyinjection;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mambure.myapplication.data.local.HourItemDao;
import com.mambure.myapplication.data.local.LocalDatabase;
import com.mambure.myapplication.data.local.SkillIqItemDao;
import com.mambure.myapplication.data.remote.RemoteRepositoryApi;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {

    @Provides
    RemoteRepositoryApi providesRemoteRepositoryApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://gadsapi.herokuapp.com")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(RemoteRepositoryApi.class);
    }

    @Provides
    OkHttpClient providesHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    LocalDatabase providesLocalDatabase(Application application) {
        return Room.databaseBuilder(application, LocalDatabase.class, LocalDatabase.class.getName().concat(".db"))
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    SkillIqItemDao providesSkillItemDao(LocalDatabase localDatabase) {
        return localDatabase.getSkillIqItemDao();
    }

    @Provides
    HourItemDao providesHoursItemDao(LocalDatabase localDatabase) {
        return localDatabase.getHourItemDao();
    }

}
