package com.abdok.chefscorner.Ui.Base.MealDetails.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;

import com.abdok.chefscorner.Ui.Adapters.RecyclerIngredientsAdapter;
import com.abdok.chefscorner.Ui.Base.CustomViews.DateSheet.DatePickerBottomSheet;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Models.IngredientFormatDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.MealDetails.Presenter.IMealDetailsPresenter;
import com.abdok.chefscorner.Ui.Base.MealDetails.Presenter.MealDetailsPresenter;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Utils.Helpers.CountryFlagMapper;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentMealDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class MealDetailsFragment extends Fragment implements IMealDetailsView {

    FragmentMealDetailsBinding binding;
    IMealDetailsPresenter presenter;
    IBaseView baseView;

    Boolean isFav;

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
        if (SharedModel.getUser()!=null){
            presenter.checkIfMealIsFav(mealDTO);
        }
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
        onClicks(mealDTO);
    }

    @Override
    public void showInformation(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onAddedToFavSuccess(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.successGreen, Gravity.TOP);
        isFav = true;
        binding.saveBtn.setImageResource(R.drawable.baseline_bookmark_remove_24);
    }

    @Override
    public void onRemovedFromFavSuccess(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.successGreen, Gravity.TOP);
        isFav = false;
        binding.saveBtn.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
    }

    @Override
    public void showError(String message) {
        Log.e("TAGError", "showError: "+message);
        SnackBarHelper.showCustomSnackBar(requireActivity(),getString(R.string.an_error_occurred_check_your_internet_connection), R.color.errorRed, Gravity.TOP);
    }

    @Override
    public void toggleFavBtn(Boolean isExists) {
        isFav = isExists;
        if (isExists){
            binding.saveBtn.setImageResource(R.drawable.baseline_bookmark_remove_24);
        }
        else {
            binding.saveBtn.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
        }
    }

    @Override
    public void onAddedToPlanSuccess(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.successGreen, Gravity.TOP);
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

    private void onClicks(MealDTO mealDTO){
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.saveBtn.setOnClickListener(v -> {
            if (isInternetAvailable()){
                if (isFav){
                    Log.e("TAGError", "onClicks: add "+isFav );
                    presenter.removeFromFav(mealDTO);
                }
                else {
                    Log.e("TAGError", "onClicks: add "+isFav );
                    presenter.addToFav(mealDTO);
                }
            }
            else{
                showError("no internet");
            }
        });

        binding.addtoPlanBtn.setOnClickListener(v -> {
            if(isInternetAvailable()){
                showDatePicker(mealDTO);
            }
            else{
                showError("no internet");
            }
        });
    }

    private void showMainView(){
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainLayout.setVisibility(View.VISIBLE);
        if (SharedModel.getUser()==null){
            showGuestView();
        }
    }

    private void showGuestView() {
        binding.addtoPlanBtn.setVisibility(View.GONE);
        binding.bottomLayout.setVisibility(View.GONE);
        binding.saveBtn.setVisibility(View.GONE);
    }


    private void navigateToIngredients(String name){
        Navigation.findNavController(requireView()).navigate(MealDetailsFragmentDirections.actionMealDetailsFragmentToAllMealsFragment(name, SearchTypeEnum.INGREDIENT));
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

    private void showDatePicker(MealDTO meal){
        DatePickerBottomSheet datePickerBottomSheet = new DatePickerBottomSheet();
        datePickerBottomSheet.show(getChildFragmentManager(),datePickerBottomSheet.getTag());
        datePickerBottomSheet.setOnDateSelectedListener(date -> {
            presenter.addMealToPlan(meal,date);
        });
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