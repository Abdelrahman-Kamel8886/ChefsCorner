package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.View;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import com.abdok.chefscorner.Adapters.RecyclerAreaNamesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerIngredientsAdapter;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Models.IngredientFormatDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter.IMealDetailsPresenter;
import com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter.MealDetailsPresenter;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Utils.CountryFlagMapper;
import com.abdok.chefscorner.databinding.FragmentMealDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class MealDetailsFragment extends Fragment implements IMealDetailsView {

    FragmentMealDetailsBinding binding;
    IMealDetailsPresenter presenter;
    IBaseView baseView;

    RecyclerIngredientsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        presenter = new MealDetailsPresenter(this);
        initView();

    }

    private void initView(){
        baseView.hideBottomNav();

        int id = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();
        MealDTO mealDTO = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();

        if (mealDTO!=null){
            InitData(mealDTO);
        }
        else {
            binding.loadingLayout.setVisibility(View.VISIBLE);
            presenter.getMealDetails(id);
        }

    }


    @Override
    public void InitData(MealDTO mealDTO) {
        showMainView();
        Glide.with(requireContext()).load(mealDTO.getStrMealThumb()).placeholder(R.drawable.load).into(binding.image);
        binding.mealTitle.setText(mealDTO.getStrMeal());
        binding.mealCategory.setText(mealDTO.getStrArea()+" "+ mealDTO.getStrCategory()+" "+CountryFlagMapper.getFlagEmoji(mealDTO.getStrArea()));
        binding.mealInstructions.setText(mealDTO.getStrInstructions());
        loadYouTubeVideo(mealDTO.getStrYoutube());
        showIngredients(mealDTO.getIngredients());

        if (mealDTO.getStrTags()!=null && !mealDTO.getStrTags().isEmpty()){
            showTags(mealDTO.getStrTags());
        }
        onClicks();
    }

    @Override
    public void showInformation(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private void showIngredients(ArrayList<IngredientFormatDTO> ingredients){
        int size = ingredients.size();
        String itemText = size>9? size+" item" : size+" items";
        binding.itemsCount.setText(itemText);
        adapter = new RecyclerIngredientsAdapter(ingredients);
        binding.recyclerIngredients.setAdapter(adapter);
        adapter.setListener(name -> navigateToIngredients(name));
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
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
    }

    private void showMainView(){
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainLayout.setVisibility(View.VISIBLE);

    }

    private void navigateToIngredients(String name){
        Navigation.findNavController(requireView()).navigate(MealDetailsFragmentDirections.actionMealDetailsFragmentToAllMealsFragment(name, SearchTypeEnum.INGREDIENT));
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.clearDisposable();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}