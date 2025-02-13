package com.abdok.chefscorner.Ui.Base.Areas.Presenter;

import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Ui.Base.Areas.View.IAreaView;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class AreaPresenter implements IAreaPresenter{

    IAreaView view;
    List<AreasNamesResponseDTO.AreaNameDTO> areas;

    public AreaPresenter(IAreaView view, List<AreasNamesResponseDTO.AreaNameDTO> areas) {
        this.view = view;
        this.areas = areas;
    }

    @Override
    public void search(String query) {
        Observable<AreasNamesResponseDTO.AreaNameDTO> observable = Observable.fromIterable(areas);
        observable
                .filter(item -> item.getStrArea().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(areas -> view.filterData(areas));
    }
}
