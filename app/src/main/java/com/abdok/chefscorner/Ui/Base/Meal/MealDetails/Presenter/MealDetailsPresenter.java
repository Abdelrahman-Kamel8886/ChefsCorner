package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter;

import com.abdok.chefscorner.Models.RandomMealsResponseDTO;
import com.abdok.chefscorner.Network.RetroConnection;
import com.abdok.chefscorner.Ui.Base.Meal.MealDetails.View.IMealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealDetailsPresenter implements IMealDetailsPresenter {

    IMealDetailsView view;


    public MealDetailsPresenter(IMealDetailsView view){
        this.view = view;
    }

    @Override
    public void getMealDetails(int id) {
        RetroConnection.getServices().getMealDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RandomMealsResponseDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(RandomMealsResponseDTO randomMealsResponseDTO) {
                        view.InitData(randomMealsResponseDTO.getMeals().get(0));
                    }


                    @Override
                    public void onError(Throwable e) {
                        view.showInformation(e.getMessage());
                    }
                });
    }


}
