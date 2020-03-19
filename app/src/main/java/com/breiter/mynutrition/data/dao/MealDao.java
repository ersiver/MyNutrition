package com.breiter.mynutrition.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.breiter.mynutrition.data.entity.Meal;

import java.util.List;

@Dao
public interface MealDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addMeal(Meal meal);

    @Update
    void updateMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("DELETE FROM meals")
    void deleteAllMeals();

    @Query("SELECT * FROM meals ORDER BY mealId DESC")
    LiveData<List<Meal>> getSavedMeals();

    @Query("SELECT * FROM meals ORDER BY mealName ASC")
    LiveData<List<Meal>> getMealsByName();

    @Query("SELECT * FROM meals WHERE mealId=:mealId")
    LiveData<List<Meal>> getMealById(long mealId);

    @Query("SELECT * FROM meals WHERE mealName LIKE  '%' || :mealName || '%'  COLLATE NOCASE")
    LiveData<List<Meal>> getMealByName(String mealName);
}
