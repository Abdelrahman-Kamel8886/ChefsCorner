package com.abdok.chefscorner.Ui.Base.Home;

import com.abdok.chefscorner.Local.SharedPref.SharedPrefHelper;
import com.abdok.chefscorner.Models.CategoryResponseDTO;
import com.abdok.chefscorner.Models.RandomMealsDTO;
import com.abdok.chefscorner.Network.RetroConnection;
import com.abdok.chefscorner.Network.RetroServices;
import com.abdok.chefscorner.Utils.SharedModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements IHomePresenter {

    private IHomeView view;
    private SharedPrefHelper sharedPrefHelper;
    private RetroServices retroServices;

    public HomePresenter(IHomeView view){
        this.view = view;
        sharedPrefHelper = SharedPrefHelper.getInstance();
        retroServices = RetroConnection.getServices();
    }

    @Override
    public void start() {
        getUserData();
    }

    @Override
    public void getUserData() {
       // view.initView(sharedPrefHelper.getUser());
        SharedModel.setUser(sharedPrefHelper.getUser());
        getRandomMeals();
    }

    @Override
    public void getRandomMeals() {
        ArrayList<RandomMealsDTO.MealsDTO> myMeals = new ArrayList<>();
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        List<Single<RandomMealsDTO>> requests = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            requests.add(retroServices.getRandomMeal());
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
                        //view.showRandomMeals(meals);
                        SharedModel.setRandomMeals(meals);
                        getBreakFastMeals();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.getMessage());
                    }
                });


    }



    @Override
    public void getBreakFastMeals() {
        retroServices.getMealsByCategory("Breakfast")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoryResponseDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CategoryResponseDTO categoryResponseDTO) {
                        //view.showBreakFastMeals(categoryResponseDTO.getMeals());
                        SharedModel.setBreakfastMeals(categoryResponseDTO.getMeals());
                        getDesertMeals();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.getMessage());
                    }
                });
    }

    @Override
    public void getDesertMeals() {
        retroServices.getMealsByCategory("Dessert")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoryResponseDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CategoryResponseDTO categoryResponseDTO) {
                        //view.showDesertMeals(categoryResponseDTO.getMeals());
                        SharedModel.setDesertMeals(categoryResponseDTO.getMeals());
                        view.initView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast(e.getMessage());
                    }
                });
    }
}
