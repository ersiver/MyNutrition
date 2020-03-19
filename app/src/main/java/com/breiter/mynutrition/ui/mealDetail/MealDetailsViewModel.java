package com.breiter.mynutrition.ui.mealDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.breiter.mynutrition.data.IngredientRepository;
import com.breiter.mynutrition.data.MealRepository;
import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.entity.Meal;

import java.util.List;

public class MealDetailsViewModel extends AndroidViewModel {
    private MealRepository mealRepository;
    private IngredientRepository ingredientRepository;
    private MediatorLiveData<List<Ingredient>> ingredientList;

    public MealDetailsViewModel(@NonNull Application application) {
        super(application);
        mealRepository = new MealRepository(application);
        ingredientRepository = new IngredientRepository(application);
        ingredientList = new MediatorLiveData<>();
    }

    void addMeal(Meal meal) {
        mealRepository.addNewMeal(meal);
    }

    void updateMeal(Meal meal) {
        mealRepository.updateMeal(meal);
    }

    void deleteIngredient(Ingredient ingredient) {
        ingredientRepository.deleteIngredient(ingredient);
    }

    LiveData<List<Ingredient>> getMealIngredients(long id) {
        ingredientList.addSource(ingredientRepository.getMealIngredients(id),
                ingredients -> ingredientList.postValue(ingredients));

        return ingredientList;
    }

    void calculateNutritionValue(Meal meal, List<Ingredient> ingredients) {
        mealRepository.calculate(meal, ingredients);
    }
}
