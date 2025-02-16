package com.abdok.chefscorner.Ui.Base.Meal.MealDetails.Presenter;

import androidx.annotation.NonNull;

import com.abdok.chefscorner.Data.Models.FavouriteMealDto;
import com.abdok.chefscorner.Data.Models.MealDTO;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Data.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Meal.MealDetails.View.IMealDetailsView;
import com.abdok.chefscorner.Utils.SharedModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class MealDetailsPresenter implements IMealDetailsPresenter {

    private IMealDetailsView view;
    private RemoteRepository repository;
    private BackupRepository backupRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MealDetailsPresenter(IMealDetailsView view) {
        this.view = view;
        this.repository = RemoteRepository.getInstance();
        this.backupRepository = BackupRepository.getInstance();
    }

    @Override
    public void getMealDetails(int id) {

        Disposable disposable = repository.getMealDetails(id)
                .subscribe(
                        mealDTO -> {
                            view.InitData(mealDTO);
                        },
                        throwable -> {
                            view.showInformation(throwable.getMessage());
                        }
                );
        compositeDisposable.add(disposable);

    }

    @Override
    public void clearDisposable() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

    @Override
    public void addToFav(MealDTO mealDTO) {
        String id = SharedModel.getUser().getId();
        FavouriteMealDto favouriteMealDto = new FavouriteMealDto(mealDTO, mealDTO.getIdMeal(), id);
        backupRepository.saveFavoriteMealToFirebase(favouriteMealDto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        addToFavLocal(favouriteMealDto);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    private void addToFavLocal(FavouriteMealDto meal) {
        Disposable disposable = backupRepository.saveFavoriteMealToLocal(meal)
                .subscribe(
                        () -> {
                            view.onAddedToFavSuccess("Meal added to favourites");
                        },
                        throwable -> {
                            view.showError(throwable.getMessage());
                        }
                );
    }

}
