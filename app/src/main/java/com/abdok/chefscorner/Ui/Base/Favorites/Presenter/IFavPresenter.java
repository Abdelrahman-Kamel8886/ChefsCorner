package com.abdok.chefscorner.Ui.Base.Favorites.Presenter;

import com.abdok.chefscorner.Data.Models.FavouriteMealDto;

public interface IFavPresenter {

    void getFavoriteMeals(String id);
    void removeFavoriteMeal(FavouriteMealDto meal);
}
