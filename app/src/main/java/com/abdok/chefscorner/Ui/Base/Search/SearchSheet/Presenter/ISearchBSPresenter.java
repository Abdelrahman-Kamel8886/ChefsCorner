package com.abdok.chefscorner.Ui.Base.Search.SearchSheet.Presenter;

public interface ISearchBSPresenter {
    void searchOnIngredients(String query);
    void searchOnAreas(String query);
    void searchOnCategories(String query);
    void resetSearch();
}
