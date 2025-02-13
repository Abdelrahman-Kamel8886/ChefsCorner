package com.abdok.chefscorner.Ui.Base.Search.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Adapters.RecyclerAreaNamesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerCategoriesNamesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerIngredientsNamesAdapter;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Search.Presenter.ISearchPresenter;
import com.abdok.chefscorner.Ui.Base.Search.Presenter.SearchPresenter;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment implements ISearchView{

    FragmentSearchBinding binding;
    RecyclerIngredientsNamesAdapter ingredientsNamesAdapter;
    RecyclerCategoriesNamesAdapter categoriesNamesAdapter;
    RecyclerAreaNamesAdapter areasNamesAdapter;
    ISearchPresenter presenter;
    IBaseView baseView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSearchBinding.bind(view);
        presenter = new SearchPresenter(this);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.showMainView();

        checkForData();
    }

    private void checkForData(){
        if (SharedModel.getIngredientsNamesResponse()==null||SharedModel.getCategoriesNamesResponse()==null||SharedModel.getAreasNamesResponse()==null){
            binding.loadingLayout.setVisibility(View.VISIBLE);
            presenter.getIngredientsNames();
            presenter.getCategoriesNames();
            presenter.getAreasNames();
        }
        else{
            initView();
        }
    }

    private void initView(){
        showIngredients(SharedModel.getIngredientsNamesResponse());
        showCategoriesNames(SharedModel.getCategoriesNamesResponse());
        showAreaNames(SharedModel.getAreasNamesResponse());
    }

    @Override
    public void showIngredients(IngredientsNamesResponseDTO ingredientsDTO) {
        ingredientsNamesAdapter = new RecyclerIngredientsNamesAdapter(ingredientsDTO.getMeals().subList(0,10));
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
        areasNamesAdapter = new RecyclerAreaNamesAdapter(areaDTO.getMeals().subList(0,9));
        binding.recyclerarea.setAdapter(areasNamesAdapter);
        areasNamesAdapter.setListener(areaName -> navigateToAreaMeals(areaName));
        binding.seeAllAreas.setOnClickListener(v -> {Navigation.findNavController(v).
                navigate(SearchFragmentDirections.actionSearchFragmentToAreasFragment(areaDTO));
        });
        showMainView();
    }

    private void showMainView(){
        binding.loadingLayout.setVisibility(View.GONE);
        binding.mainView.setVisibility(View.VISIBLE);
    }


    private void navigateToAreaMeals(String areaName){
        Navigation
                .findNavController(requireView())
                .navigate(SearchFragmentDirections
                        .actionSearchFragmentToAllMealsFragment(areaName , SearchTypeEnum.AREA));
    }
    private void navigateToIngredientsAllMeals(String ingredientName){
        Navigation
                .findNavController(requireView())
                .navigate(SearchFragmentDirections
                        .actionSearchFragmentToAllMealsFragment(ingredientName , SearchTypeEnum.INGREDIENT));

    }
    private void navigateToCategoriesAllMeals(String categoryName){
        Navigation
                .findNavController(requireView())
                .navigate(SearchFragmentDirections.actionSearchFragmentToAllMealsFragment(categoryName , SearchTypeEnum.CATEGORY));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}