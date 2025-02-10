package com.abdok.chefscorner.Ui.Base.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdok.chefscorner.Adapters.RecyclerCategoryMealAdapter;
import com.abdok.chefscorner.Adapters.RecyclerRandomAdapter;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentHomeBinding;

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

    private void checkForData(){
        if (SharedModel.getRandomMeals()==null||SharedModel.getBreakfastMeals()==null||SharedModel.getDesertMeals()==null){
            presenter.start();
        }
        else{
            initView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void initView() {
        showRandomMeals(SharedModel.getRandomMeals());
        showBreakFastMeals(SharedModel.getBreakfastMeals());
        showDesertMeals(SharedModel.getDesertMeals());
    }


    @Override
    public void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMeals(List<MealDTO> meals) {
        adapter = new RecyclerRandomAdapter(meals);
        binding.randomRecycler.setAdapter(adapter);
       // binding.randomRecycler.set3DItem(true);
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

        if (baseView!=null){
            baseView.showMainView();
            onClicks();
        }
    }

    private void onClicks(){
        adapter.setListener(this::onItemClick);
        breakfastAdapter.setOnItemClickListener(this::onItemClick);
        desertAdapter.setOnItemClickListener(this::onItemClick);
    }



    private void onItemClick(int id){
        navigateToDetails(id , null);
    }

    private void onItemClick(MealDTO mealDTO){
        int id = Integer.parseInt(mealDTO.getIdMeal());
        navigateToDetails(id,mealDTO);
    }

    private void navigateToDetails(int id , MealDTO mealDTO){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id,mealDTO));
    }



}