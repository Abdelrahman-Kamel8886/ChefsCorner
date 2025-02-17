package com.abdok.chefscorner.Data.Repositories.Backup;

import com.abdok.chefscorner.Data.DataSources.Local.Room.LocalDataBase;
import com.abdok.chefscorner.Data.DataSources.Local.Room.MealsDao;
import com.abdok.chefscorner.Data.DataSources.Remote.FirebaseRealtime.FirebaseRealtimeDataSource;
import com.abdok.chefscorner.Data.DataSources.Local.SharedPreference.SharedPreferenceDataSource;
import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BackupRepository implements IBackupRepo {

    private FirebaseRealtimeDataSource firebaseRealtimeDataSource;
    private MealsDao mealDao;
    private SharedPreferenceDataSource sharedPreferenceDataSource;
    String id;
    private static BackupRepository instance;


    private BackupRepository() {
        this.firebaseRealtimeDataSource = FirebaseRealtimeDataSource.getInstance();
        this.sharedPreferenceDataSource = SharedPreferenceDataSource.getInstance();
        this.mealDao = LocalDataBase.getDao();
        id = sharedPreferenceDataSource.getUser().getId();
    }

    public static synchronized BackupRepository getInstance() {
        if (instance == null) {
            instance = new BackupRepository();
        }
        return instance;
    }



    @Override
    public Task<Void> savePlanMealToFirebase(MealDTO meal , DateDTO date) {

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
    public DatabaseReference getPlanMeals(String id) {
       return firebaseRealtimeDataSource.getPlanMeals(id);
    }

    @Override
    public Completable savePlanMealToLocal(PlanMealDto meal) {
        return mealDao.insertToPlan(meal)
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
        return mealDao.deleteFromPlan(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<DateDTO>> getAllDaysMealAdded(String mealId, String id) {
        return mealDao.getAllDaysMealAdded(mealId,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable clearPlanMealTable() {
        return mealDao.clearPlanMealTable()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insertAllToPlan(List<PlanMealDto> planMeals) {
        return mealDao.insertAllToPlan(planMeals)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Task<Void> saveFavoriteMealToFirebase(FavouriteMealDto meal) {
        return firebaseRealtimeDataSource.saveFavoriteMeal(meal);
    }

    @Override
    public Task<Void> deleteFavoriteMealFromFirebase(FavouriteMealDto meal) {
        return firebaseRealtimeDataSource.deleteFavoriteMeal(meal);
    }

    @Override
    public DatabaseReference getFavoriteMeals(String id) {
        return firebaseRealtimeDataSource.getFavoriteMeals(id);
    }

    @Override
    public Completable saveFavoriteMealToLocal(FavouriteMealDto meal) {
        return mealDao.insertToFavourite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<FavouriteMealDto>> getLocalFavoriteMeals(String id) {
        return mealDao.getAllFavouriteMeals(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable deleteFavoriteMealFromLocal(FavouriteMealDto meal) {
        return mealDao.deleteFromFavourite(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Boolean> isExistInFavorite(String id, String mealId) {
        return mealDao.isExistsInFavourite(id,mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable clearFavoriteMealTable() {
        return mealDao.clearFavouriteMealTable().subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insertAllToFavorite(List<FavouriteMealDto> favoriteMeals) {
        return mealDao.insertAllToFavourite(favoriteMeals).subscribeOn(Schedulers.io());
    }


}
