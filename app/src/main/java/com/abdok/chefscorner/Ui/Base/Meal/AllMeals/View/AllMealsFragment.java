package com.abdok.chefscorner.Ui.Base.Meal.AllMeals.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Adapters.RecyclerAllMealAdapter;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.Home.HomeFragmentDirections;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Meal.AllMeals.Presenter.AllMealsPresenter;
import com.abdok.chefscorner.Ui.Base.Meal.AllMeals.Presenter.IAllMealsPresenter;
import com.abdok.chefscorner.Utils.CountryFlagMapper;
import com.abdok.chefscorner.databinding.FragmentAllMealsBinding;

import java.util.List;


public class AllMealsFragment extends Fragment implements IAllMealsView {

    FragmentAllMealsBinding binding;
    RecyclerAllMealAdapter adapter;
    IAllMealsPresenter presenter;
    IBaseView baseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAllMealsBinding.bind(view);
        presenter = new AllMealsPresenter(this);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.hideBottomNav();

        String title = AllMealsFragmentArgs.fromBundle(getArguments()).getTitle();
        SearchTypeEnum type = AllMealsFragmentArgs.fromBundle(getArguments()).getType();

        initView(title,type);
    }

    private void initView(String title, SearchTypeEnum type){
        presenter.getCategoriesMeals(title);
        binding.title.setText(title+" Category");
        binding.backBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        adapter = new RecyclerAllMealAdapter(meals);
        binding.recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(id ->navigateToDetails(id));
    }

    private void navigateToDetails(int id){
        Navigation.findNavController(requireView()).navigate(AllMealsFragmentDirections.actionAllMealsFragmentToMealDetailsFragment(id,null));
    }

}