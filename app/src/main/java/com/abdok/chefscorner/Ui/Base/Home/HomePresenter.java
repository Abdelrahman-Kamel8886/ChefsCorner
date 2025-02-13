package com.abdok.chefscorner.Ui.Base.Home;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Data.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Utils.SharedModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class HomePresenter implements IHomePresenter , BackupRepository.BackupCallback {

    private IHomeView view;
    private RemoteRepository remoteRepository;
    private BackupRepository backupRepository;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenter(IHomeView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
        backupRepository = BackupRepository.getInstance(this);

    }

    @Override
    public void start() {
        getRandomMeals();
    }

    @Override
    public void getRandomMeals() {
        Disposable disposable = remoteRepository.getRandomMeals()
                .subscribe(meals -> {
                            SharedModel.setRandomMeals((ArrayList<MealDTO>) meals);
                            getBreakFastMeals();
                        }, throwable -> {
                            view.showMessage(throwable.getMessage());
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
                            view.showMessage(throwable.getMessage());
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
                        },
                        throwable -> {
                            view.showMessage(throwable.getMessage());
                        }

                );
        compositeDisposable.add(dessert);
    }
    @Override
    public void clearDisposable() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

    @Override
    public void addMealToPlan(MealDTO mealDTO, DateDTO date) {
        backupRepository.savePlanMeal(mealDTO,date);
    }

    @Override
    public void onSuccess(String message) {
        view.showMessage(message);
    }

    @Override
    public void onFailure(String message) {
        view.showMessage(message);
    }

    @Override
    public void onPlanMealsReceived(List<PlanMealDto> planMealDtos) {
    }
}
