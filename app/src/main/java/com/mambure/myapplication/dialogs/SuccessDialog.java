package com.mambure.myapplication.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.mambure.myapplication.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.dialog_success, null);
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

    @OnClick(R.id.btn_ok)
    public void onClick(View view) {
        requireActivity().finish();
    }
}
