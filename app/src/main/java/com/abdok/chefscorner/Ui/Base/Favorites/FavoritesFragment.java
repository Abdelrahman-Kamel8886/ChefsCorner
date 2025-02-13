package com.abdok.chefscorner.Ui.Base.Favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdok.chefscorner.R;
import com.abdok.chefscorner.Ui.Base.IBaseView;
import com.abdok.chefscorner.databinding.FragmentFavoritesBinding;


public class FavoritesFragment extends Fragment {

    FragmentFavoritesBinding binding;
    IBaseView baseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentFavoritesBinding.bind(view);
        baseView = (IBaseView) getParentFragment().getParentFragment();
        baseView.showMainView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}