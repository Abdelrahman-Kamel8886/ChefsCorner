package com.abdok.chefscorner.Ui.Base.Search.View;

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
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.abdok.chefscorner.Adapters.RecyclerAreaNamesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerCategoriesNamesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerIngredientsNamesAdapter;
import com.abdok.chefscorner.Ui.Base.Search.SearchSheet.View.SearchBottomSheetFragment;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Data.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Search.Presenter.ISearchPresenter;
import com.abdok.chefscorner.Ui.Base.Search.Presenter.SearchPresenter;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentSearchBinding;
import com.google.android.material.snackbar.Snackbar;


public class SearchFragment extends Fragment implements ISearchView {

    FragmentSearchBinding binding;
    RecyclerIngredientsNamesAdapter ingredientsNamesAdapter;
    RecyclerCategoriesNamesAdapter categoriesNamesAdapter;
    RecyclerAreaNamesAdapter areasNamesAdapter;
    GridLayoutManager layoutManager;
    ISearchPresenter presenter;
    IBaseView baseView;

    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    private boolean internetConnectionLost = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSearchBinding.bind(view);
        presenter = new SearchPresenter(this);
        layoutManager= new GridLayoutManager(requireContext(), 2);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.showMainView();

        if (!isInternetAvailable()) {
            showError();
        } else {
            checkForData();
        }
    }

    private void checkForData() {
        if (SharedModel.getIngredientsNamesResponse() == null || SharedModel.getCategoriesNamesResponse() == null || SharedModel.getAreasNamesResponse() == null) {
            binding.loadingLayout.setVisibility(View.VISIBLE);
            presenter.getIngredientsNames();
            presenter.getCategoriesNames();
            presenter.getAreasNames();
        } else {
            initView();
        }
    }

    private void initView() {
        showIngredients(SharedModel.getIngredientsNamesResponse());
        showCategoriesNames(SharedModel.getCategoriesNamesResponse());
        showAreaNames(SharedModel.getAreasNamesResponse());
    }

    @Override
    public void showIngredients(IngredientsNamesResponseDTO ingredientsDTO) {
        ingredientsNamesAdapter = new RecyclerIngredientsNamesAdapter(ingredientsDTO.getMeals().subList(0, 10));
        binding.recyclerIngredients.setAdapter(ingredientsNamesAdapter);
        ingredientsNamesAdapter.setListener(name -> navigateToIngredientsAllMeals(name));
        binding.seeAllIngredients.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(SearchFragmentDirections.actionSearchFragmentToAllIngredientsFragment(ingredientsDTO));
        });
    }

    @Override
    public void showCategoriesNames(CategoriesNamesResponseDTO categoriesDTO) {
        categoriesNamesAdapter = new RecyclerCategoriesNamesAdapter(categoriesDTO.getMeals());
        binding.recyclerCategories.setAdapter(categoriesNamesAdapter);
        categoriesNamesAdapter.setOnItemClickListener(category -> navigateToCategoriesAllMeals(category.getStrCategory()));
    }

    @Override
    public void showAreaNames(AreasNamesResponseDTO areaDTO) {
        areasNamesAdapter = new RecyclerAreaNamesAdapter(areaDTO.getMeals().subList(0, 9));
        binding.recyclerarea.setAdapter(areasNamesAdapter);
        areasNamesAdapter.setListener(areaName -> navigateToAreaMeals(areaName));
        binding.seeAllAreas.setOnClickListener(v -> {
            Navigation.findNavController(v).
                    navigate(SearchFragmentDirections.actionSearchFragmentToAreasFragment(areaDTO));
        });
        showMainView();
        binding.cardSearch.setOnClickListener(v -> showSearchBottomSheet());
    }

    private void showSearchBottomSheet(){
        SearchBottomSheetFragment bottomSheetFragment = new SearchBottomSheetFragment();
        bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
        bottomSheetFragment.setOnItemClickListener(new SearchBottomSheetFragment.OnItemClickListener() {
            @Override
            public void onCategoryClick(String category) {
                navigateToCategoriesAllMeals(category);
            }

            @Override
            public void onAreaClick(String area) {
                navigateToAreaMeals(area);
            }

            @Override
            public void onIngredientClick(String ingredient) {
                navigateToIngredientsAllMeals(ingredient);
            }
        });
    }

    @Override
    public void showError() {
        showCustomSnackBar(getString(R.string.no_internet_connection), R.color.errorRed, Gravity.TOP);
        baseView.showMainView();
        binding.mainView.setVisibility(View.GONE);
        binding.loadingLayout.setVisibility(View.GONE);
        binding.noInternetView.setVisibility(View.VISIBLE);
        internetConnectionLost = true;
    }

    private void showMainView() {
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainView.setVisibility(View.VISIBLE);
    }

    private void reloadData() {
        showCustomSnackBar(getString(R.string.internet_connection_restored), R.color.successGreen, Gravity.BOTTOM);
        binding.noInternetView.setVisibility(View.GONE);
        binding.loadingLayout.setVisibility(View.VISIBLE);
        checkForData();
    }

    private void registerNetworkCallback() {
        connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                requireActivity().runOnUiThread(() -> {
                    if (internetConnectionLost) {

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

    private void showCustomSnackBar(String message, int colorResId, int gravity) {
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


    private void navigateToAreaMeals(String areaName) {
        Navigation
                .findNavController(requireView())
                .navigate(SearchFragmentDirections
                        .actionSearchFragmentToAllMealsFragment(areaName, SearchTypeEnum.AREA));
    }

    private void navigateToIngredientsAllMeals(String ingredientName) {
        Navigation
                .findNavController(requireView())
                .navigate(SearchFragmentDirections
                        .actionSearchFragmentToAllMealsFragment(ingredientName, SearchTypeEnum.INGREDIENT));

    }

    private void navigateToCategoriesAllMeals(String categoryName) {
        Navigation
                .findNavController(requireView())
                .navigate(SearchFragmentDirections.actionSearchFragmentToAllMealsFragment(categoryName, SearchTypeEnum.CATEGORY));
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
    public void onStart() {
        super.onStart();
        registerNetworkCallback();
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
        presenter.onDestroy();
        binding = null;
    }


}