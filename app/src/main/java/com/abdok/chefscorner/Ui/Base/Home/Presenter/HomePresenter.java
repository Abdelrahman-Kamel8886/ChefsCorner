package com.abdok.chefscorner.Ui.Base.Home.Presenter;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Data.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Home.View.IHomeView;
import com.abdok.chefscorner.Utils.SharedModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class HomePresenter implements IHomePresenter  {

    private IHomeView view;
    private RemoteRepository remoteRepository;
    private BackupRepository backupRepository;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomePresenter(IHomeView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
        backupRepository = BackupRepository.getInstance();

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
                            view.showError();
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
                            view.showError();
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
                            view.showError();
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
        backupRepository.savePlanMealToFirebase(mealDTO,date).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                PlanMealDto meal = new PlanMealDto(SharedModel.getUser().getId(),date,mealDTO);
                meal.setMealId(mealDTO.getIdMeal());
                saveToLocal(meal);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    private void saveToLocal(PlanMealDto meal){
        Disposable disposable = backupRepository.savePlanMealToLocal(meal)
                .subscribe(
                        () -> view.showMessage("Meal saved successfully to your plan"),
                        throwable -> view.showMessage(throwable.getMessage())
                );
    }

}
