package com.abdok.chefscorner.Network;

import com.abdok.chefscorner.Models.RandomMealsDTO;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RetroServices {

    @GET("random.php")
    Single<RandomMealsDTO> getRandomMeal();
}
