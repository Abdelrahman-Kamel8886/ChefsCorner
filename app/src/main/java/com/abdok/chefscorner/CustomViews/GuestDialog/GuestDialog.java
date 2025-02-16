package com.abdok.chefscorner.CustomViews.GuestDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.abdok.chefscorner.databinding.DialogGuestBinding;

public class GuestDialog extends DialogFragment {

    private DialogGuestBinding binding;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        binding = DialogGuestBinding.inflate(LayoutInflater.from(getContext()));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        binding.cancelBtn.setOnClickListener(v -> {
            dismiss();
        });

        binding.signUpBtn.setOnClickListener(v -> {
            if (onItemClickListener != null) onItemClickListener.onSignUpClick();
            dismiss();
        });

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


    public interface OnItemClickListener {
        void onSignUpClick();
    }

}
