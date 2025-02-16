package com.abdok.chefscorner.Ui.Base.Meal.OfflineMeal.PlanDetails.Presnter;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Ui.Base.Meal.OfflineMeal.PlanDetails.View.IPlanMDView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class PlanMealDetailsPresenter implements IPlanMDPresenter  {


    IPlanMDView view;
    BackupRepository backupRepository;


    public PlanMealDetailsPresenter(IPlanMDView view) {
        this.view = view;
        this.backupRepository = BackupRepository.getInstance();
    }


    @Override
    public void removeFromPlan(PlanMealDto planMeal) {
        backupRepository.deletePlanMealFromFirebase(planMeal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        removeFromLocal(planMeal);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.onRemoveFromPlanFailed(e.getMessage());
                    }
                });
    }

    private void removeFromLocal(PlanMealDto planMeal){
        backupRepository.deletePlanMealFromLocal(planMeal)
                .subscribe(
                        () -> view.onRemoveFromPlanSuccess("Meal Removed From Plan Successfully"),
                        throwable -> view.onRemoveFromPlanFailed(throwable.getMessage())
                );
    }
}
