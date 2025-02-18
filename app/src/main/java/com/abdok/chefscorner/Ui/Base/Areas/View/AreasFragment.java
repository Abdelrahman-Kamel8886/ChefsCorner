package com.abdok.chefscorner.Ui.Base.Areas.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.abdok.chefscorner.Ui.Adapters.RecyclerAreaNamesAdapter;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.Areas.Presenter.AreaPresenter;
import com.abdok.chefscorner.Ui.Base.Areas.Presenter.IAreaPresenter;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.databinding.FragmentAreasBinding;

import java.util.List;


public class AreasFragment extends Fragment implements IAreaView {

    FragmentAreasBinding binding;
    IBaseView baseView;

    RecyclerAreaNamesAdapter adapter;

    IAreaPresenter presenter;

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
        presenter = new AreaPresenter(this,areaDTO.getMeals());
        initView(areaDTO.getMeals());
    }

    private void initView(List<AreasNamesResponseDTO.AreaNameDTO> areas){
        adapter = new RecyclerAreaNamesAdapter(areas);
        binding.recyclerarea.setAdapter(adapter);

        onClicks();
    }

    private void onClicks(){
        adapter.setListener(area -> navigateToDetails(area));
        binding.backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.search(newText);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void filterData(List<AreasNamesResponseDTO.AreaNameDTO> areas) {
        adapter.setAreas(areas);
    }
    private void navigateToDetails(String name){
        Navigation
                .findNavController(requireView()).navigate(AreasFragmentDirections.actionAreasFragmentToAllMealsFragment(name, SearchTypeEnum.AREA));
    }
}