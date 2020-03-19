package com.breiter.mynutrition.ui.ingredientDetail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.entity.Meal;
import com.breiter.mynutrition.ui.mealDetail.MealDetailsActivity;
import com.google.gson.Gson;

public class IngredientDetailsActivity extends AppCompatActivity {
    private static final String MEAL = "meal";
    private static final String EDIT = "edit";
    private static final String INGREDIENT = "ingredient";

    private double quantity;
    private boolean isNewIngredient = true;
    private Ingredient ingredient;
    private Meal meal;
    private IngredientDetailsViewModel viewModel;
    private RadioGroup radioGroup;
    private RadioButton checkedButton;
    private EditText quantityEditText;
    private String chosenUnit;
    private TextLoaderHelper textLoaderHelper;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_details);

        viewModel = new ViewModelProvider(this).get(IngredientDetailsViewModel.class);

        getDataFromIntent();              //1
        setToolbarTitle();                //2
        setValuesForNew();                //3
        displayNutritionData();           //4
        setLastChosenQuantity();          //5
        setLastChosenMassUnit();          //6
        startListeningForQuantityChange();//6
        startListeningForUnitChange();    //7
        clickToConfirmIngredient();       //8
        clickToGoBack();                  //9
    }

    //1
    private void getDataFromIntent() {
        Intent intent = getIntent(); //from SearchIngredientActivity or AddEditMealActivity
        Gson gson = new Gson();

        String ingredientJson = intent.getStringExtra(INGREDIENT);
        ingredient = gson.fromJson(ingredientJson, Ingredient.class);

        String mealJson = intent.getStringExtra(MEAL);
        meal = gson.fromJson(mealJson, Meal.class);

        determineIfNewOrSaved(intent); //1a
    }

    //1a
    private void determineIfNewOrSaved(Intent intent) {
        isNewIngredient = !intent.hasExtra(EDIT);
    }

    //2
    private void setToolbarTitle() {
        TextView titleTextView = findViewById(R.id.titleToolbarTextView);
        if (isNewIngredient)
            titleTextView.setText(R.string.ingredients_details);
        else
            titleTextView.setText(R.string.edit_ingredient);
    }

    //3 Prevents mass = 0
    private void setValuesForNew() {
        if (isNewIngredient) {
            ingredient.setQuantity(100);
            ingredient.setQuantityUnit("g");
        }
    }

    //4
    private void displayNutritionData() {
        relativeLayout = findViewById(R.id.mainLayout);
        textLoaderHelper = new TextLoaderHelper(relativeLayout, ingredient);

        if (isNewIngredient)
            textLoaderHelper.displayStarterData();
        else
            textLoaderHelper.displayIngredientData();
    }

    //5
    private void setLastChosenQuantity() {
        quantityEditText = findViewById(R.id.quantityEditText);

        if(!isNewIngredient)
        quantityEditText.setText(String.valueOf(ingredient.getQuantity()));
    }

    //6
    private void setLastChosenMassUnit() {
        radioGroup = findViewById(R.id.radioGroup);

        switch (ingredient.getQuantityUnit()) {
            case "g":
                radioGroup.check(R.id.radio_g);
                break;
            case "kg":
                radioGroup.check(R.id.radio_kg);
                break;
            case "l":
                radioGroup.check(R.id.radio_l);
                break;
            default:
                radioGroup.check(R.id.radio_ml);
                break;
        }
    }

    //6 Update whenever mass has changed
    private void startListeningForQuantityChange() {
        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    quantity = Double.parseDouble(s.toString());
                    ingredient.setQuantity(quantity);
                } catch (IllegalArgumentException e) {
                    ingredient.setQuantity(0);
                }

                viewModel.calculateNutritionValue(ingredient);
                textLoaderHelper.displayIngredientData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //7
    private void startListeningForUnitChange() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            checkedButton = findViewById(radioGroup.getCheckedRadioButtonId());
            chosenUnit = checkedButton.getText().toString();
            ingredient.setQuantityUnit(chosenUnit);

            try {
                quantity = Double.valueOf(quantityEditText.getText().toString());
                ingredient.setQuantity(quantity);
            } catch (IllegalArgumentException e) {
                ingredient.setQuantity(0);
            }
            viewModel.calculateNutritionValue(ingredient);
            textLoaderHelper.displayIngredientData();
        });
    }

    //8
    private void clickToConfirmIngredient() {
        Button addIngredientButton = findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(v -> {
            String quantityInput = quantityEditText.getText().toString();
            if (quantityInput.trim().isEmpty())
                Toast.makeText(IngredientDetailsActivity.this, R.string.enter_quantity, Toast.LENGTH_SHORT).show();
            else {
                saveIngredient(); //8a
            }
        });
    }

    //8a
    private void saveIngredient() {
        if (isNewIngredient)
            addNewIngredient(); //8b
        else
            updateIngredient(); //8c
    }

    //8b
    private void addNewIngredient() {
        ingredient.setMealContainingId(meal.getMealId());
        viewModel.addIngredient(ingredient);
        goToMealActivity(); //8d
    }

    //8c
    private void updateIngredient() {
        viewModel.updateIngredient(ingredient);
        goToMealActivity(); //8d
    }

    //8d
    private void goToMealActivity() {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        Intent intent = new Intent(this, MealDetailsActivity.class);
        intent.putExtra(MEAL, json);
        startActivity(intent);
    }

    //9
    private void clickToGoBack() {
        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(v -> {
            goToAddEditMealActivity(); //9a
        });
    }

    //9a
    private void goToAddEditMealActivity() {
        finish();
    }
}
