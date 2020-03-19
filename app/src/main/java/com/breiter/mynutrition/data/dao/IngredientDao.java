package com.breiter.mynutrition.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.breiter.mynutrition.data.entity.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void addIngredient(Ingredient ingredient);

    @Update
    void updateIngredient(Ingredient ingredient);

    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Query("SELECT * FROM ingredients WHERE mealContainingId=:id")
    LiveData<List<Ingredient>> getMealIngredients(long id);
}
