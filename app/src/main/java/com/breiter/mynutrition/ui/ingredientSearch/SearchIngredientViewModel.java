package com.breiter.mynutrition.ui.ingredientSearch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.breiter.mynutrition.data.IngredientRepository;
import com.breiter.mynutrition.data.entity.Ingredient;

import java.util.List;

public class SearchIngredientViewModel extends AndroidViewModel {
    private IngredientRepository repository;

    public SearchIngredientViewModel(@NonNull Application application) {
        super(application);
        repository = new IngredientRepository(application);
    }

    LiveData<List<Ingredient>> searchIngredient(String search) {
        return repository.searchIngredient(search);
    }

    LiveData<List<Ingredient>> searchIngredientByComponent(String search) {
        return repository.searchIngredientByComponent(search);
    }
}
