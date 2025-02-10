package com.abdok.chefscorner.Ui.Base.Home;

import com.abdok.chefscorner.Local.SharedPref.SharedPrefHelper;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Models.RandomMealsResponseDTO;
import com.abdok.chefscorner.Network.RetroConnection;
import com.abdok.chefscorner.Network.RetroServices;
import com.abdok.chefscorner.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Utils.SharedModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class HomePresenter implements IHomePresenter {

    private IHomeView view;
    private RemoteRepository remoteRepository;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenter(IHomeView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
    }

    @Override
    public void start() {
        getRandomMeals();
    }

    @Override
    public void getRandomMeals() {
        ArrayList<MealDTO> myMeals = new ArrayList<>();
        Disposable disposable = remoteRepository.getRandomMeals()
                .subscribe(
                        randomMealsResponseDTO -> {
                            myMeals.addAll(randomMealsResponseDTO.getMeals());

                        },
                        throwable -> {
                            view.showToast(throwable.getMessage());
                        },
                        () -> {
                            SharedModel.setRandomMeals(myMeals);
                            getBreakFastMeals();
                        }

                );
        compositeDisposable.add(disposable);
    }


    @Override
    public void getBreakFastMeals() {

        Disposable breakfast = remoteRepository.getCategoriesMeals("Breakfast")
                .subscribe(
                        categoryMealsResponseDTO -> {
                            SharedModel.setBreakfastMeals(categoryMealsResponseDTO.getMeals());
                            getDesertMeals();
                        },
                        throwable -> {
                            view.showToast(throwable.getMessage());
                        }
                );
        compositeDisposable.add(breakfast);
    }

    @Override
    public void getDesertMeals() {
        Disposable dessert = remoteRepository.getCategoriesMeals("Dessert")
                .subscribe(
                        categoryMealsResponseDTO -> {
                            SharedModel.setDesertMeals(categoryMealsResponseDTO.getMeals());
                            view.initView();
                            clearDisposable();
                        },
                        throwable -> {
                            view.showToast(throwable.getMessage());
                        }

                );
        compositeDisposable.add(dessert);
    }

    private void clearDisposable() {
        compositeDisposable.clear();
    }

}
