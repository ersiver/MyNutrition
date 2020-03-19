package com.breiter.mynutrition.data.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.breiter.mynutrition.data.dao.IngredientDao;
import com.breiter.mynutrition.data.dao.MealDao;
import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.entity.Meal;

@Database(entities = {Meal.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class NutritionDatabase extends RoomDatabase {
    private static NutritionDatabase instance;

    public static synchronized NutritionDatabase getInstance(Application application) {
        if (instance == null)
            instance = Room.databaseBuilder(application.getApplicationContext(),
                    NutritionDatabase.class, "nutrition_database")
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }
    public abstract IngredientDao ingredientDao();

    public abstract MealDao mealDao();
}
