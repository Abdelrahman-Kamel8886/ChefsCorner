package com.abdok.chefscorner.Ui.Base.Areas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.Adapters.RecyclerAreaNamesAdapter;
import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.databinding.FragmentAreasBinding;


public class AreasFragment extends Fragment {

    FragmentAreasBinding binding;
    IBaseView baseView;

    RecyclerAreaNamesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_areas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAreasBinding.bind(view);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.hideBottomNav();
        AreasNamesResponseDTO areaDTO = AreasFragmentArgs.fromBundle(getArguments()).getAreaDTO();
        adapter = new RecyclerAreaNamesAdapter(areaDTO.getMeals());
        binding.recyclerarea.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}