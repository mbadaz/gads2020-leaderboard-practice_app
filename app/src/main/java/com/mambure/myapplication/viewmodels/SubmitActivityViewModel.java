package com.mambure.myapplication.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.mambure.myapplication.data.PostSubmissionUseCase;
import com.mambure.myapplication.data.PostSubmissionUseCase.SubmissionListener;
import com.mambure.myapplication.models.Submission;

public class SubmitActivityViewModel extends ViewModel implements LifecycleObserver {

    private final PostSubmissionUseCase postSubmissionUseCase;
    private final MutableLiveData<Boolean> updateLiveData = new MutableLiveData<>();

    @ViewModelInject
    public SubmitActivityViewModel(PostSubmissionUseCase postSubmissionUseCase) {
        this.postSubmissionUseCase = postSubmissionUseCase;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void executeOnResume() {
        postSubmissionUseCase.setSubmissionListener(submissionListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void executeOnPause() {
        postSubmissionUseCase.removeListener();
    }

    private final SubmissionListener submissionListener = new SubmissionListener() {
        @Override
        public void onCompleted() {
            updateLiveData.postValue(true);
        }

        @Override
        public void onError() {
            updateLiveData.postValue(false);
        }
    };

    public MutableLiveData<Boolean> getUpdateLiveData() {
        return updateLiveData;
    }

    public void removeLiveDataObservers(LifecycleOwner lifecycleOwner) {
        updateLiveData.removeObservers(lifecycleOwner);
    }

    public void submit(Submission submission) {
        postSubmissionUseCase.submit(submission);
    }
}
