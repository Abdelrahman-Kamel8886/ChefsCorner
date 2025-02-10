package com.abdok.chefscorner.Ui.Base.Search.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Adapters.RecyclerCategoriesNamesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerIngredientsNamesAdapter;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.Search.Presenter.ISearchPresenter;
import com.abdok.chefscorner.Ui.Base.Search.Presenter.SearchPresenter;
import com.abdok.chefscorner.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements ISearchView{

    FragmentSearchBinding binding;
    RecyclerIngredientsNamesAdapter ingredientsNamesAdapter;
    RecyclerCategoriesNamesAdapter categoriesNamesAdapter;
    ISearchPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSearchBinding.bind(view);
        presenter = new SearchPresenter(this);
        presenter.getIngredientsNames();
        presenter.getCategoriesNames();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showIngredients(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients) {
        ingredientsNamesAdapter = new RecyclerIngredientsNamesAdapter((ArrayList<IngredientsNamesResponseDTO.IngredientDTO>) ingredients);
        binding.recyclerIngredients.setAdapter(ingredientsNamesAdapter);

    }

    @Override
    public void showCategoriesNames(List<CategoriesNamesResponseDTO.CategoryNameDTO> categories) {
        categoriesNamesAdapter = new RecyclerCategoriesNamesAdapter((ArrayList<CategoriesNamesResponseDTO.CategoryNameDTO>) categories);
        binding.recyclerCategories.setAdapter(categoriesNamesAdapter);

    }
}