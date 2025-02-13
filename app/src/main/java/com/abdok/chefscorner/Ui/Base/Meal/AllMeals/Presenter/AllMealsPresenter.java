package com.abdok.chefscorner.Ui.Base.Meal.AllMeals.Presenter;

import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Meal.AllMeals.View.IAllMealsView;
import com.abdok.chefscorner.Utils.SharedModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class AllMealsPresenter implements IAllMealsPresenter{

    IAllMealsView view;
    private RemoteRepository remoteRepository;
    List<CategoryMealsResponseDTO.CategoryMealDTO> meals;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AllMealsPresenter(IAllMealsView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
    }

    @Override
    public void getMeals(String title, SearchTypeEnum type) {
        switch (type) {
            case CATEGORY:
                getCategoriesMeals(title);
                break;
            case AREA:
                getAreaMeals(title);
                break;
            case INGREDIENT:
                getIngredientsMeals(title);
                break;
        }
    }

    @Override
    public void getCategoriesMeals(String category) {
        Disposable disposable = remoteRepository.getCategoriesMeals(category)
                .subscribe(
                        categoryMealsResponseDTO -> {
                            meals = categoryMealsResponseDTO.getMeals();
                            view.showMeals(categoryMealsResponseDTO.getMeals());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getIngredientsMeals(String ingredient) {
        Disposable disposable = remoteRepository.getIngredientsMeals(ingredient)
                .subscribe(
                        categoryMealsResponseDTO -> {
                            meals = categoryMealsResponseDTO.getMeals();
                            view.showMeals(categoryMealsResponseDTO.getMeals());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void getAreaMeals(String area) {
        Disposable disposable = remoteRepository.getAreaMeals(area)
                .subscribe(
                        categoryMealsResponseDTO -> {
                            meals = categoryMealsResponseDTO.getMeals();
                            view.showMeals(categoryMealsResponseDTO.getMeals());
                        }
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void search(String query) {
        Observable<CategoryMealsResponseDTO.CategoryMealDTO> observable = Observable.fromIterable(meals);
        observable
                .filter(item -> item.getStrMeal().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(areas -> view.filterData(areas));
    }

    @Override
    public void clearDisposable() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }


}
