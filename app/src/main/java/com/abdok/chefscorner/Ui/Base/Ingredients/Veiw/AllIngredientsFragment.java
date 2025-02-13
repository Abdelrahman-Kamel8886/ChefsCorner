package com.abdok.chefscorner.Ui.Base.Ingredients.Veiw;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.abdok.chefscorner.Adapters.RecyclerIngredientsNamesAdapter;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Data.Models.IngredientsNamesResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.Ingredients.Presenter.AllIngredientsPresenter;
import com.abdok.chefscorner.Ui.Base.Ingredients.Presenter.IAllIngredientsPresenter;
import com.abdok.chefscorner.databinding.FragmentAllIngredientsBinding;

import java.util.List;


public class AllIngredientsFragment extends Fragment implements IAllIngredientsView {

    FragmentAllIngredientsBinding binding;
    RecyclerIngredientsNamesAdapter adapter;
    IBaseView baseView;
    IAllIngredientsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAllIngredientsBinding.bind(view);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.hideBottomNav();
        IngredientsNamesResponseDTO dto = AllIngredientsFragmentArgs.fromBundle(getArguments()).getIngredientsDTO();
        presenter = new AllIngredientsPresenter(this, dto.getMeals());
        initView(dto.getMeals());

    }

    private void initView(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients) {
        adapter = new RecyclerIngredientsNamesAdapter(ingredients);
        binding.recyclerview.setAdapter(adapter);

        onClicks();
    }


    private void onClicks() {
        adapter.setListener(ingredient -> navigateToDetails(ingredient));
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
    public void filterData(List<IngredientsNamesResponseDTO.IngredientDTO> ingredients) {
        adapter.setIngredients(ingredients);
    }

    private void navigateToDetails(String name) {
        Navigation
                .findNavController(requireView()).navigate(AllIngredientsFragmentDirections
                        .actionAllIngredientsFragmentToAllMealsFragment(name, SearchTypeEnum.INGREDIENT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}