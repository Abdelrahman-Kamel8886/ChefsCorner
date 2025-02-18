package com.abdok.chefscorner.Ui.Base.Profile.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Profile.Presenter.IProfilePresenter;
import com.abdok.chefscorner.Ui.Base.Profile.Presenter.ProfilePresenter;
import com.abdok.chefscorner.Utils.Consts;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentProfileBinding;
import com.bumptech.glide.Glide;


public class ProfileFragment extends Fragment implements IProfileView {

    FragmentProfileBinding binding;
    IBaseView baseView;
    IProfilePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentProfileBinding.bind(view);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.hideBottomNav();
        presenter = new ProfilePresenter(this, getString(R.string.default_web_client_id));
        showUserData(SharedModel.getUser());
        onClicks();
    }



    private void onClicks(){
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).navigateUp());
        binding.logoutBtn.setOnClickListener(v -> presenter.logout());
        binding.shareBtn.setOnClickListener(v -> shareApp() );
        binding.accountSettingsBtn.setOnClickListener(v -> navigateToEditProfile());
        binding.facebookBtn.setOnClickListener(v -> openFacebookApp());
        binding.linkedinBtn.setOnClickListener(v -> openLinkedInProfile());
        binding.githubBtn.setOnClickListener(v -> openGitHubProfile());
        binding.gmailBtn.setOnClickListener(v -> openGmail());
    }


    @Override
    public void showUserData(UserDTO user) {
        binding.userName.setText(user.getName());
        if (user.getPhotoUrl()!=null){
            Glide.with(requireContext()).load(user.getPhotoUrl()).placeholder(R.drawable.user_avatar).into(binding.avatarImg);
            Log.d("TAGURI", "ProfileFragment : "+user.getPhotoUrl());
        }
    }

    @Override
    public void navigateToLogin() {
        NavController navController = NavHostFragment.findNavController(requireActivity().getSupportFragmentManager().findFragmentById(R.id.container));
        navController.navigate(R.id.loginFragment, null, new NavOptions.Builder()
                .setPopUpTo(R.id.baseFragment, true)
                .build());
    }
    @Override
    public void showInformation(String msg){
        SnackBarHelper.showCustomSnackBar(requireActivity(),msg, R.color.successGreen, Gravity.BOTTOM);
    }

    private void shareApp() {
        String appLink = Consts.APP_SHARE_LINK;
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this amazing app: " + appLink);
        shareIntent.setType("text/plain");

        if (shareIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(shareIntent, "Share app via"));
        } else {
            Toast.makeText(requireContext(), "No app available to share", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFacebookApp() {
        String userId = Consts.FACEBOOK_LINK;
        Intent intent;
        try {
            requireActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + userId));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + userId));
        }
        startActivity(intent);
    }

    private void openLinkedInProfile() {
        String profileUrl = Consts.LINKEDIN_LINK;
        Intent intent;
        try {
            requireActivity().getPackageManager().getPackageInfo("com.linkedin.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://in/" + profileUrl.substring(profileUrl.lastIndexOf("/") + 1)));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl));
        }
        startActivity(intent);
    }

    private void openGitHubProfile() {
        String username = Consts.GITHUB_LINK;
        Intent intent;
        try {
            requireActivity().getPackageManager().getPackageInfo("com.github.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("github://user?username=" + username));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + username));
        }
        startActivity(intent);
    }

    private void openGmail() {
        String email = Consts.GMAIL_LINK;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + email));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToEditProfile() {
       Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_editProfileFragment);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}