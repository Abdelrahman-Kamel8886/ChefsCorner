package com.abdok.chefscorner.Ui.Base.Favorites.Presenter;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Ui.Base.Favorites.View.IFavView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class FavouritesPresenter implements IFavPresenter {

    IFavView view;
    BackupRepository backupRepository;

    public FavouritesPresenter(IFavView view) {
        this.view = view;
        this.backupRepository = BackupRepository.getInstance();
    }

    @Override
    public void getFavoriteMeals(String id) {
        backupRepository.getLocalFavoriteMeals(id)
                .subscribe(
                        meals -> {
                            view.showList(meals);
                        },
                        error -> {
                            view.showError(error.getMessage());
                        }
                );
    }

    @Override
    public void removeFavoriteMeal(FavouriteMealDto meal) {
        backupRepository.deleteFavoriteMealFromFirebase(meal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        deleteFromLocal(meal);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    private void deleteFromLocal(FavouriteMealDto meal) {
        backupRepository.deleteFavoriteMealFromLocal(meal)
                .subscribe(
                        () -> {
                            view.onRemoveSuccess("Meal removed from favorites successfully");
                        }, error -> {
                            view.showError(error.getMessage());
                        });
    }
}
