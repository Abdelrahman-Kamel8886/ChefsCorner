package com.abdok.chefscorner.Ui.Base.Plan.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Adapters.RecyclerDatesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerPlanMealAdapter;
import com.abdok.chefscorner.Data.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.Data.Models.PlanMealDto;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Plan.Presenter.IPlanPresenter;
import com.abdok.chefscorner.Ui.Base.Plan.Presenter.PlanPresenter;
import com.abdok.chefscorner.Utils.Helpers.WeekHelper;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentPlanBinding;

import java.util.ArrayList;
import java.util.List;


public class PlanFragment extends Fragment implements IPlanView {

    FragmentPlanBinding binding;
    RecyclerDatesAdapter datesAdapter;
    List<DateDTO> datesList;
    RecyclerPlanMealAdapter planMealAdapter;

    IPlanPresenter presenter;

    IBaseView baseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentPlanBinding.bind(view);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.showMainView();
        presenter = new PlanPresenter(this);
        datesList = new ArrayList(WeekHelper.getCurrentWeek());
        planMealAdapter = new RecyclerPlanMealAdapter();
        binding.recyclerPlan.setAdapter(planMealAdapter);

        if (SharedModel.getUser() == null){
            binding.userView.setVisibility(View.GONE);
            binding.guestView.setVisibility(View.VISIBLE);
        }
        else{
            binding.userView.setVisibility(View.VISIBLE);
            binding.guestView.setVisibility(View.GONE);

        }

        initView();
    }

    private void initView() {
        datesAdapter = new RecyclerDatesAdapter(datesList);
        binding.recyclerDates.setAdapter(datesAdapter);
        datesAdapter.setOnDateClickListener(dateDTO -> {
            binding.selectedDay.setText(dateDTO.getDay()+" , "+dateDTO.getDate());
            presenter.getMeals(SharedModel.getUser().getId(), dateDTO);
        });
    }
    @Override
    public void showMeals(List<PlanMealDto> meals) {
        if (meals.size()==0){
            binding.emptyGroup.setVisibility(View.VISIBLE);
            binding.planGroup.setVisibility(View.GONE);
        }
        else{
            String count = meals.size()<9 ? meals.size()+" items" : meals.size()+" item";
            binding.itemsCount.setText(count);
            binding.emptyGroup.setVisibility(View.GONE);
            binding.planGroup.setVisibility(View.VISIBLE);
            planMealAdapter.setMeals(meals);
            planMealAdapter.setOnItemClickListener(planMealDto -> {
                navigateToMealDetails(planMealDto);
            });
        }

    }

    private void navigateToMealDetails(PlanMealDto meal){
        Navigation.findNavController(requireView()).navigate(PlanFragmentDirections.actionPlanFragmentToOfflineMealDetailsFragment(meal));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}