package com.breiter.mynutrition.ui.mealSaved;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.breiter.mynutrition.data.MealRepository;
import com.breiter.mynutrition.data.entity.Meal;

import java.util.List;

public class SavedMealViewModel extends AndroidViewModel {
    private MealRepository mealRepository;
    private MediatorLiveData<List<Meal>> allMeals;
    private MediatorLiveData<List<Meal>> allMealsByName;


    public SavedMealViewModel(@NonNull Application application) {
        super(application);
        mealRepository = new MealRepository(application);
        allMeals = new MediatorLiveData<>();
        allMealsByName = new MediatorLiveData<>();
        getAllMeals();
    }

    private void getAllMeals() {
        allMeals.addSource(mealRepository.getSaveMeals(), meals -> allMeals.postValue(meals));
    }

    LiveData<List<Meal>> getSavedMeals() {
        return allMeals;
    }

    LiveData<List<Meal>> getMealsByName(String name) {
        allMealsByName.addSource(mealRepository.getMealByName(name), meals -> allMealsByName.postValue(meals));
        return allMealsByName;
    }

    void deleteMeal(Meal meal) {
        mealRepository.deleteMeal(meal);
    }

    void deleteAllMeals() {
        mealRepository.deleteAllMeals();
    }

    LiveData<List<Meal>> getMealsByName() {
        return mealRepository.getMealsByName();
    }
}