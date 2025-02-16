package com.abdok.chefscorner.Ui.Base.Search.SearchSheet.Presenter;

import com.abdok.chefscorner.Ui.Base.Search.SearchSheet.View.ISearchBSView;
import com.abdok.chefscorner.Data.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Data.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.Utils.SharedModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SearchSheetPresenter implements ISearchBSPresenter{

    private ISearchBSView view;
    private List<IngredientsNamesResponseDTO.IngredientDTO> ingredients;
    private List<AreasNamesResponseDTO.AreaNameDTO> areas;
    private List<CategoriesNamesResponseDTO.CategoryNameDTO> categories;

    public SearchSheetPresenter(ISearchBSView view) {
        this.view = view;
        ingredients = new ArrayList(SharedModel.getIngredientsNamesResponse().getMeals());
        areas = new ArrayList(SharedModel.getAreasNamesResponse().getMeals());
        categories = new ArrayList(SharedModel.getCategoriesNamesResponse().getMeals());
    }


    @Override
    public void searchOnIngredients(String query) {
        Observable<IngredientsNamesResponseDTO.IngredientDTO> observable = Observable.fromIterable(SharedModel.getIngredientsNamesResponse().getMeals());
        observable
                .filter(item -> item.getStrIngredient().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(list -> view.filterIngredientsData(list));
    }

    @Override
    public void searchOnAreas(String query) {
        Observable<AreasNamesResponseDTO.AreaNameDTO> observable = Observable.fromIterable(SharedModel.getAreasNamesResponse().getMeals());
        observable
                .filter(item -> item.getStrArea().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(list -> view.filterAreasData(list));
    }

    @Override
    public void searchOnCategories(String query) {
        Observable<CategoriesNamesResponseDTO.CategoryNameDTO> observable = Observable.fromIterable(SharedModel.getCategoriesNamesResponse().getMeals());
        observable
                .filter(item -> item.getStrCategory().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(list -> view.filterCategoriesData(list));
    }

    @Override
    public void resetSearch() {
        view.filterIngredientsData(ingredients);
        view.filterAreasData(areas);
        view.filterCategoriesData(categories);
    }
}
