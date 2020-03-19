package com.breiter.mynutrition.ui.mealSaved;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Meal;
import com.breiter.mynutrition.ui.mealDetail.MealDetailsActivity;
import com.breiter.mynutrition.ui.mealNutrition.MealNutritionValueActivity;
import com.breiter.mynutrition.ui.tool.ItemSwipeHelper;
import com.google.gson.Gson;

import java.util.List;

public class SavedMealsActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "shared_prefs";
    private static final String SORT_TYPE = "sortType";
    private static final String MEAL = "meal";
    private static final String EDIT = "edit";
    private static final String SAVED = "saved";

    private SavedMealViewModel viewModel;
    private RecyclerView savedRecyclerView;
    private SavedMealsAdapter adapter;
    private EditText searchMealEditText;
    private ImageView addMealImageView;
    private boolean isSortedByDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_meals);

        initToolbar();             //1
        initRecycler();            //2
        findSortOption();          //3
        getAllSavedMeals();        //4
        searchSavedMealByName();   //5
        clickToSeeIndividualMeal();//6
        clickToCreatedNewMeal();   //7
        clickToClearEditText();    //8
        swipeToRevealMealOptions();//9
    }

    //1
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    //2
    private void initRecycler() {
        savedRecyclerView = findViewById(R.id.savedRecyclerView);
        savedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        savedRecyclerView.setHasFixedSize(true);
        adapter = new SavedMealsAdapter();
        savedRecyclerView.setAdapter(adapter);
    }

    //3 Last chosen sort option
    private void findSortOption() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        isSortedByDefault = sharedPreferences.getBoolean(SORT_TYPE, true);
    }

    //4
    private void getAllSavedMeals() {
        viewModel = new ViewModelProvider(this).get(SavedMealViewModel.class);
        if (isSortedByDefault)
            getMealsByDefault();
        else
            getMealsByName();
    }

    //4a
    private void getMealsByDefault() {
        viewModel.getSavedMeals().observe(this, meals -> {
            adapter.setSavedMeals(meals);
            scrollBack();
            hideButtonOnScroll();
        });
    }

    //4b
    private void getMealsByName() {
        viewModel.getMealsByName().observe(this, meals -> {
            adapter.setSavedMeals(meals);
            scrollBack();
            hideButtonOnScroll();
        });
    }

    //4c Returning to same position after coming from other activity
    private void scrollBack() {
        Intent intent = getIntent();
        Gson gson = new Gson();
        String json = intent.getStringExtra(MEAL); //from MealNutritionActivity
        Meal meal = gson.fromJson(json, Meal.class);
        if (meal != null)
            savedRecyclerView.scrollToPosition(adapter.getItemPosition(meal));
    }

    //4d
    private void hideButtonOnScroll() {
        savedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy == 0)
                    addMealImageView.setVisibility(View.VISIBLE);
                else if (dy > 0)
                    addMealImageView.setVisibility(View.GONE);
                else if (dy < -5)
                    addMealImageView.setVisibility(View.VISIBLE);
            }
        });
    }

    //5
    private void searchSavedMealByName() {
        searchMealEditText = findViewById(R.id.searchIngredientEditText);
        searchMealEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    viewModel.getMealsByName(s.toString().toUpperCase().trim())
                            .observe(SavedMealsActivity.this, meals -> adapter.setSavedMeals(meals));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    getAllSavedMeals();
            }
        });
    }

    //6
    private void clickToSeeIndividualMeal() {
        adapter.setListener(this::goToMealActivity);
    }

    //6a
    private void goToMealActivity(Meal meal) {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        Intent intent = new Intent(this, MealDetailsActivity.class);
        intent.putExtra(MEAL, json);
        intent.putExtra(EDIT, EDIT);
        startActivity(intent);
    }

    //7
    private void clickToCreatedNewMeal() {
        addMealImageView = findViewById(R.id.addMealButton);
        addMealImageView.setOnClickListener(v -> {
            showNewMealDialog(); //6a
        });
    }

    //7a
    private void showNewMealDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SavedMealsActivity.this);
        ViewGroup nullParent = null;
        final View view = getLayoutInflater().inflate(R.layout.dialog_layout, nullParent);
        builder.setView(view)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    EditText mealNameEditText = view.findViewById(R.id.mealNameEditText);
                    String mealName = mealNameEditText.getText().toString();
                    if (mealName.trim().isEmpty()) {
                        Toast.makeText(SavedMealsActivity.this,
                                R.string.enter_meal, Toast.LENGTH_SHORT).show();
                        showNewMealDialog();
                    } else
                        goToAddNewMealActivity(mealName); //6b
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //7b
    private void goToAddNewMealActivity(String mealName) {
        mealName = mealName.substring(0, 1).toUpperCase().concat(mealName.substring(1)).trim();
        Meal newMeal = new Meal(mealName);
        Gson gson = new Gson();
        String json = gson.toJson(newMeal);
        Intent intent = new Intent(this, MealDetailsActivity.class);
        intent.putExtra(MEAL, json);
        startActivity(intent);
    }

    //8
    private void clickToClearEditText() {
        ImageView clearTextImageView = findViewById(R.id.clearTextImageView);
        clearTextImageView.setOnClickListener(v -> searchMealEditText.getText().clear());
    }

    //9
    private void swipeToRevealMealOptions() {
        ItemSwipeHelper itemSwipeHelper = new ItemSwipeHelper(this, savedRecyclerView) {

            @Override
            public void initUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton(
                        getApplicationContext(),
                        R.drawable.ic_delete,
                        getResources().getColor(R.color.deleteColor),
                        position -> {
                            viewModel.deleteMeal(adapter.getMealAt(position));
                            Toast.makeText(SavedMealsActivity.this,
                                    R.string.meal_deleted, Toast.LENGTH_SHORT).show();
                        }
                ));

                underlayButtons.add(new UnderlayButton(
                        getApplicationContext(),
                        R.drawable.ic_info,
                        getResources().getColor(R.color.infoColor),
                        position -> {
                            goToMealNutritionValueActivity(adapter.getMealAt(position)); //8a
                        }
                ));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemSwipeHelper);
        itemTouchHelper.attachToRecyclerView(savedRecyclerView);
    }

    //9a
    private void goToMealNutritionValueActivity(Meal meal) {
        Gson gson = new Gson();
        String json = gson.toJson(meal);
        Intent intent = new Intent(this, MealNutritionValueActivity.class);
        intent.putExtra(MEAL, json);
        intent.putExtra(SAVED, SAVED);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemSortByName) {
            getMealsByName();
            saveSortOption(false); //10
        } else if (item.getItemId() == R.id.itemDefaultSort) {
            getMealsByDefault();
            saveSortOption(true); //10
        } else
            viewModel.deleteAllMeals();

        return super.onOptionsItemSelected(item);
    }

    //10
    private void saveSortOption(boolean isDefault) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SORT_TYPE, isDefault);
        editor.apply();
    }
}
