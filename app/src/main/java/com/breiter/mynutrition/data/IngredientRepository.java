package com.breiter.mynutrition.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.breiter.mynutrition.data.dao.IngredientDao;
import com.breiter.mynutrition.data.db.NutritionDatabase;
import com.breiter.mynutrition.data.logic.CalculatorIngredientNutrition;
import com.breiter.mynutrition.data.net.DatabaseResponse;
import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.net.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientRepository {
    private static final String TAG = "IngredientRepository";
    private IngredientDao ingredientDao;
    private CalculatorIngredientNutrition calculator;

    public IngredientRepository(Application application) {
        NutritionDatabase database = NutritionDatabase.getInstance(application);
        ingredientDao = database.ingredientDao();
        calculator = new CalculatorIngredientNutrition();
    }

    //Search for a new ingredient in the FoodDatabase
    public LiveData<List<Ingredient>> searchIngredient(final String search) {
        final MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();
        Call<DatabaseResponse> call = RetrofitClient.searchIngredients();
        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseResponse> call, @NonNull Response<DatabaseResponse> response) {
                if (response.isSuccessful()) {
                    List<Ingredient> matchingSearch = new ArrayList<>();
                    DatabaseResponse databaseResponse = response.body();
                    if (databaseResponse != null) {
                        for (Ingredient ingredient : databaseResponse.getIngredients()) {
                            if (ingredient.getName().toLowerCase().startsWith(search.toLowerCase()))
                                matchingSearch.add(ingredient);
                        }
                        ingredients.setValue(matchingSearch);
                    }
                } else
                    Log.i(TAG, "Error body: " + response.message());
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "On failure: " + t.getMessage());

            }
        });

        return ingredients;
    }

    //Search for a new ingredient by component ingredient in the FoodDatabase
    public LiveData<List<Ingredient>> searchIngredientByComponent(final String search) {
        final MutableLiveData<List<Ingredient>> ingredients = new MutableLiveData<>();
        Call<DatabaseResponse> call = RetrofitClient.searchIngredients();
        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseResponse> call, @NonNull Response<DatabaseResponse> response) {
                if (response.isSuccessful()) {
                    List<Ingredient> matchingSearch = new ArrayList<>();
                    DatabaseResponse databaseResponse = response.body();
                    if (databaseResponse != null) {

                        for (Ingredient ingredient : databaseResponse.getIngredients()) {
                            if (ingredient.getDescription().toLowerCase().contains(search.toLowerCase().trim()))
                                matchingSearch.add(ingredient);
                        }
                        ingredients.setValue(matchingSearch);
                    }
                } else
                    Log.i(TAG, "Error body: " + response.message());
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "On failure: " + t.getMessage());

            }
        });

        return ingredients;
    }

    //Geting all saved ingredients for meal
    public LiveData<List<Ingredient>> getMealIngredients(long mealId) {
        return ingredientDao.getMealIngredients(mealId);
    }

    //Adding new ingredient
    public void addIngredient(Ingredient ingredient) {
        new AddAsyncTask(ingredientDao).execute(ingredient);
    }

    private static class AddAsyncTask extends AsyncTask<Ingredient, Void, Void> {
        private IngredientDao ingredientDao;

        AddAsyncTask(IngredientDao ingredientDao) {
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(Ingredient... ingredients) {
            ingredientDao.addIngredient(ingredients[0]);
            return null;
        }
    }

    //Updating
    public void updateIngredient(Ingredient ingredient) {
        new UpdateAsyncTask(ingredientDao).execute(ingredient);
    }

    private static class UpdateAsyncTask extends AsyncTask<Ingredient, Void, Void> {
        private IngredientDao ingredientDao;

        UpdateAsyncTask(IngredientDao ingredientDao) {
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(Ingredient... ingredients) {
            ingredientDao.updateIngredient(ingredients[0]);
            return null;
        }
    }

    //Deleting
    public void deleteIngredient(Ingredient ingredient) {
        new DeleteAsyncTask(ingredientDao).execute(ingredient);
    }

    private static class DeleteAsyncTask extends AsyncTask<Ingredient, Void, Void> {
        private IngredientDao ingredientDao;

        DeleteAsyncTask(IngredientDao ingredientDao) {
            this.ingredientDao = ingredientDao;
        }

        @Override
        protected Void doInBackground(Ingredient... ingredients) {
            this.ingredientDao.deleteIngredient(ingredients[0]);
            return null;
        }
    }

    //Calculating nutritional values
    public void calculate(Ingredient ingredient) {
        calculator.calculateNutritionForIngredient(ingredient);
    }

}
