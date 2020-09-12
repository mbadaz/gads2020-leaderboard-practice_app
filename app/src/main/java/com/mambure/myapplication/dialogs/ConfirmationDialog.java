package com.mambure.myapplication.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.mambure.myapplication.R;
import com.mambure.myapplication.SubmitActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmationDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.dialog1, null);
        ButterKnife.bind(this, view);
        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setCancelable(true)
                .create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.dialog1, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//    }

    @OnClick({R.id.btn_yes, R.id.view_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                ((SubmitActivity)requireActivity()).submitProject();
                dismiss();
                break;
            case R.id.view_cancel:
                dismiss();
                break;
        }
    }
}
