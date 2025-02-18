package com.abdok.chefscorner.Ui.Base.PlanDetails.View;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.abdok.chefscorner.Ui.Adapters.RecyclerIngredientsAdapter;
import com.abdok.chefscorner.Models.IngredientFormatDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Models.PlanMealDto;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.PlanDetails.Presnter.IPlanMDPresenter;
import com.abdok.chefscorner.Ui.Base.PlanDetails.Presnter.PlanMealDetailsPresenter;
import com.abdok.chefscorner.Utils.Helpers.CountryFlagMapper;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.databinding.FragmentMealDetailsBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlanMealDetailsDetailsFragment extends Fragment implements IPlanMDView {

    FragmentMealDetailsBinding binding;
    RecyclerIngredientsAdapter adapter;
    IBaseView baseView;
    IPlanMDPresenter presenter;

    PlanMealDto planMeal;


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
        presenter = new PlanMealDetailsPresenter(this);
        planMeal = PlanMealDetailsDetailsFragmentArgs.fromBundle(getArguments()).getPlanMeal();
        initView(planMeal);
    }

    private void initView(PlanMealDto planMeal){
        baseView.hideBottomNav();
        binding.saveBtn.setVisibility(View.GONE);
        binding.addtoPlanBtn.setText(R.string.remove_from_your_plan);
        binding.addtoPlanBtn.setTextColor(getContext().getColor(R.color.white));
        binding.addtoPlanBtn.setBackgroundTintList(getContext().getColorStateList(R.color.errorRed));

        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainLayout.setVisibility(View.VISIBLE);
        //init Meal
        MealDTO meal = planMeal.getMeal();
        Glide.with(requireContext())
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.load)
                .error(R.drawable.no_image)
                .into(binding.image);
//        Bitmap bitmap = BitmapHelper.getBitmapFromBase64(meal.getBitmapBase64());
//        if (bitmap != null) {
//            binding.image.setImageBitmap(bitmap);
//        }
//        else{
//            Glide.with(requireContext())
//                    .load(meal.getStrMealThumb())
//                    .placeholder(R.drawable.load)
//                    .error(R.drawable.no_image)
//                    .into(binding.image);
//        }
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
                presenter.removeFromPlan(planMeal);
            }
            else{
                SnackBarHelper.showCustomSnackBar(requireActivity(),getString(R.string.you_cannot_remove_meal_from_plan_without_internet_connection), R.color.errorRed, Gravity.TOP);
            }
        });
        binding.backBtn.setOnClickListener(v -> navigateUp());
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




    @Override
    public void onRemoveFromPlanSuccess(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.successGreen, Gravity.TOP);
        navigateUp();
    }

    @Override
    public void onRemoveFromPlanFailed(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.errorRed, Gravity.TOP);
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
