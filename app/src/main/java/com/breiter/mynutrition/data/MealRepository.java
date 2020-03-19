package com.breiter.mynutrition.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.breiter.mynutrition.data.dao.MealDao;
import com.breiter.mynutrition.data.db.NutritionDatabase;
import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.entity.Meal;
import com.breiter.mynutrition.data.logic.CalculatorMealNutrition;

import java.util.List;

public class MealRepository {
    private MealDao mealDao;
    private CalculatorMealNutrition calculator;

    public MealRepository(Application application) {
        NutritionDatabase database = NutritionDatabase.getInstance(application);
        this.mealDao = database.mealDao();
        calculator = new CalculatorMealNutrition();
    }

    //Getting all saved meals sorted be defautl
    public LiveData<List<Meal>> getSaveMeals() {
        return mealDao.getSavedMeals();
    }

    public LiveData<List<Meal>> getMealsByName() {
        return mealDao.getMealsByName();
    }

    //Getting saved meals by mealId
    public LiveData<List<Meal>> getSavedMealsById(long mealId) {
        return mealDao.getMealById(mealId);
    }

    //Getting meals by name
    public LiveData<List<Meal>> getMealByName(String name) {
        return mealDao.getMealByName(name);
    }

    //Adding a new meal
    public void addNewMeal(Meal meal) {
        new AddMealAsyncTask(mealDao).execute(meal);
    }

    private static class AddMealAsyncTask extends AsyncTask<Meal, Void, Void> {
        private MealDao mealDao;

        AddMealAsyncTask(MealDao mealDao) {
            this.mealDao = mealDao;
        }

        @Override
        protected Void doInBackground(Meal... meals) {
            mealDao.addMeal(meals[0]);
            return null;
        }
    }

    //Updating a meal
    public void updateMeal(Meal meal) {
        new UpdateAsyncTask(mealDao).execute(meal);
    }

    private static class UpdateAsyncTask extends AsyncTask<Meal, Void, Void> {
        private MealDao mealDao;

        UpdateAsyncTask(MealDao mealDao) {
            this.mealDao = mealDao;
        }

        @Override
        protected Void doInBackground(Meal... meals) {
            mealDao.updateMeal(meals[0]);
            return null;
        }
    }

    //Deleting a meal
    public void deleteMeal(Meal meal) {
        new DeleteAsyncTask(mealDao).execute(meal);
    }

    private static class DeleteAsyncTask extends AsyncTask<Meal, Void, Void> {
        private MealDao mealDao;

        DeleteAsyncTask(MealDao mealDao) {
            this.mealDao = mealDao;
        }

        @Override
        protected Void doInBackground(Meal... meals) {
            this.mealDao.deleteMeal(meals[0]);
            return null;
        }
    }

    //Deleting all saved meals
    public void deleteAllMeals() {
        new DeleteAllMealsAsyncTask(mealDao).execute();
    }

    private static class DeleteAllMealsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MealDao mealDao;

        DeleteAllMealsAsyncTask(MealDao mealDao) {
            this.mealDao = mealDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mealDao.deleteAllMeals();
            return null;
        }
    }

    //Calculating whole meal nutritional value
    public void calculate(Meal meal, List<Ingredient> ingredients) {
        calculator.calculateNutritionForMeal(meal, ingredients);
    }
}
