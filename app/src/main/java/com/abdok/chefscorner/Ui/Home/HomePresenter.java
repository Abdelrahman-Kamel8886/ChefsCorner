package com.abdok.chefscorner.Ui.Home;

import android.util.Log;

import com.abdok.chefscorner.Models.RandomMealsDTO;
import com.abdok.chefscorner.Network.RetroConnection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements IHomePresenter{

    private IHomeView view;

    public HomePresenter(IHomeView view){
        this.view = view;
    }
    @Override
    public void getRandomMeals() {
        ArrayList<RandomMealsDTO.MealsDTO> myMeals = new ArrayList<>();
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        List<Single<RandomMealsDTO>> requests = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            requests.add(RetroConnection.getServices().getRandomMeal());
        }

        Single.zip(requests, objects -> {
                    for (Object obj : objects) {
                        RandomMealsDTO randomMealsDTO = (RandomMealsDTO) obj;
                        myMeals.addAll(randomMealsDTO.getMeals());
                    }
                    return myMeals;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<RandomMealsDTO.MealsDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ArrayList<RandomMealsDTO.MealsDTO> meals) {
                        view.showRandomMeals(meals);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.getMessage());
                    }
                });


    }
}
