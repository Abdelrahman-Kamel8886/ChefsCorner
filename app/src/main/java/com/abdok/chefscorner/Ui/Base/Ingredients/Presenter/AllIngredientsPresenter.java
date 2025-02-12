package com.abdok.chefscorner.Ui.Base.Ingredients.Presenter;

import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Ui.Base.Ingredients.Veiw.IAllIngredientsView;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public class AllIngredientsPresenter implements IAllIngredientsPresenter{


    List<IngredientsNamesResponseDTO.IngredientDTO> ingredientsList;
    IAllIngredientsView view;

    public AllIngredientsPresenter(IAllIngredientsView view , List<IngredientsNamesResponseDTO.IngredientDTO> ingredientsList) {
        this.ingredientsList = ingredientsList;
        this.view = view;
    }


    @Override
    public void search(String query) {
        Observable<IngredientsNamesResponseDTO.IngredientDTO> observable = Observable.fromIterable(ingredientsList);
        observable
                .filter(item -> item.getStrIngredient().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(ingredientDTOS -> view.filterData(ingredientDTOS));
    }
}
