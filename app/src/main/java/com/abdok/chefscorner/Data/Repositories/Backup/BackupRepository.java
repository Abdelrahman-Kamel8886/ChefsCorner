package com.abdok.chefscorner.Data.Repositories.Backup;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.DataSources.Local.Room.LocalDataBase;
import com.abdok.chefscorner.Data.DataSources.Local.Room.MealsDao;
import com.abdok.chefscorner.Data.DataSources.Remote.FirebaseRealtime.FirebaseRealtimeDataSource;
import com.abdok.chefscorner.Data.DataSources.Local.SharedPref.SharedPrefHelper;
import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BackupRepository implements IBackupRepo {

    private FirebaseRealtimeDataSource firebaseRealtimeDataSource;
    private MealsDao mealDao;
    private SharedPrefHelper sharedPrefHelper;
    private BackupCallback callback;
    private static BackupRepository instance;


    private BackupRepository(BackupCallback callback) {
        this.firebaseRealtimeDataSource = FirebaseRealtimeDataSource.getInstance();
        this.sharedPrefHelper = SharedPrefHelper.getInstance();
        this.callback = callback;
        this.mealDao = LocalDataBase.getDao();
    }

    public static synchronized BackupRepository getInstance(BackupCallback callback) {
        if (instance == null) {
            instance = new BackupRepository(callback);
        }
        return instance;
    }


    @Override
    public void savePlanMeal(MealDTO meal, DateDTO date) {
        savePlanMealToFirebase(meal, date);
    }

    @Override
    public void savePlanMealToFirebase(MealDTO meal , DateDTO date) {
        String id = sharedPrefHelper.getUser().getId();
        PlanMealDto dto = new PlanMealDto(id,date,meal);
        dto.setMealId(meal.getIdMeal());
        firebaseRealtimeDataSource.savePlanMeal(dto)
                .addOnSuccessListener(unused -> savePlanMealToLocal(dto))
                .addOnFailureListener(e -> {callback.onFailure(e.getMessage());});
    }

    @Override
    public void deletePlanMealFromFirebase(PlanMealDto dto) {
        firebaseRealtimeDataSource.deletePlanMeal(dto)
                .addOnSuccessListener(unused -> callback.onSuccess("Meal deleted successfully"))
                .addOnFailureListener(e -> {callback.onFailure(e.getMessage());});
    }

    @Override
    public List<PlanMealDto> getPlanMeals(String id) {
        firebaseRealtimeDataSource.getPlanMeals(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<PlanMealDto> planMealDtos = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PlanMealDto planMealDto = dataSnapshot.getValue(PlanMealDto.class);
                            planMealDtos.add(planMealDto);
                        }
                        callback.onPlanMealsReceived(planMealDtos);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        return Collections.emptyList();
    }

    @Override
    public void savePlanMealToLocal(PlanMealDto meal) {
        Disposable disposable = mealDao.insert(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> callback.onSuccess("Meal saved successfully"),
                        throwable -> callback.onFailure(throwable.getMessage())
                );
    }


    public interface BackupCallback {
        void onSuccess(String message);
        void onFailure(String message);
        void onPlanMealsReceived(List<PlanMealDto> planMealDtos);
    }

}
