package com.breiter.mynutrition.ui.ingredientDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.breiter.mynutrition.data.IngredientRepository;
import com.breiter.mynutrition.data.entity.Ingredient;

public class IngredientDetailsViewModel extends AndroidViewModel {
    private IngredientRepository repository;

    public IngredientDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new IngredientRepository(application);
    }

    void addIngredient(Ingredient ingredient) {
        repository.addIngredient(ingredient);
    }

    void updateIngredient(Ingredient ingredient) {
        repository.updateIngredient(ingredient);
    }

    void calculateNutritionValue(Ingredient ingredient) {
        repository.calculate(ingredient);
    }
}