package com.abdok.chefscorner.Ui.Base.Meal.AllMeals.Presenter;

import com.abdok.chefscorner.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Meal.AllMeals.View.IAllMealsView;
import com.abdok.chefscorner.Utils.SharedModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AllMealsPresenter implements IAllMealsPresenter{

    IAllMealsView view;
    private RemoteRepository remoteRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AllMealsPresenter(IAllMealsView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
    }

    @Override
    public void getCategoriesMeals(String category) {
        Disposable disposable = remoteRepository.getCategoriesMeals(category)
                .subscribe(
                        categoryMealsResponseDTO -> {
                            SharedModel.setBreakfastMeals(categoryMealsResponseDTO.getMeals());
                            view.showMeals(categoryMealsResponseDTO.getMeals());
                        }
                );
        compositeDisposable.add(disposable);
    }
}
