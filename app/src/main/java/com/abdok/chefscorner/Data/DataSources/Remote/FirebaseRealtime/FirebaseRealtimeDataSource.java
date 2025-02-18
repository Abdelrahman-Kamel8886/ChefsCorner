package com.abdok.chefscorner.Data.DataSources.Remote.FirebaseRealtime;

import com.abdok.chefscorner.Models.FavouriteMealDto;
import com.abdok.chefscorner.Models.PlanMealDto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealtimeDataSource {

    private static final String ROOT_REF = "Root";
    private static final String PLAN_MEALS = "PlanMeals";
    private static final String FAVORITE_MEALS = "FavoriteMeals";

    private static FirebaseRealtimeDataSource instance;

    private DatabaseReference reference;

    private FirebaseRealtimeDataSource() {
        reference = FirebaseDatabase.getInstance().getReference().child(ROOT_REF);
    }

    public static synchronized FirebaseRealtimeDataSource getInstance() {
        if (instance == null) {
            instance = new FirebaseRealtimeDataSource();

        }
        return instance;
    }

    //Plan Meals

    public Task<Void> savePlanMeal(PlanMealDto dto) {
        return reference.child(PLAN_MEALS).child(dto.getId()).child(dto.getDate().getDate()).child(dto.getMeal().getIdMeal()).setValue(dto);
    }
    public Task<Void> deletePlanMeal(PlanMealDto dto) {
        return reference.child(PLAN_MEALS).child(dto.getId()).child(dto.getDate().getDate()).child(dto.getMeal().getIdMeal()).removeValue();
    }
    public DatabaseReference getPlanMeals(String id) {
        return reference.child(PLAN_MEALS).child(id);
    }

    // Favorite Meals


    public Task<Void> saveFavoriteMeal(FavouriteMealDto dto) {
        return reference.child(FAVORITE_MEALS).child(dto.getId()).child(dto.getMeal().getIdMeal()).setValue(dto);
    }
    public Task<Void> deleteFavoriteMeal(FavouriteMealDto dto) {
        return reference.child(FAVORITE_MEALS).child(dto.getId()).child(dto.getMeal().getIdMeal()).removeValue();
    }

    public DatabaseReference getFavoriteMeals(String id) {
        return reference.child(FAVORITE_MEALS).child(id);
    }


}
