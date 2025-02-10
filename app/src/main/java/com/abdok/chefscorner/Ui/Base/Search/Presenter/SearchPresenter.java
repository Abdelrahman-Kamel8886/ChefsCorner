package com.abdok.chefscorner.Ui.Base.Search.Presenter;

import com.abdok.chefscorner.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Search.View.ISearchView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
                }, throwable -> {
                });
       compositeDisposable.add(d);
    }

    @Override
    public void getAreasNames() {
        Disposable d = remoteRepository.getAreasNames()
                .subscribe(areasNamesResponseDTO -> {
                }, throwable -> {
                });
        compositeDisposable.add(d);
    }

    @Override
    public void getCategoriesNames() {
        Disposable d = remoteRepository.getCategoriesNames()
                .subscribe(categoriesNamesResponseDTO -> {
                }, throwable -> {
                });
        compositeDisposable.add(d);
    }
}
