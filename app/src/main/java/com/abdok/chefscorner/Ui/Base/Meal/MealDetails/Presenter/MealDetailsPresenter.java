package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter;

import com.abdok.chefscorner.Models.RandomMealsResponseDTO;
import com.abdok.chefscorner.Network.RetroConnection;
import com.abdok.chefscorner.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Meal.MealDetails.View.IMealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetailsPresenter implements IMealDetailsPresenter {

    private IMealDetailsView view;
    private RemoteRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MealDetailsPresenter(IMealDetailsView view) {
        this.view = view;
        this.repository = RemoteRepository.getInstance();
    }

    @Override
    public void getMealDetails(int id) {

        Disposable disposable = repository.getMealDetails(id)
                .subscribe(
                        mealDTO -> {
                            view.InitData(mealDTO);
                        },
                        throwable -> {
                            view.showInformation(throwable.getMessage());
                        }
                );
        compositeDisposable.add(disposable);

    }

    @Override
    public void clearDisposable() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

}
