package com.abdok.chefscorner.Data.Repositories.Backup;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.DataSources.Local.Room.LocalDataBase;
import com.abdok.chefscorner.Data.DataSources.Local.Room.MealsDao;
import com.abdok.chefscorner.Data.DataSources.Remote.FirebaseRealtime.FirebaseRealtimeDataSource;
import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BackupRepository implements IBackupRepo {

    private FirebaseRealtimeDataSource firebaseRealtimeDataSource;
    private MealsDao mealDao;
    private SharedPreferenceDataSource sharedPreferenceDataSource;
    private static BackupRepository instance;


    private BackupRepository() {
        this.firebaseRealtimeDataSource = FirebaseRealtimeDataSource.getInstance();
        this.sharedPreferenceDataSource = SharedPreferenceDataSource.getInstance();
        this.mealDao = LocalDataBase.getDao();
    }

    public static synchronized BackupRepository getInstance() {
        if (instance == null) {
            instance = new BackupRepository();
        }
        return instance;
    }



    @Override
    public Task<Void> savePlanMealToFirebase(MealDTO meal , DateDTO date) {
        String id = sharedPreferenceDataSource.getUser().getId();
        //Bitmap bitmap = meal.getBitmap();

        PlanMealDto planMealDto = new PlanMealDto(id,date,meal);
        planMealDto.setMealId(meal.getIdMeal());
        //planMealDto.getMeal().setBitmap(null);

       return firebaseRealtimeDataSource.savePlanMeal(planMealDto);
    }

    @Override
    public Task<Void> deletePlanMealFromFirebase(PlanMealDto dto) {
        return firebaseRealtimeDataSource.deletePlanMeal(dto);
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
                        //callback.onPlanMealsReceived(planMealDtos);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        return Collections.emptyList();
    }

    @Override
    public Completable savePlanMealToLocal(PlanMealDto meal) {
        return mealDao.insert(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<PlanMealDto> >getLocalPlanMeals(DateDTO dateDTO, String id) {
        return mealDao.getAllMeals(dateDTO,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable deletePlanMealFromLocal(PlanMealDto meal) {
        return mealDao.delete(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
