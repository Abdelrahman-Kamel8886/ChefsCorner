package com.abdok.chefscorner.Ui.Base.Meal.OfflineMeal.View;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abdok.chefscorner.Adapters.RecyclerIngredientsAdapter;
import com.abdok.chefscorner.Data.Models.IngredientFormatDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter.MealDetailsPresenter;
import com.abdok.chefscorner.Utils.CountryFlagMapper;
import com.abdok.chefscorner.Utils.Helpers.BitmapHelper;
import com.abdok.chefscorner.databinding.FragmentMealDetailsBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OfflineMealDetailsFragment extends Fragment implements IOfflineMealView {

    FragmentMealDetailsBinding binding;
    RecyclerIngredientsAdapter adapter;
    IBaseView baseView;


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
        PlanMealDto planMeal = OfflineMealDetailsFragmentArgs.fromBundle(getArguments()).getPlanMeal();
        initView(planMeal);
    }

    private void initView(PlanMealDto planMeal){

        baseView.hideBottomNav();
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainLayout.setVisibility(View.VISIBLE);
        //init Meal
        MealDTO meal = planMeal.getMeal();
        Bitmap bitmap = BitmapHelper.getBitmapFromBase64(meal.getBitmapBase64());
        if (bitmap != null) {
            binding.image.setImageBitmap(bitmap);
        }
        else{
            Glide.with(requireContext())
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.load)
                    .error(R.drawable.no_image)
                    .into(binding.image);
        }
        binding.mealTitle.setText(meal.getStrMeal());
        binding.mealCategory.setText(meal.getStrArea()+" "+ meal.getStrCategory()+" "+ CountryFlagMapper.getFlagEmoji(meal.getStrArea()));
        binding.mealInstructions.setText(meal.getStrInstructions());
        loadYouTubeVideo(meal.getStrYoutube());
        showIngredients(meal.getIngredients());
        if (meal.getStrTags()!=null && !meal.getStrTags().isEmpty()){
            showTags(meal.getStrTags());
        }
    }

    private void showIngredients(ArrayList<IngredientFormatDTO> ingredients){
        int size = ingredients.size();
        String itemText = size>9? size+" item" : size+" items";
        binding.itemsCount.setText(itemText);
        adapter = new RecyclerIngredientsAdapter(ingredients);
        binding.recyclerIngredients.setAdapter(adapter);
        //adapter.setListener(name -> navigateToIngredients(name));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
