package com.abdok.chefscorner.Ui.Base.Search.Presenter;

import android.util.Log;

import com.abdok.chefscorner.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Search.View.ISearchView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class SearchPresenter implements ISearchPresenter {

    ISearchView view;
    RemoteRepository remoteRepository;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchPresenter(ISearchView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
    }


    @Override
    public void getIngredientsNames() {
       Disposable d = remoteRepository.getIngredientsNames()
                .subscribe(ingredientsNamesResponseDTO -> {
                    view.showIngredients(ingredientsNamesResponseDTO);
                }, throwable -> {
                });
       compositeDisposable.add(d);
    }

    @Override
    public void getAreasNames() {
        Disposable d = remoteRepository.getAreasNames()
                .subscribe(areasNamesResponseDTO -> {
                    Log.i("TAGlll", "getAreasNames: "+areasNamesResponseDTO.getMeals().size());
                    view.showAreaNames(areasNamesResponseDTO);
                }, throwable -> {
                });
        compositeDisposable.add(d);
    }

    @Override
    public void getCategoriesNames() {
        Disposable d = remoteRepository.getCategoriesNames()
                .subscribe(categoriesNamesResponseDTO -> {
                    view.showCategoriesNames(categoriesNamesResponseDTO);
                }, throwable -> {

                });
        compositeDisposable.add(d);
    }
}
