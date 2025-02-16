package com.abdok.chefscorner.Ui.Base.Search.SearchSheet.View;

import com.abdok.chefscorner.Data.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.IngredientsNamesResponseDTO;

import java.util.List;

public interface ISearchBSView {

    void filterAreasData(List<AreasNamesResponseDTO.AreaNameDTO> areas);
    void filterIngredientsData(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients);
    void filterCategoriesData(List<CategoriesNamesResponseDTO.CategoryNameDTO> categories);
}
