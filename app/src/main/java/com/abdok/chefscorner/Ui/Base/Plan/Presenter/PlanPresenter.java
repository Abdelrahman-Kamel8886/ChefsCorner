package com.abdok.chefscorner.Ui.Base.Plan.Presenter;

import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Repositories.Backup.BackupRepository;
import com.abdok.chefscorner.Ui.Base.Plan.View.IPlanView;

public class PlanPresenter implements IPlanPresenter{

    private IPlanView view;
    private BackupRepository backupRepository;

    public PlanPresenter(IPlanView view) {
        this.view = view;
        this.backupRepository = BackupRepository.getInstance();
    }

    @Override
    public void getMeals(String id, DateDTO date) {
        backupRepository.getLocalPlanMeals(date,id)
                .subscribe(view::showMeals);
    }



}
