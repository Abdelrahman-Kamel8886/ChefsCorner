package com.abdok.chefscorner.Ui.Base.Meal.OfflineMeals.FavouriteDetails.Presenter;

import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.PlanMealDto;

public interface IFavMDPresenter {
    void removeFromFav(FavouriteMealDto favouriteMealDto);
}
