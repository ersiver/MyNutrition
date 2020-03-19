package com.breiter.mynutrition.ui.mealDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.entity.Meal;
import com.breiter.mynutrition.ui.ingredientDetail.IngredientDetailsActivity;
import com.breiter.mynutrition.ui.ingredientSearch.SearchIngredientActivity;
import com.breiter.mynutrition.ui.mealNutrition.MealNutritionValueActivity;
import com.breiter.mynutrition.ui.mealSaved.SavedMealsActivity;
import com.breiter.mynutrition.ui.tool.ItemSwipeHelper;
import com.google.gson.Gson;

import java.util.List;

import static com.breiter.mynutrition.ui.tool.DoubleToString.convert;

public class MealDetailsActivity extends AppCompatActivity {
    private static final String MEAL = "meal";
    private static final String EDIT = "edit";
    private static final String INGREDIENT = "ingredient";

    private boolean isNewMeal = true;
    private Meal meal;
    private MealDetailsViewModel viewModel;
    private RecyclerView recyclerView;
    private MealDetailsAdapter adapter;
    private ImageView searchIngredientsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        getDataFromIntent();           //1
        setToolbarTitle();             //2
        displayMealName();             //3
        initRecycler();                //4
        getIngredients();              //5
        clickToDisplayNutritionValue();//6
        clickToSearchNewIngredient();  //7
        clickToSeeSavedIngredient();   //8
        clickToGoBack();               //9
        swipeToDeleteIngredient();     //10
    }

    //1
    private void getDataFromIntent() {
        Intent intent = getIntent();
        Gson gson = new Gson();
        String json = intent.getStringExtra(MEAL); //from SavedMealsActivity or AddIngredient
        meal = gson.fromJson(json, Meal.class);

        determineIfNewOrSaved(intent); //1a
        savedMealIfNew();              //1b
    }

    //1a
    private void determineIfNewOrSaved(Intent intent) {
        isNewMeal = !intent.hasExtra(EDIT);
    }

    //1b
    private void savedMealIfNew() {
        viewModel = new ViewModelProvider(this).get(MealDetailsViewModel.class);
        if (isNewMeal)
            viewModel.addMeal(meal);
    }

    //2
    private void setToolbarTitle() {
        TextView titleToolbarTextView = findViewById(R.id.titleToolbarTextView);
        if (isNewMeal)
            titleToolbarTextView.setText(R.string.create_meal);
        else
            titleToolbarTextView.setText(R.string.edit_meal);
    }

    //3
    private void displayMealName() {
       TextView nameTextView = findViewById(R.id.nameTextView);
       nameTextView.setText(meal.getMealName());
    }

    //4
    private void initRecycler() {
        recyclerView = findViewById(R.id.mealIngredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new MealDetailsAdapter();
        recyclerView.setAdapter(adapter);
    }

    //5
    private void getIngredients() {
        viewModel.getMealIngredients(meal.getMealId()).observe(this, ingredients -> {
            if (ingredients != null && !ingredients.isEmpty()) {
                adapter.setIngredients(ingredients);
                viewModel.calculateNutritionValue(meal, ingredients);
                viewModel.updateMeal(meal);
                displayCalories(); //5a
                hideButtonOnScroll(); //5b
            }
        });
    }

    //5a
    private void displayCalories() {
        RelativeLayout caloriesLayout = findViewById(R.id.caloriesLayout);
        TextView kcalTextView = findViewById(R.id.kcalTextView);
        if (meal.getEnergyKcal()!=0.0) {
            caloriesLayout.setVisibility(View.VISIBLE);
            kcalTextView.setText(convert(meal.getEnergyKcal(),0));
        }
    }

    //5b
    private void hideButtonOnScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0)
                    searchIngredientsImageView.setVisibility(View.VISIBLE);
                else if (dy > 0)
                    searchIngredientsImageView.setVisibility(View.GONE);
                else if (dy < -5)
                    searchIngredientsImageView.setVisibility(View.VISIBLE);
            }
        });
    }

    //6
    private void clickToDisplayNutritionValue() {
        Button detailNutritionButton = findViewById(R.id.detailNutritionButton);
        detailNutritionButton.setOnClickListener(v -> {
            goToMealNutritionValueActivity(); //6a
        });
    }

    //6a
    private void goToMealNutritionValueActivity() {
        Intent intent = new Intent(this, MealNutritionValueActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        intent.putExtra(MEAL, json);
        startActivity(intent);
    }

    //7
    private void clickToSearchNewIngredient() {
        searchIngredientsImageView = findViewById(R.id.searchIngredientsImageView);
        searchIngredientsImageView.setOnClickListener(v -> {
            goToSearchIngredientActivity(); //7a
        });
    }

    //7a
    private void goToSearchIngredientActivity() {
        Intent intent = new Intent(this, SearchIngredientActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        intent.putExtra(MEAL, json);
        startActivity(intent);
    }

    //8
    private void clickToSeeSavedIngredient() {
        adapter.setListener(this::goToAddIngredientActivity); //8a
    }

    //8a
    private void goToAddIngredientActivity(Ingredient ingredient) {
        Intent intent = new Intent(this, IngredientDetailsActivity.class);
        Gson gson = new Gson();
        String ingredientJson = gson.toJson(ingredient);
        String mealJson = gson.toJson(meal);
        intent.putExtra(INGREDIENT, ingredientJson);
        intent.putExtra(MEAL, mealJson);
        intent.putExtra(EDIT, EDIT);
        startActivity(intent);
    }

    //9
    private void clickToGoBack() {
        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(v -> {
            goToSavedMealsActivity(); //9a
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToSavedMealsActivity(); //9a
    }

    //9a
    private void goToSavedMealsActivity() {
        Intent intent = new Intent(this, SavedMealsActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        intent.putExtra(MEAL, json);
        startActivity(intent);
    }

    //10
    private void swipeToDeleteIngredient() {
        ItemSwipeHelper itemSwipeHelper = new ItemSwipeHelper(this, recyclerView) {

            @Override
            public void initUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton(
                        getApplicationContext(),
                        R.drawable.ic_delete,
                        getResources().getColor(R.color.deleteColor),
                        position -> viewModel.deleteIngredient(adapter.getIngredientAt(position))
                ));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemSwipeHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
