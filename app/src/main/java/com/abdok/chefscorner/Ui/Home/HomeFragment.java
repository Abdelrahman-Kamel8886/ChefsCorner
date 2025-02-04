package com.abdok.chefscorner.Ui.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abdok.chefscorner.Adapters.RecyclerRandomAdapter;
import com.abdok.chefscorner.Models.RandomMealsDTO;
import com.abdok.chefscorner.Network.RetroConnection;
import com.abdok.chefscorner.R;
import com.abdok.chefscorner.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment implements IHomeView {

    FragmentHomeBinding binding;
    IHomePresenter presenter;
    RecyclerRandomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        presenter = new HomePresenter(this);
        presenter.getRandomMeals();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMeals(List<RandomMealsDTO.MealsDTO> meals) {
        adapter = new RecyclerRandomAdapter(meals);
        binding.randomRecycler.setAdapter(adapter);
    }
}