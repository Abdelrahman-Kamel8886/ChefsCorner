package com.abdok.chefscorner.Data.DataSources.Remote.FirebaseRealtime;

import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealtimeDataSource {

    private static final String ROOT_REF = "Root";
    private static final String USER_REF = "Users";

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

    public Task<Void> savePlanMeal(PlanMealDto dto) {
        return reference.child(USER_REF).child(dto.getId()).child(dto.getDate().getDate()).child(dto.getMeal().getIdMeal()).setValue(dto);
    }
    public Task<Void> deletePlanMeal(PlanMealDto dto) {
        return reference.child(USER_REF).child(dto.getId()).child(dto.getMeal().getIdMeal()).removeValue();
    }
    public DatabaseReference getPlanMeals(String id) {
        return reference.child(USER_REF).child(id);
    }
}
