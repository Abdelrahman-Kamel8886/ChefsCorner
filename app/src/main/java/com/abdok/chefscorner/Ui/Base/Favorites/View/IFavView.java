package com.abdok.chefscorner.Ui.Base.Favorites.View;

import com.abdok.chefscorner.Models.FavouriteMealDto;

import java.util.List;

public interface IFavView {

    void showList(List<FavouriteMealDto> meals);
    void showError(String message);
    void onRemoveSuccess(String message);

}
