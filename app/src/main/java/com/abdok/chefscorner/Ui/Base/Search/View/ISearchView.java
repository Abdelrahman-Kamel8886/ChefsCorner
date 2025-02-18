package com.abdok.chefscorner.Ui.Base.Search.View;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;

public interface ISearchView {

    void showIngredients(IngredientsNamesResponseDTO ingredientsDTO);
    void showCategoriesNames(CategoriesNamesResponseDTO categoriesDTO);
    void showAreaNames(AreasNamesResponseDTO areaDTO);
    void showError();

}
