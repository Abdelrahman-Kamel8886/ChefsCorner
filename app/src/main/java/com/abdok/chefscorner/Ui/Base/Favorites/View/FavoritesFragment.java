package com.abdok.chefscorner.Ui.Base.Favorites.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Ui.Adapters.RecyclerFavMealAdapter;
import com.abdok.chefscorner.Models.FavouriteMealDto;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.Favorites.Presenter.FavouritesPresenter;
import com.abdok.chefscorner.Ui.Base.Favorites.Presenter.IFavPresenter;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentFavoritesBinding;

import java.util.List;


public class FavoritesFragment extends Fragment implements IFavView {

    FragmentFavoritesBinding binding;
    IBaseView baseView;
    IFavPresenter presenter;
    RecyclerFavMealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFavoritesBinding.bind(view);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.showMainView();
        presenter = new FavouritesPresenter(this);

        if (SharedModel.getUser() == null){
            binding.guestView.setVisibility(View.VISIBLE);
            binding.signUpBtn.setOnClickListener(v -> navigateToLogin());
            binding.title.setVisibility(View.GONE);
        }
        else{
            binding.title.setVisibility(View.VISIBLE);
            binding.guestView.setVisibility(View.GONE);
            presenter.getFavoriteMeals(SharedModel.getUser().getId());
        }

    }

    private void navigateToLogin(){
        NavController navController = NavHostFragment.findNavController(requireActivity().getSupportFragmentManager().findFragmentById(R.id.container));
        navController.navigate(R.id.loginFragment, null, new NavOptions.Builder()
                .setPopUpTo(R.id.baseFragment, true)
                .build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showList(List<FavouriteMealDto> meals) {
        if (meals.size()!=0){
            String count = meals.size()<10? meals.size()+" items" : meals.size()+" item";
            binding.itemsCount.setText(count);
            binding.itemsCount.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyGroup.setVisibility(View.GONE);

            adapter = new RecyclerFavMealAdapter();
            adapter.setMeals(meals);
            binding.recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new RecyclerFavMealAdapter.onItemClickListener() {
                @Override
                public void onItemClick(FavouriteMealDto meal) {
                    navigateToFavDetails(meal);
                }

                @Override
                public void onItemRemove(FavouriteMealDto meal) {
                    if (isInternetAvailable()){
                        presenter.removeFavoriteMeal(meal);
                    }
                    else{
                        SnackBarHelper.showCustomSnackBar(requireActivity(),getString(R.string.you_cannot_remove_a_meal_without_internet_connection), R.color.errorRed, Gravity.TOP);
                    }
                }
            });

        }
        else{
            binding.itemsCount.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyGroup.setVisibility(View.VISIBLE);
        }

    }

    private void navigateToFavDetails(FavouriteMealDto meal){
        Navigation.findNavController(requireView()).navigate(FavoritesFragmentDirections.actionFavoritesFragmentToFavouriteMealDetailsFragment(meal));
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
    public void showError(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.errorRed, Gravity.TOP);
    }

    @Override
    public void onRemoveSuccess(String message) {
        SnackBarHelper.showCustomSnackBar(requireActivity(),message, R.color.successGreen, Gravity.BOTTOM);
        presenter.getFavoriteMeals(SharedModel.getUser().getId());
    }



}