package com.abdok.chefscorner.Ui.Base.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HomeFragment extends Fragment implements IHomeView {

    FragmentHomeBinding binding;
    IHomePresenter presenter;
    RecyclerRandomAdapter adapter;
    RecyclerCategoryMealAdapter breakfastAdapter , desertAdapter;
    IBaseView baseView;

    DatabaseReference myRef;

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

        myRef = FirebaseDatabase.getInstance().getReference("PlanMeals");
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
        Snackbar.make(requireView(),message,Snackbar.LENGTH_SHORT).show();
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