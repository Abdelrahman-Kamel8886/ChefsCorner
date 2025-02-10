package com.abdok.chefscorner.Ui.Base.Details.Presenter;

import com.abdok.chefscorner.Models.RandomMealsResponseDTO;
import com.abdok.chefscorner.Network.RetroConnection;
import com.abdok.chefscorner.Ui.Base.Details.View.IMealDetailsView;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.flowable.FlowableFromObservable;
import io.reactivex.internal.operators.observable.ObservableSampleWithObservable;
import io.reactivex.schedulers.Schedulers;

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
