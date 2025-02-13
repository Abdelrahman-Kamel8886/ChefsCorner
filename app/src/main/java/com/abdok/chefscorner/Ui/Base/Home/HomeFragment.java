package com.abdok.chefscorner.Ui.Base.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdok.chefscorner.Adapters.RecyclerCategoryMealAdapter;
import com.abdok.chefscorner.Adapters.RecyclerRandomAdapter;
import com.abdok.chefscorner.CustomViews.DatePickerBottomSheet;
import com.abdok.chefscorner.Models.MealDTO;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.Models.CategoryMealsResponseDTO;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Utils.SharedModel;
import com.abdok.chefscorner.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeFragment extends Fragment implements IHomeView {

    FragmentHomeBinding binding;
    IHomePresenter presenter;
    RecyclerRandomAdapter adapter;
    RecyclerCategoryMealAdapter breakfastAdapter , desertAdapter;
    IBaseView baseView;

    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        presenter = new HomePresenter(this);
        baseView = (IBaseView) getParentFragment().getParentFragment();

        myRef = FirebaseDatabase.getInstance().getReference("PlanMeals");
        checkForData();
    }

    private void checkForData(){
        if (SharedModel.getRandomMeals()==null||SharedModel.getBreakfastMeals()==null||SharedModel.getDesertMeals()==null){
            presenter.start();
        }
        else{
            initView();
        }
    }


    private void getData(){
        myRef.child(SharedModel.getUser().getId()).child("MyPlan").child("13-2-2025")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            MealDTO mealDTO = dataSnapshot.getValue(MealDTO.class);
                            Log.e("TAGFire", "onDataChange: "+mealDTO.getStrMeal());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void delete(){
        myRef.child(SharedModel.getUser().getId()).child("MyPlan").child("13-2-2025").child("52835")
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })

        ;
    }



    @Override
    public void initView() {
        showRandomMeals(SharedModel.getRandomMeals());
        showBreakFastMeals(SharedModel.getBreakfastMeals());
        showDesertMeals(SharedModel.getDesertMeals());
    }


    @Override
    public void showMessage(String message) {
        Snackbar.make(requireView(),message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMeals(List<MealDTO> meals) {
        adapter = new RecyclerRandomAdapter(meals);
        binding.randomRecycler.setAdapter(adapter);
        binding.randomRecycler.setAlpha(true);
        binding.randomRecycler.setInfinite(false);
    }

    @Override
    public void showBreakFastMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        breakfastAdapter = new RecyclerCategoryMealAdapter(meals);
        binding.breakFastRecycler.setAdapter(breakfastAdapter);

    }

    @Override
    public void showDesertMeals(List<CategoryMealsResponseDTO.CategoryMealDTO> meals) {
        desertAdapter = new RecyclerCategoryMealAdapter(meals);
        binding.desertsRecycler.setAdapter(desertAdapter);

        if (baseView!=null){
            baseView.showMainView();
            onClicks();
        }
    }

    private void onClicks(){
        adapter.setListener(new RecyclerRandomAdapter.onItemClickListener() {
            @Override
            public void onItemClick(MealDTO mealDTO) {
                onRandomItemClick(mealDTO);
            }

            @Override
            public void onAddToPlanClick(MealDTO mealDTO) {
                showDatePicker();
            }
        });
        breakfastAdapter.setOnItemClickListener(this::onRandomItemClick);
        desertAdapter.setOnItemClickListener(this::onRandomItemClick);
    }

    private void showDatePicker(){
        DatePickerBottomSheet datePickerBottomSheet = new DatePickerBottomSheet();
        datePickerBottomSheet.show(getChildFragmentManager(),datePickerBottomSheet.getTag());
        datePickerBottomSheet.setOnDateSelectedListener(dateDTO -> {
            showMessage(dateDTO.getDate()+" "+dateDTO.getDay()+" "+dateDTO.getSubDate());
        });
    }

    private void onRandomItemClick(int id){
        navigateToDetails(id , null);
    }

    private void onRandomItemClick(MealDTO mealDTO){
        int id = Integer.parseInt(mealDTO.getIdMeal());
        navigateToDetails(id,mealDTO);
    }

    private void navigateToDetails(int id , MealDTO mealDTO){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(id,mealDTO));
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.clearDisposable();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}