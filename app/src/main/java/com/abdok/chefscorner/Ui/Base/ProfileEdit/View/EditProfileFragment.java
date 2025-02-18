package com.abdok.chefscorner.Ui.Base.ProfileEdit.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.ProfileEdit.Presenter.EditProfilePresenter;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.ProfileEdit.Presenter.IEditProfilePresenter;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentEditProfileBinding;
import com.bumptech.glide.Glide;

public class EditProfileFragment extends Fragment implements IEditProfileView {

    IEditProfilePresenter presenter;
    FragmentEditProfileBinding binding;

    boolean isNameChanged = false;
    boolean isPhotoChanged = false;

    private ActivityResultLauncher<String> getContentLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    Uri resultUri;

    IBaseView baseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new EditProfilePresenter(this);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        showUserData(SharedModel.getUser());
        initializeLaunchers();
        onClicks();
    }

    private void initializeLaunchers(){
        getContentLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            resultUri = uri;
                            binding.avatarImg.setImageURI(resultUri);
                            isPhotoChanged = true;
                            checkChanges();
                        }
                    }
                }
        );

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        openGallery();
                    } else {
                        SnackBarHelper.showCustomSnackBar(requireActivity(),getString(R.string.permission_denied), R.color.errorRed, Gravity.BOTTOM);
                    }
                }
        );
    }

    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
        }
    }

    private void openGallery() {
        getContentLauncher.launch("image/*");
    }


    private void showUserData(UserDTO user) {
        binding.editName.setText(user.getName());
        binding.editEmail.setText(user.getEmail());
        if (user.getPhotoUrl()!=null){
            Glide.with(requireContext()).load(user.getPhotoUrl())
                    .placeholder(R.drawable.user_avatar)
                    .into(binding.avatarImg);
            Log.d("TAGURI", "editProfileFragment: "+user.getPhotoUrl());
        }
    }


    private void onClicks(){
        binding.backBtn.setOnClickListener(v -> navigateUp());
        binding.imageBtn.setOnClickListener(v -> grantPermission());
        binding.saveBtn.setOnClickListener(v -> updateProfile());
        binding.editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString();
                if (!name.equals(SharedModel.getUser().getName()) && !name.isEmpty() && !name.equals(null)){
                    isNameChanged = true;
                }else {
                    isNameChanged = false;
                }
                checkChanges();
            }
        });
    }


    private void checkChanges(){
        if (isNameChanged || isPhotoChanged){
            binding.saveBtn.setEnabled(true);
        }else {
            binding.saveBtn.setEnabled(false);
        }
    }

    private void updateProfile(){
        if (isNameChanged && isPhotoChanged) {
            presenter.updateBoth(binding.editName.getText().toString(), resultUri);
        }
        else if (isNameChanged) {
            presenter.updateName(binding.editName.getText().toString());
        }
        else if (isPhotoChanged) {
            presenter.updatePhoto(resultUri);
        }
    }

    @Override
    public void onError(String msg) {

        SnackBarHelper.showCustomSnackBar(requireActivity() ,msg , R.color.errorRed , Gravity.TOP);
    }

    @Override
    public void onSuccess(String msg) {
        SnackBarHelper.showCustomSnackBar(requireActivity() ,msg , R.color.successGreen , Gravity.TOP);
        showUserData(SharedModel.getUser());
        baseView.updateProfile();
    }

    private void navigateUp(){
        Navigation.findNavController(requireView()).navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}