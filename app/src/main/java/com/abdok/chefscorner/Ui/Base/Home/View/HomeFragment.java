package com.abdok.chefscorner.Ui.Base.Home.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
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

import com.abdok.chefscorner.Models.HistoryDTO;
import com.abdok.chefscorner.Ui.Adapters.RecyclerCategoryMealAdapter;
import com.abdok.chefscorner.Ui.Adapters.RecyclerHistoryMealAdapter;
import com.abdok.chefscorner.Ui.Adapters.RecyclerRandomAdapter;
import com.abdok.chefscorner.Ui.Base.CustomViews.DateSheet.DatePickerBottomSheet;
import com.abdok.chefscorner.Ui.Base.CustomViews.GuestDialog.GuestDialog;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Ui.Base.Home.Presenter.HomePresenter;
import com.abdok.chefscorner.Ui.Base.Home.Presenter.IHomePresenter;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment implements IHomeView {

    private FragmentHomeBinding binding;
    private IHomePresenter presenter;
    private RecyclerRandomAdapter adapter;
    private RecyclerCategoryMealAdapter breakfastAdapter , desertAdapter;

    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    private IBaseView baseView;

    private boolean internetConnectionLost = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        presenter = new HomePresenter(this);
        baseView = (IBaseView) getParentFragment().getParentFragment();

        if (!isInternetAvailable()){
            showError();
        }
        else {
            checkForData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        registerNetworkCallback();
    }

    private void checkForData(){
        presenter.getHistoryMeals();
        if (SharedModel.getRandomMeals()==null||SharedModel.getBreakfastMeals()==null||SharedModel.getDesertMeals()==null){
            presenter.start();
            if (SharedModel.getUser()!=null){
                String id = SharedModel.getUser().getId();
                presenter.syncFavouriteMeals(id);
                presenter.syncPlanMeals(id);
            }
        }
        else{
            initView();
        }
    }

    @Override
    public void initView() {
        showRandomMeals(SharedModel.getRandomMeals());
        showBreakFastMeals(SharedModel.getBreakfastMeals());
        showDesertMeals(SharedModel.getDesertMeals());
    }

    @Override
    public void showMessage(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.charcoal, Gravity.BOTTOM);
    }

    @Override
    public void showError() {
        Log.e("HomeTAG", "showError: "+internetConnectionLost);
        SnackBarHelper.showCustomSnackBar(requireActivity(),getString(R.string.no_internet_connection), R.color.errorRed, Gravity.TOP);
        baseView.showMainView();
        binding.mainLayout.setVisibility(View.GONE);
        binding.loadingLayout.setVisibility(View.GONE);
        binding.noInternetView.setVisibility(View.VISIBLE);
        internetConnectionLost = true;
    }

    @Override
    public void showRandomMeals(List<MealDTO> meals) {
        adapter = new RecyclerRandomAdapter(meals);
        binding.randomRecycler.setAdapter(adapter);
        binding.randomRecycler.setAlpha(true);
        binding.randomRecycler.setInfinite(false);
    }

    @Override
    public void showBreakFastMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        breakfastAdapter = new RecyclerCategoryMealAdapter(meals);
        binding.breakFastRecycler.setAdapter(breakfastAdapter);

    }

    @Override
    public void showDesertMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        desertAdapter = new RecyclerCategoryMealAdapter(meals);
        binding.desertsRecycler.setAdapter(desertAdapter);
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainLayout.setVisibility(View.VISIBLE);
        binding.noInternetView.setVisibility(View.GONE);
        if (baseView!=null){
            baseView.showMainView();
            onClicks();
        }
    }

    @Override
    public void showHistoryMeals(List<HistoryDTO> meals) {
        if (meals!=null && meals.size()!=0){
            binding.historyGroup.setVisibility(View.VISIBLE);
            RecyclerHistoryMealAdapter hadapter = new RecyclerHistoryMealAdapter();
            binding.historyRecycler.setAdapter(hadapter);
            hadapter.setMeals(meals);
            hadapter.setOnItemClickListener(meal -> {
                navigateToDetails(Integer.parseInt(meal.getMeal().getIdMeal()),meal.getMeal());
            });
        }
        else{
            binding.historyGroup.setVisibility(View.GONE);
        }
    }

    private void onClicks(){
        adapter.setListener(new RecyclerRandomAdapter.onItemClickListener() {
            @Override
            public void onItemClick(MealDTO mealDTO) {
                onRandomItemClick(mealDTO);
            }

            @Override
            public void onAddToPlanClick(MealDTO mealDTO) {
                if (SharedModel.getUser()==null){
                    showGuestDialog();
                }

                else{
                    showDatePicker(mealDTO);
                }

            }
        });
        breakfastAdapter.setOnItemClickListener(this::onRandomItemClick);
        desertAdapter.setOnItemClickListener(this::onRandomItemClick);
    }

    private void showDatePicker(MealDTO meal){
        DatePickerBottomSheet datePickerBottomSheet = new DatePickerBottomSheet();
        datePickerBottomSheet.show(getChildFragmentManager(),datePickerBottomSheet.getTag());
        datePickerBottomSheet.setOnDateSelectedListener(date -> {
            presenter.addMealToPlan(meal,date);
        });
    }

    private void onRandomItemClick(int id){
        navigateToDetails(id , null);
    }

    private void onRandomItemClick(MealDTO mealDTO){
        int id = Integer.parseInt(mealDTO.getIdMeal());
        navigateToDetails(id,mealDTO);
    }

    private void navigateToDetails(int id , MealDTO mealDTO){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id,mealDTO));
    }
    private void reloadData(){
        Log.e("HomeTAG", "reloadData: "+internetConnectionLost);
        SnackBarHelper.showCustomSnackBar(requireActivity(),getString(R.string.internet_connection_restored), R.color.successGreen, Gravity.BOTTOM);
        binding.noInternetView.setVisibility(View.GONE);
        binding.loadingLayout.setVisibility(View.VISIBLE);
        checkForData();
    }

    private void registerNetworkCallback() {
        connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                requireActivity().runOnUiThread(() ->{
                    Log.e("HomeTAG", "onAvailable: "+internetConnectionLost);
                    if (internetConnectionLost){

                        reloadData();
                        internetConnectionLost = false;
                    }
                });
            }
            @Override
            public void onLost(@NonNull Network network) {
                Log.e("HomeTAG", "onLost: "+internetConnectionLost);
                requireActivity().runOnUiThread(() ->
                {
                    internetConnectionLost = true;
                    showError();
                });
            }
        };

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
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

    private void showGuestDialog() {
        GuestDialog guestDialog = new GuestDialog();
        guestDialog.show(getChildFragmentManager(), "GuestDialog");
        guestDialog.setOnItemClickListener(()->{
            navigateToLogin();
        });

    }

    private void navigateToLogin(){
        NavController navController = NavHostFragment.findNavController(requireActivity().getSupportFragmentManager().findFragmentById(R.id.container));
        navController.navigate(R.id.loginFragment, null, new NavOptions.Builder()
                .setPopUpTo(R.id.baseFragment, true)
                .build());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // presenter.clearDisposable();
        binding = null;
    }
}