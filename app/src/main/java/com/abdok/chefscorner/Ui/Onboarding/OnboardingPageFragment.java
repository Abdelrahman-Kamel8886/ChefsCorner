package com.abdok.chefscorner.Ui.Onboarding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Splash.SplashFragment;
import com.abdok.chefscorner.databinding.FragmentOnboardingPageBinding;

public class OnboardingPageFragment extends Fragment {

    FragmentOnboardingPageBinding binding;

    private static final String ARG_POSITION = "position";

    public static OnboardingPageFragment newInstance(int position) {
        OnboardingPageFragment fragment = new OnboardingPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOnboardingPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        int position = getArguments() != null ? getArguments().getInt(ARG_POSITION) : 0;

        switch (position) {
            case 0:
                showFirstView();
                break;
            case 1:
                showSecondView();
                break;
            case 2:
                showThirdView();
                break;
        }
    }

    private void showFirstView() {

        binding.slideScreenImage.setAnimation(R.raw.board1);

        binding.ind1.setImageResource(R.drawable.selected);
        binding.ind2.setImageResource(R.drawable.unselected);
        binding.ind3.setImageResource(R.drawable.unselected);

        binding.title.setText(R.string.find_your_perfect_recipe);
        binding.desc.setText(R.string.explore_a_vast_collection_of_delicious_recipes_from_quick_meals_to_gourmet_dishes_whatever_you_re_craving_we_ve_got_the_perfect_recipe_for_you);

    }

    private void showSecondView() {
        binding.slideScreenImage.setAnimation(R.raw.board2);
        binding.ind1.setImageResource(R.drawable.unselected);
        binding.ind2.setImageResource(R.drawable.selected);
        binding.ind3.setImageResource(R.drawable.unselected);

        binding.title.setText(R.string.easy_to_follow_instructions);
        binding.desc.setText(R.string.every_recipe_comes_with_clear_step_by_step_instructions_and_ingredients_making_cooking_easy_and_enjoyable_for_everyone_from_beginners_to_chefs);
    }

    private void showThirdView() {
        binding.slideScreenImage.setAnimation(R.raw.board3);
        binding.ind1.setImageResource(R.drawable.unselected);
        binding.ind2.setImageResource(R.drawable.unselected);
        binding.ind3.setImageResource(R.drawable.selected);

        binding.title.setText(R.string.save_your_favorites);
        binding.desc.setText(R.string.bookmark_your_favorite_recipes_create_your_personal_collection_and_access_them_anytime_cooking_has_never_been_this_convenient);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
