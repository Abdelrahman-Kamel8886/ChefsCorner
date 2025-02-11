package com.abdok.chefscorner.Ui.Base.Ingredients.Veiw;

import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;

import java.util.List;

public interface IAllIngredientsView {
    void filterData(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients);
}
