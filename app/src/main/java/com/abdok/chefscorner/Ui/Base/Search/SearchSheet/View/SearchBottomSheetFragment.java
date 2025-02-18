package com.abdok.chefscorner.Ui.Base.Search.SearchSheet.View;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.abdok.chefscorner.Ui.Adapters.RecyclerAreaNamesAdapter;
import com.abdok.chefscorner.Ui.Adapters.RecyclerCategoriesNamesAdapter;
import com.abdok.chefscorner.Ui.Adapters.RecyclerIngredientsNamesAdapter;
import com.abdok.chefscorner.Ui.Base.Search.SearchSheet.Presenter.ISearchBSPresenter;
import com.abdok.chefscorner.Ui.Base.Search.SearchSheet.Presenter.SearchSheetPresenter;
import com.abdok.chefscorner.Models.AreasNamesResponseDTO;
import com.abdok.chefscorner.Models.CategoriesNamesResponseDTO;
import com.abdok.chefscorner.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.databinding.BottomSheetSearchBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchBottomSheetFragment extends BottomSheetDialogFragment implements ISearchBSView{

    private BottomSheetSearchBinding binding;

    RecyclerIngredientsNamesAdapter ingredientsNamesAdapter;
    RecyclerCategoriesNamesAdapter categoriesNamesAdapter;
    RecyclerAreaNamesAdapter areasNamesAdapter;
    GridLayoutManager layoutManager;

    ISearchBSPresenter presenter;

    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                bottomSheet.requestLayout();
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SearchSheetPresenter(this);
        layoutManager = new GridLayoutManager(requireContext(),2);
        binding.recyclerSearch.setLayoutManager(layoutManager);

        areasNamesAdapter = new RecyclerAreaNamesAdapter(new ArrayList<>());
        categoriesNamesAdapter = new RecyclerCategoriesNamesAdapter(new ArrayList<>());
        ingredientsNamesAdapter = new RecyclerIngredientsNamesAdapter(new ArrayList<>());

        onClicks();
    }


    private void onClicks(){
        binding.closeBtn.setOnClickListener(v -> dismiss());


        binding.chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                binding.searchView.setQuery(null,true);
                presenter.resetSearch();
                if (checkedIds.size()!=0){
                    if (checkedIds.get(0) == R.id.categoriesChip){
                        layoutManager.setSpanCount(2);
                        binding.recyclerSearch.setAdapter(categoriesNamesAdapter);
                    }
                    else if (checkedIds.get(0) == R.id.areasChip){
                        layoutManager.setSpanCount(3);
                        binding.recyclerSearch.setAdapter(areasNamesAdapter);
                    }
                    else if (checkedIds.get(0) == R.id.ingredientsChip){
                        layoutManager.setSpanCount(3);
                        binding.recyclerSearch.setAdapter(ingredientsNamesAdapter);
                    }
                    binding.recyclerSearch.setVisibility(View.VISIBLE);
                }
                else{
                    binding.recyclerSearch.setVisibility(View.GONE);
                }
            }
        });

        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                binding.chipGroup.clearCheck();
                hideSearchingView();
                return false;
            }
        });

        binding.searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchingView();
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (binding.chipGroup.getCheckedChipId() == R.id.categoriesChip){
                    presenter.searchOnCategories(newText);
                }
                else if (binding.chipGroup.getCheckedChipId() == R.id.areasChip){
                    presenter.searchOnAreas(newText);
                }
                else if (binding.chipGroup.getCheckedChipId() == R.id.ingredientsChip){
                    presenter.searchOnIngredients(newText);
                }
                return false;
            }
        });

        categoriesNamesAdapter.setOnItemClickListener(category -> {
            onItemClickListener.onCategoryClick(category.getStrCategory());
            dismiss();
        });

        areasNamesAdapter.setListener(area -> {
            onItemClickListener.onAreaClick(area);
            dismiss();
        });

        ingredientsNamesAdapter.setListener(ingredient -> {
            onItemClickListener.onIngredientClick(ingredient);
            dismiss();
        });

    }

    private void showSearchingView(){
        binding.chipGroup.setVisibility(View.VISIBLE);
//        binding.recyclerSearch.setVisibility(View.VISIBLE);
    }

    private void hideSearchingView(){
        binding.chipGroup.setVisibility(View.GONE);
        binding.recyclerSearch.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void filterAreasData(List<AreasNamesResponseDTO.AreaNameDTO> areas) {
        areasNamesAdapter.setAreas(areas);
    }

    @Override
    public void filterIngredientsData(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients) {
        ingredientsNamesAdapter.setIngredients(ingredients);
    }

    @Override
    public void filterCategoriesData(List<CategoriesNamesResponseDTO.CategoryNameDTO> categories) {
        categoriesNamesAdapter.setList(categories);
    }

    public interface OnItemClickListener {
        void onCategoryClick(String category);
        void onAreaClick(String area);
        void onIngredientClick(String ingredient);
    }

}
