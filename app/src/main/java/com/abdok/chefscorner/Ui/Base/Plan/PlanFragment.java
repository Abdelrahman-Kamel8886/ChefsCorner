package com.abdok.chefscorner.Ui.Base.Plan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Adapters.RecyclerDatesAdapter;
import com.abdok.chefscorner.Adapters.RecyclerPlanMealAdapter;
import com.abdok.chefscorner.Data.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.Data.Models.DateDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Utils.Helpers.WeekHelper;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentPlanBinding;

import java.util.ArrayList;
import java.util.List;


public class PlanFragment extends Fragment {

    FragmentPlanBinding binding;
    RecyclerDatesAdapter datesAdapter;
    List<DateDTO> datesList;
    RecyclerPlanMealAdapter planMealAdapter;

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

        datesList = new ArrayList(WeekHelper.getCurrentWeek());
        planMealAdapter = new RecyclerPlanMealAdapter();
        binding.recyclerPlan.setAdapter(planMealAdapter);


        initView();
    }

    private void initView() {

        datesAdapter = new RecyclerDatesAdapter(datesList);
        binding.recyclerDates.setAdapter(datesAdapter);
        datesAdapter.setOnDateClickListener(dateDTO -> {
            binding.selectedDay.setText(dateDTO.getDay()+" , "+dateDTO.getDate());

            if(dateDTO.getDay().equals("Sat") || dateDTO.getDay().equals("Sun")){
                binding.planGroup.setVisibility(View.VISIBLE);
                binding.emptyGroup.setVisibility(View.GONE);
            }
            else {
                binding.planGroup.setVisibility(View.GONE);
                binding.emptyGroup.setVisibility(View.VISIBLE);
            }



        });
        showMeals(SharedModel.getBreakfastMeals());

    }

    private void showMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        planMealAdapter.setMeals(meals);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}