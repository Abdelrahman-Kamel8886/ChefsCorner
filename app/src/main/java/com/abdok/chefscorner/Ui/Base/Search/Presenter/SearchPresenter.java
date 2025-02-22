package com.abdok.chefscorner.Ui.Base.Search.Presenter;

import com.abdok.chefscorner.Data.Repositories.Remote.RemoteRepository;
import com.abdok.chefscorner.Ui.Base.Search.View.ISearchView;
import com.abdok.chefscorner.Utils.SharedModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;


public class SearchPresenter implements ISearchPresenter{

    private ISearchView view;
    private RemoteRepository remoteRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchPresenter(ISearchView view) {
        this.view = view;
        remoteRepository = RemoteRepository.getInstance();
    }


    @Override
    public void getIngredientsNames() {
       Disposable d = remoteRepository.getIngredientsNames()
                .subscribe(ingredientsNamesResponseDTO -> {
                    SharedModel.setIngredientsNamesResponse(ingredientsNamesResponseDTO);
                    view.showIngredients(ingredientsNamesResponseDTO);
                }, throwable -> {view.showError();
                });
       compositeDisposable.add(d);
    }

    @Override
    public void getAreasNames() {
        Disposable d = remoteRepository.getAreasNames()
                .subscribe(areasNamesResponseDTO -> {
                    SharedModel.setAreasNamesResponse(areasNamesResponseDTO);
                    view.showAreaNames(areasNamesResponseDTO);
                }, throwable -> {view.showError();});
        compositeDisposable.add(d);
    }

    @Override
    public void getCategoriesNames() {
        Disposable d = remoteRepository.getCategoriesNames()
                .subscribe(categoriesNamesResponseDTO -> {
                    SharedModel.setCategoriesNamesResponse(categoriesNamesResponseDTO);
                    view.showCategoriesNames(categoriesNamesResponseDTO);
                }, throwable -> {view.showError();

                });
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy(){
        compositeDisposable.dispose();
    }

}
