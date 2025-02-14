package com.abdok.chefscorner.Ui.Base.Home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdok.chefscorner.Adapters.RecyclerCategoryMealAdapter;
import com.abdok.chefscorner.Adapters.RecyclerRandomAdapter;
import com.abdok.chefscorner.CustomViews.DatePickerBottomSheet;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Data.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

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
        checkForData();
    }

    private void checkForData(){
        if (SharedModel.getRandomMeals()==null||SharedModel.getBreakfastMeals()==null||SharedModel.getDesertMeals()==null){
            presenter.start();
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
        View view = getView();
        if (view == null) {
            view = getActivity().findViewById(android.R.id.content); // Fallback to root view
        }
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        showCustomSnackBar("Internet Connection Lost", R.color.errorRed, Gravity.TOP);
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

    private void onClicks(){
        adapter.setListener(new RecyclerRandomAdapter.onItemClickListener() {
            @Override
            public void onItemClick(MealDTO mealDTO) {
                onRandomItemClick(mealDTO);
            }

            @Override
            public void onAddToPlanClick(MealDTO mealDTO) {
                showDatePicker(mealDTO);
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

    @Override
    public void onStart() {
        super.onStart();
       registerNetworkCallback();
    }

    private void reloadData(){
        showCustomSnackBar("Internet Connection Restored", R.color.successGreen, Gravity.BOTTOM);
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
                    if (internetConnectionLost){

                        reloadData();
                        internetConnectionLost = false;
                    }
                });
            }

            @Override
            public void onLost(@NonNull Network network) {
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

    private void showCustomSnackBar(String message , int colorResId , int gravity){
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT);

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

    @Override
    public void onStop() {
        super.onStop();
        presenter.clearDisposable();
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}