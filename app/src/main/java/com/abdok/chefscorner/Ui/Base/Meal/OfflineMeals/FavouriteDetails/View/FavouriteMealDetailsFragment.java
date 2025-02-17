package com.abdok.chefscorner.Ui.Base.Meal.OfflineMeals.FavouriteDetails.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.abdok.chefscorner.Adapters.RecyclerIngredientsAdapter;
import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.IngredientFormatDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Meal.OfflineMeals.FavouriteDetails.Presenter.FavMealDetailsPresenter;
import com.abdok.chefscorner.Ui.Base.Meal.OfflineMeals.FavouriteDetails.Presenter.IFavMDPresenter;
import com.abdok.chefscorner.Utils.CountryFlagMapper;
import com.abdok.chefscorner.databinding.FragmentMealDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FavouriteMealDetailsFragment extends Fragment implements IFavMDView {

    FragmentMealDetailsBinding binding;
    RecyclerIngredientsAdapter adapter;
    IBaseView baseView;
    FavouriteMealDto favouriteMealDto;
    IFavMDPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        favouriteMealDto = FavouriteMealDetailsFragmentArgs.fromBundle(getArguments()).getFavouriteMeal();
        presenter = new FavMealDetailsPresenter(this);
        initView(favouriteMealDto);
    }

    private void initView(FavouriteMealDto favouriteMealDto){
        baseView.hideBottomNav();
        binding.saveBtn.setVisibility(View.GONE);
        binding.addtoPlanBtn.setText("Remove from your Favourite Meals");
        binding.addtoPlanBtn.setTextColor(getContext().getColor(R.color.white));
        binding.addtoPlanBtn.setBackgroundTintList(getContext().getColorStateList(R.color.errorRed));

        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainLayout.setVisibility(View.VISIBLE);

        //init Meal
        MealDTO meal = favouriteMealDto.getMeal();
        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.load)
                .error(R.drawable.no_image)
                .into(binding.image);

        binding.mealTitle.setText(meal.getStrMeal());
        binding.mealCategory.setText(meal.getStrArea()+" "+ meal.getStrCategory()+" "+ CountryFlagMapper.getFlagEmoji(meal.getStrArea()));
        binding.mealInstructions.setText(meal.getStrInstructions());

        if (isInternetAvailable()){
            loadYouTubeVideo(meal.getStrYoutube());
        }
        showIngredients(meal.getIngredients());
        if (meal.getStrTags()!=null && !meal.getStrTags().isEmpty()){
            showTags(meal.getStrTags());
        }

        onClicks();

    }

    private void showIngredients(ArrayList<IngredientFormatDTO> ingredients){
        int size = ingredients.size();
        String itemText = size>9? size+" item" : size+" items";
        binding.itemsCount.setText(itemText);
        adapter = new RecyclerIngredientsAdapter(ingredients);
        binding.recyclerIngredients.setAdapter(adapter);
        adapter.setListener(name -> {});
    }

    private void showTags(String tags){
        tags = "#"+tags.replace(","," #");
        binding.tagGroup.setVisibility(View.VISIBLE);
        binding.ingredientTags.setText(tags);
    }

    private void loadYouTubeVideo(String youtubeUrl) {
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        binding.webview.setWebChromeClient(new WebChromeClient());

        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getQueryParameter("v");

        if (videoId != null) {
            String iframe = "<iframe width=\"100%\" height=\"100%\" " +
                    "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=0&mute=0\" " +
                    "frameborder=\"0\" allowfullscreen></iframe>";
            binding.webview.loadData(iframe, "text/html", "utf-8");
        } else {
            binding.webview.setVisibility(View.GONE);
            // binding.webview.loadData("<p>Invalid YouTube URL</p>", "text/html", "utf-8");
        }
    }

    private void onClicks(){
        binding.addtoPlanBtn.setOnClickListener(v -> {
            if (isInternetAvailable()){
                presenter.removeFromFav(favouriteMealDto);
            }
            else{
                showCustomSnackBar(getString(R.string.you_cannot_remove_meal_from_favourites_without_internet_connection), R.color.errorRed, Gravity.TOP);
            }
        });
        binding.backBtn.setOnClickListener(v -> navigateUp());
    }

    private void showCustomSnackBar(String message , int colorResId , int gravity){
        try {
            View view = requireActivity().findViewById(android.R.id.content);

            if (view != null){
                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);

                View snackbarView = snackbar.getView();
                int color = ContextCompat.getColor(requireContext(), colorResId);
                snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));

                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                params.gravity = gravity;
                snackbarView.setLayoutParams(params);

                snackbar.show();
            }
            else{
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            Network network = cm.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        }
        return false;
    }


    private void navigateUp(){
        Navigation.findNavController(requireView()).navigateUp();
    }

    @Override
    public void onRemoveFromFavSuccess(String message) {
        showCustomSnackBar(message, R.color.successGreen, Gravity.TOP);
        navigateUp();
    }

    @Override
    public void onRemoveFromFavFailed(String message) {
        showCustomSnackBar(message, R.color.errorRed, Gravity.TOP);
    }
}
