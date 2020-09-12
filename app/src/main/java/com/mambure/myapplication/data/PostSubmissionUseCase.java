package com.mambure.myapplication.data;

import com.mambure.myapplication.data.remote.GoogleDocApi;
import com.mambure.myapplication.models.Submission;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import retrofit2.Response;

public class PostSubmissionUseCase {
    public void setSubmissionListener(SubmissionListener submissionListener) {
        this.submissionListener = submissionListener;
    }

    public void removeListener() {
        submissionListener = null;
    }

    public interface SubmissionListener {
        void onCompleted();

        void onError();
    }

    private SubmissionListener submissionListener;
    private final GoogleDocApi googleDocApi;
    private final ExecutorService executorService;
    private boolean isBusy;

    /**
     * The {@link Inject} annotation tells Hilt how to construct this object
     * In this case Hilt will get the required dependency from
     * {@link com.mambure.myapplication.dependencyinjection.GoogleApiModule}
     * @param googleDocApi
     */
    @Inject
    public PostSubmissionUseCase(GoogleDocApi googleDocApi) {
        this.googleDocApi = googleDocApi;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void submit(Submission submission) {
        if(isBusy) return;
        isBusy = true;
        executorService.submit(() -> {
            try {
                Response<Void> response = googleDocApi.postSubmission(
                        submission.getEmail(),
                        submission.getFirstName(),
                        submission.getLastName(),
                        submission.getRepoUrl()
                ).execute();
                notifySuccess();
            } catch (IOException e) {
                e.printStackTrace();
                notifyError();
            }
        });
    }

    private void notifyError() {
        if(submissionListener != null)
            submissionListener.onError();
        isBusy = false;
    }

    private void notifySuccess() {
        if(submissionListener != null)
            submissionListener.onCompleted();
        isBusy = false;
    }
}
