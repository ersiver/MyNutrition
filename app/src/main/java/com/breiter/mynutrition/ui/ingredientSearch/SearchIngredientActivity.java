package com.breiter.mynutrition.ui.ingredientSearch;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.entity.Meal;
import com.breiter.mynutrition.ui.ingredientDetail.IngredientDetailsActivity;
import com.google.gson.Gson;

public class SearchIngredientActivity extends AppCompatActivity {
    private static final String MEAL = "meal";
    private static final String INGREDIENT = "ingredient";

    private boolean isSearchingDefault = true;
    private Meal meal;
    private RecyclerView ingredientsRecyclerView;
    private EditText searchIngredientEditText;
    private ImageView clearTextImageView;
    private SearchIngredientAdapter adapter;
    private SearchIngredientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ingredient);

        initViews();                      //1
        getDataFromIntent();              //2
        setToolbarTitle();                //3
        getListWhileTyping();             //4
        clearEditText();                  //5
        switchToChangeSearchingOptions(); //6
        clickToSeeIndividualIngredient(); //7
        clickToGoBack();                  //8
    }

    //1
    private void initViews() {
        ingredientsRecyclerView = findViewById(R.id.mealIngredientsRecyclerView);
        searchIngredientEditText = findViewById(R.id.searchIngredientEditText);
        clearTextImageView = findViewById(R.id.clearTextImageView);
        adapter = new SearchIngredientAdapter();
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setAdapter(adapter);
    }

    //2
    private void getDataFromIntent() {
        Intent intent = getIntent();
        Gson gson = new Gson();
        String json = intent.getStringExtra(MEAL); //from AddEditMeal
        meal = gson.fromJson(json, Meal.class);
    }

    //3
    private void setToolbarTitle() {
        TextView titleToolbarTextView = findViewById(R.id.titleToolbarTextView);
        titleToolbarTextView.setText(R.string.search_ingredient);
    }

    //4
    private void getListWhileTyping() {
        searchIngredientEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2)
                    getList(s.toString().trim()); //4b
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //4b
    private void getList(String search) {
        viewModel = new ViewModelProvider(this).get(SearchIngredientViewModel.class);

        if (isSearchingDefault)
            viewModel.searchIngredient(search).observe(this, ingredients -> adapter.setIngredients(ingredients));
        else
            viewModel.searchIngredientByComponent(search).observe(this, ingredients -> adapter.setIngredients(ingredients));
    }

    //5
    private void clearEditText() {
        clearTextImageView.setOnClickListener(v -> searchIngredientEditText.getText().clear());
    }

    //6
    private void switchToChangeSearchingOptions() {
        CheckBox searchOptionSwitch = findViewById(R.id.searchOptionSwitch);
        searchOptionSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> isSearchingDefault = !isChecked);
    }

    //7
    private void clickToSeeIndividualIngredient() {
        //7a
        adapter.setListener(this::goToAddIngredientActivity);
    }

    //7a
    private void goToAddIngredientActivity(Ingredient ingredient) {
        Intent intent = new Intent(this, IngredientDetailsActivity.class);
        Gson gson = new Gson();
        String ingredientJson = gson.toJson(ingredient);
        String mealJson = gson.toJson(meal);
        intent.putExtra(INGREDIENT, ingredientJson);
        intent.putExtra(MEAL, mealJson);
        startActivity(intent);
    }

    //8
    private void clickToGoBack() {
        ImageView backImageView = findViewById(R.id.backImageView);

        backImageView.setOnClickListener(v -> {
            goToAddEditMealActivity(); //8a
        });
    }

    //8a
    private void goToAddEditMealActivity() {
        finish();
    }
}
