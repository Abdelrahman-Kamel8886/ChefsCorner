package com.abdok.chefscorner.Ui.Base.Home.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Data.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Home.View.IHomeView;
import com.abdok.chefscorner.Utils.SharedModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

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
    public void addMealToPlan(MealDTO mealDTO, DateDTO date) {
        backupRepository.savePlanMealToFirebase(mealDTO,date).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                PlanMealDto meal = new PlanMealDto(SharedModel.getUser().getId(),date,mealDTO);
                meal.setMealId(mealDTO.getIdMeal());
                savePlanMealToLocal(meal);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                view.showMessage(e.getMessage());
            }
        });
    }

    @Override
    public void syncPlanMeals(String id) {
        backupRepository.getPlanMeals(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PlanMealDto> planMealDtos = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot mealSnapshot : dataSnapshot.getChildren()) {
                        PlanMealDto planMealDto = mealSnapshot.getValue(PlanMealDto.class);
                        planMealDtos.add(planMealDto);
                    }
                }
                syncPlanMealsToLocal(planMealDtos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void syncFavouriteMeals(String id) {
        backupRepository.getFavoriteMeals(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<FavouriteMealDto> favouriteMealDtos = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            FavouriteMealDto favouriteMealDto = dataSnapshot.getValue(FavouriteMealDto.class);
                            favouriteMealDtos.add(favouriteMealDto);
                        }
                        syncFavouriteMealsToLocal(favouriteMealDtos);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void syncPlanMealsToLocal(List<PlanMealDto> planMealDtos){
       clearPlanMealTable(planMealDtos);
    }

    private void clearPlanMealTable(List<PlanMealDto> planMealDtos){
        Disposable disposable = backupRepository.clearPlanMealTable().subscribe(
                () -> insertAllToPlan(planMealDtos)
        );
        compositeDisposable.add(disposable);
    }

    private void insertAllToPlan(List<PlanMealDto> planMealDtos){
        Disposable disposable =backupRepository.insertAllToPlan(planMealDtos).subscribe();
        compositeDisposable.add(disposable);
    }

    private void syncFavouriteMealsToLocal(List<FavouriteMealDto> favouriteMealDtos){
        clearFavouriteMealTable(favouriteMealDtos);
    }

    private void clearFavouriteMealTable(List<FavouriteMealDto> favouriteMealDtos){
        Disposable disposable = backupRepository.clearFavoriteMealTable().subscribe(
                () -> insertAllToFavorite(favouriteMealDtos)
                , throwable -> Log.e("HomeTAG", "clearFavouriteMealTable: "+throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }
    private void insertAllToFavorite(List<FavouriteMealDto> favouriteMealDtos){
        Disposable disposable =backupRepository.insertAllToFavorite(favouriteMealDtos).subscribe(
                () -> Log.e("HomeTAG", "insertAllToFavorite: ")
                , throwable -> Log.e("HomeTAG", "insertAllToFavorite: "+throwable.getMessage())
        );
        compositeDisposable.add(disposable);
    }

    private void savePlanMealToLocal(PlanMealDto meal){
        Disposable disposable = backupRepository.savePlanMealToLocal(meal)
                .subscribe(
                        () -> view.showMessage("Meal saved successfully to your plan"),
                        throwable -> view.showMessage(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void clearDisposable() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

}
