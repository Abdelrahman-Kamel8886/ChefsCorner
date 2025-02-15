package com.abdok.chefscorner.Ui.Base.Search.View;

import com.abdok.chefscorner.Data.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.IngredientsNamesResponseDTO;

public interface ISearchView {

    void showIngredients(IngredientsNamesResponseDTO ingredientsDTO);
    void showCategoriesNames(CategoriesNamesResponseDTO categoriesDTO);
    void showAreaNames(AreasNamesResponseDTO areaDTO);
    void showError();
}
