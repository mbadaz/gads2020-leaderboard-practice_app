package com.mambure.myapplication.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.mambure.myapplication.data.FetchHoursLeaderboardUseCase;
import com.mambure.myapplication.data.FetchHoursLeaderboardUseCase.HourLeadersListener;
import com.mambure.myapplication.data.FetchSkillIQLeaderboardUseCase;
import com.mambure.myapplication.data.FetchSkillIQLeaderboardUseCase.SkillIQListener;
import com.mambure.myapplication.models.HourItem;
import com.mambure.myapplication.models.SkillsIQItem;

import java.util.List;

/***
 * Implement {@link LifecycleObserver} to observe lifecyle of its owner
 */
public class MainActivityViewModel extends ViewModel implements LifecycleObserver {

    private final FetchHoursLeaderboardUseCase fetchHoursLeaderboardUseCase;
    private final FetchSkillIQLeaderboardUseCase fetchSkillIQLeaderboardUseCase;
    private final MutableLiveData<List<SkillsIQItem>> skillIqLeaderboardLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<HourItem>> hoursLeaderboadLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> messagesLiveData = new MutableLiveData<>();

    // Dependencies injected by Hilt
    @ViewModelInject
    public MainActivityViewModel(
            FetchHoursLeaderboardUseCase fetchHoursLeaderboardUseCase, FetchSkillIQLeaderboardUseCase fetchSkillIQLeaderboardUseCase) {
        this.fetchHoursLeaderboardUseCase = fetchHoursLeaderboardUseCase;
        this.fetchSkillIQLeaderboardUseCase = fetchSkillIQLeaderboardUseCase;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void executeOnResume() {
        fetchHoursLeaderboardUseCase.setHourLeadersListener(hourLeadersListener);
        fetchSkillIQLeaderboardUseCase.setSkillIQListener(skillIQListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void executeOnPause() {
        fetchSkillIQLeaderboardUseCase.removeListener();
        fetchHoursLeaderboardUseCase.removeListener();
    }

    private final HourLeadersListener hourLeadersListener = new HourLeadersListener() {
        @Override
        public void onHourLeadersFetched(List<HourItem> hourItems) {
            hoursLeaderboadLiveData.postValue(hourItems);
        }

        @Override
        public void onError() {
            messagesLiveData.postValue("An error occurred fetching data.");
        }
    };

    private final SkillIQListener skillIQListener = new SkillIQListener() {
        @Override
        public void onSkillIqsLeadersFetched(List<SkillsIQItem> skillsIQItems) {
            skillIqLeaderboardLiveData.postValue(skillsIQItems);
        }

        @Override
        public void onError() {
            messagesLiveData.postValue("An error occurred fetching data.");
        }
    };

    public LiveData<List<SkillsIQItem>> getSkillIqLeaderboardLiveData() {
        fetchSkillIQLeaderboardUseCase.fetchSkillIqLeaders();
        return skillIqLeaderboardLiveData;
    }

    public LiveData<List<HourItem>> getHoursLeaderboardLiveData() {
        fetchHoursLeaderboardUseCase.fetchHoursLeaders();
        return hoursLeaderboadLiveData;
    }

    public MutableLiveData<String> getMessagesLiveData() {
        return messagesLiveData;
    }

    public void removeLiveDataObservers(LifecycleOwner lifecycleOwner) {
        skillIqLeaderboardLiveData.removeObservers(lifecycleOwner);
        hoursLeaderboadLiveData.removeObservers(lifecycleOwner);
        messagesLiveData.removeObservers(lifecycleOwner);
    }
}
