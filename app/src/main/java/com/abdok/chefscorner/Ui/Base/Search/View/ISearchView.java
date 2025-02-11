package com.abdok.chefscorner.Ui.Base.Search.View;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.airbnb.lottie.L;

import java.util.ArrayList;
import java.util.List;

public interface ISearchView {

    void showIngredients(IngredientsNamesResponseDTO ingredientsDTO);
    void showCategoriesNames(CategoriesNamesResponseDTO categoriesDTO);
    void showAreaNames(AreasNamesResponseDTO areaDTO);
}
