package com.abdok.chefscorner.Ui.Base.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdok.chefscorner.Adapters.RecyclerCategoryMealAdapter;
import com.abdok.chefscorner.Adapters.RecyclerRandomAdapter;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Models.CategoryResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsDTO;
import com.abdok.chefscorner.Models.UserDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentHomeBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeFragment extends Fragment implements IHomeView {

    FragmentHomeBinding binding;
    IHomePresenter presenter;
    RecyclerRandomAdapter adapter;
    RecyclerCategoryMealAdapter breakfastAdapter , desertAdapter;
    IBaseView baseView;

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

    private void onClicks(){
        binding.avatarImg.setOnClickListener(v -> navigateToProfile());
    }


    private void checkForData(){

        Log.d("Debug", "checkForData: "+SharedModel.getUser());
        Log.d("Debug", "checkForData: "+SharedModel.getRandomMeals());
        Log.d("Debug", "checkForData: "+SharedModel.getBreakfastMeals());
        Log.d("Debug", "checkForData: "+SharedModel.getDesertMeals());

        if (SharedModel.getUser() == null||SharedModel.getRandomMeals()==null||SharedModel.getBreakfastMeals()==null||SharedModel.getDesertMeals()==null){
            presenter.start();
            Log.d("Debug", "checkForData: 1");
        }
        else{
            initView();
            Log.d("Debug", "checkForData: 2");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void initView() {
        showUserData(SharedModel.getUser());
        showRandomMeals(SharedModel.getRandomMeals());
        showBreakFastMeals(SharedModel.getBreakfastMeals());
        showDesertMeals(SharedModel.getDesertMeals());
    }

    @Override
    public void showUserData(UserDTO user) {
        binding.userName.setText("Hi, " + user.getName());
        if (user.getPhotoUrl() != null){
            Glide.with(requireContext()).load(user.getPhotoUrl()).into(binding.avatarImg);
        }

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMeals(List<RandomMealsDTO.MealsDTO> meals) {
        adapter = new RecyclerRandomAdapter(meals);
        binding.randomRecycler.setAdapter(adapter);
       // binding.randomRecycler.set3DItem(true);
        binding.randomRecycler.setAlpha(true);
        binding.randomRecycler.setInfinite(false);
    }

    @Override
    public void showBreakFastMeals(List<CategoryResponseDTO.CategoryMealDTO> meals) {
        breakfastAdapter = new RecyclerCategoryMealAdapter(meals);
        binding.breakFastRecycler.setAdapter(breakfastAdapter);
    }

    @Override
    public void showDesertMeals(List<CategoryResponseDTO.CategoryMealDTO> meals) {
        desertAdapter = new RecyclerCategoryMealAdapter(meals);
        binding.desertsRecycler.setAdapter(desertAdapter);
        if (baseView!=null){
            baseView.showMainView();
            onClicks();
        }
    }

    private void navigateToProfile(){
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_profileFragment);
    }

}