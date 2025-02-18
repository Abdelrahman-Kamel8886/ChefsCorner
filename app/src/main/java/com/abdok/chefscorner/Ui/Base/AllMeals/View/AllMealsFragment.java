package com.abdok.chefscorner.Ui.Base.AllMeals.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.abdok.chefscorner.Ui.Adapters.RecyclerAllMealAdapter;
import com.abdok.chefscorner.Enums.SearchTypeEnum;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Ui.Base.AllMeals.Presenter.AllMealsPresenter;
import com.abdok.chefscorner.Ui.Base.AllMeals.Presenter.IAllMealsPresenter;
import com.abdok.chefscorner.Utils.Helpers.CountryFlagMapper;
import com.abdok.chefscorner.Utils.Helpers.SnackBarHelper;
import com.abdok.chefscorner.databinding.FragmentAllMealsBinding;

import java.util.List;


public class AllMealsFragment extends Fragment implements IAllMealsView {

    FragmentAllMealsBinding binding;
    RecyclerAllMealAdapter adapter;
    IAllMealsPresenter presenter;
    IBaseView baseView;

    public static final String CATEGORY_EMOJI = "🍽️";
    public static final String INGREDIENT_EMOJI = "🥕";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAGMeals", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("TAGMeals", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_all_meals, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAllMealsBinding.bind(view);
        presenter = new AllMealsPresenter(this);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.hideBottomNav();
        String title = AllMealsFragmentArgs.fromBundle(getArguments()).getTitle();
        SearchTypeEnum type = AllMealsFragmentArgs.fromBundle(getArguments()).getType();
        initView(title,type);
        Log.e("TAGMeals", "onViewCreated: " );
    }

    private void initView(String title, SearchTypeEnum type){
        String str = title+" "+type.getValue();
        String code = "";
        if (type==SearchTypeEnum.AREA){
            code = CountryFlagMapper.getFlagEmoji(title)+ " ";
            code+=str;
        }
        else {
            code = str;
        }

        binding.title.setText(code);
        presenter.getMeals(title,type);
    }


    @Override
    public void showMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        adapter = new RecyclerAllMealAdapter(meals);
        binding.recyclerview.setAdapter(adapter);
        showMainView();
        onClicks();
    }

    @Override
    public void filterData(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        adapter.setMeals(meals);
    }

    @Override
    public void navigateUp() {
        SnackBarHelper.showCustomSnackBar(requireActivity(),getString(R.string.no_internet_connection), R.color.errorRed, Gravity.TOP);
        Navigation.findNavController(requireView()).navigateUp();
    }

    private void onClicks(){
        adapter.setOnItemClickListener(id ->navigateToDetails(id));
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

    private void showMainView(){
        binding.loadingLayout.setVisibility(View.GONE);
        binding.viewGroup.setVisibility(View.VISIBLE);
    }

    private void navigateToDetails(int id){
        Navigation.findNavController(requireView()).navigate(AllMealsFragmentDirections.actionAllMealsFragmentToMealDetailsFragment(id,null));
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAGMeals", "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("TAGMeals", "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Log.i("TAGMeals", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAGMeals", "onDestroy: ");
    }
}