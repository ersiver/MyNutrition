package com.breiter.mynutrition.ui.mealNutrition;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Meal;
import com.breiter.mynutrition.ui.mealSaved.SavedMealsActivity;
import com.google.gson.Gson;

public class MealNutritionValueActivity extends AppCompatActivity {
    private static final String MEAL = "meal";
    private static final String SAVED = "saved";
    private Meal meal;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_nutrition_value);

        getDataFromIntent();      //1
        setToolbarTitle();        //2
        displayNutritionalData(); //3
        displayMealName();        //4
        clickToGoBack();          //5
    }

    //1
    private void getDataFromIntent() {
        intent = getIntent();
        Gson gson = new Gson();
        String json = intent.getStringExtra(MEAL); //from AddEditMealActivity or SavedMeals
        meal = gson.fromJson(json, Meal.class);
    }

    //2
    private void setToolbarTitle() {
        TextView titleToolbarTextView = findViewById(R.id.titleToolbarTextView);
            titleToolbarTextView.setText(R.string.nutritional_value);
    }

    //3
    private void displayMealName() {
        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText(meal.getMealName());
    }

    //4
    private void displayNutritionalData() {
        RelativeLayout relativeLayout = findViewById(R.id.mainLayout);
        TextLoaderHelper textLoaderHelper = new TextLoaderHelper(relativeLayout, meal);
        textLoaderHelper.displayNutritionalData();
    }

    //5
    private void clickToGoBack() {
        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(v -> {
            if (intent.hasExtra(SAVED))
                goToSavedMealsActivity(); //5a
            else
                finish();
        });
    }

    //5a
    private void goToSavedMealsActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        Intent intent = new Intent(this, SavedMealsActivity.class);
        intent.putExtra(MEAL, json);
        startActivity(intent);
    }
}



