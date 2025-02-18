package com.abdok.chefscorner.Ui.Base.FavouriteDetails.Presenter;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Ui.Base.FavouriteDetails.View.IFavMDView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class FavMealDetailsPresenter implements IFavMDPresenter {

    IFavMDView view;
    BackupRepository backupRepository;

    public FavMealDetailsPresenter(IFavMDView view) {
        this.view = view;
        backupRepository = BackupRepository.getInstance();
    }

    @Override
    public void removeFromFav(FavouriteMealDto favouriteMealDto) {
        backupRepository.deleteFavoriteMealFromFirebase(favouriteMealDto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        removeFromFavLocal(favouriteMealDto);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.onRemoveFromFavFailed(e.getMessage());
                    }
                });
    }

    private void removeFromFavLocal(FavouriteMealDto favouriteMealDto){
        backupRepository.deleteFavoriteMealFromLocal(favouriteMealDto)
                .subscribe(
                        () -> view.onRemoveFromFavSuccess("Meal removed from favourites successfully"),
                        throwable -> view.onRemoveFromFavFailed(throwable.getMessage())
                );
    }
}
