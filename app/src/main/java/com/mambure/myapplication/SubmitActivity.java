package com.mambure.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.mambure.myapplication.dialogs.ConfirmationDialog;
import com.mambure.myapplication.dialogs.FailedDialog;
import com.mambure.myapplication.dialogs.SuccessDialog;
import com.mambure.myapplication.models.Submission;
import com.mambure.myapplication.viewmodels.SubmitActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SubmitActivity extends AppCompatActivity {

    @BindView(R.id.txtInput_github)
    EditText txtInputGithub;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.txtInpunt_firstName)
    EditText txtInpuntFirstName;
    @BindView(R.id.txtInput_lastName)
    EditText txtInputLastName;
    @BindView(R.id.txtInput_email)
    EditText txtInputEmail;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;

    private SubmitActivityViewModel submitActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        submitActivityViewModel = new ViewModelProvider(this)
                .get(SubmitActivityViewModel.class);
        getLifecycle().addObserver(submitActivityViewModel);
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        if(isInputComplete()) {
            ConfirmationDialog dialog = new ConfirmationDialog();
            dialog.show(getSupportFragmentManager(), null);
            return;
        }

        Toast.makeText(this, "Please fill in all the fields!", Toast.LENGTH_SHORT)
                .show();
    }

    private boolean isInputComplete() {
       return !txtInpuntFirstName.getText().toString().isEmpty() &&
                !txtInputEmail.getText().toString().isEmpty() &&
                !txtInputLastName.getText().toString().isEmpty() &&
                !txtInputGithub.getText().toString().isEmpty();
    }

    public void submitProject() {
        progressBar2.setVisibility(View.VISIBLE);
        Submission submission = new Submission();
        submission.setEmail(txtInputEmail.getText().toString());
        submission.setFirstName(txtInpuntFirstName.getText().toString());
        submission.setLastName(txtInputLastName.getText().toString());
        submission.setRepoUrl(txtInputGithub.getText().toString());
        submitActivityViewModel.submit(submission);
    }

    @Override
    protected void onResume() {
        super.onResume();
        submitActivityViewModel.getUpdateLiveData()
                .observe(this, isSuccessFull -> {
                    if (isSuccessFull) {
                        new SuccessDialog().show(getSupportFragmentManager(), null);
                    } else {
                        new FailedDialog().show(getSupportFragmentManager(), null);
                    }
                    progressBar2.setVisibility(View.GONE);
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        submitActivityViewModel.removeLiveDataObservers(this);
    }
}