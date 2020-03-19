package com.breiter.mynutrition.ui.mealNutrition;

import android.view.View;
import android.widget.TextView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Meal;

import static com.breiter.mynutrition.ui.tool.DoubleToString.convert;

class TextLoaderHelper {
    private View view;
    private Meal meal;

    TextLoaderHelper(View view, Meal meal) {
        this.view = view;
        this.meal = meal;
    }

    void displayNutritionalData() {
        TextView kcalTextView = view.findViewById(R.id.kcalTextView);
        kcalTextView.setText(convert(meal.getEnergyKcal(), 0));

        TextView kJTextView = view.findViewById(R.id.kJTextView);
        kJTextView.setText(convert(meal.getEnergyKJ(), 0));

        TextView proteinTextView = view.findViewById(R.id.proteinTextView);
        proteinTextView.setText(convert(meal.getProtein(), 1));

        TextView carboTextView = view.findViewById(R.id.carboTextView);
        carboTextView.setText(convert(meal.getCarbohydrate(), 1));

        TextView sugarsTextView = view.findViewById(R.id.sugarsTextView);
        sugarsTextView.setText(convert(meal.getSugars(), 1));

        TextView starchTextView = view.findViewById(R.id.starchTextView);
        starchTextView.setText(convert(meal.getStarch(), 1));

        TextView fatTextView = view.findViewById(R.id.fatTextView);
        fatTextView.setText(convert(meal.getFat(), 1));

        TextView satFatTextView = view.findViewById(R.id.satFatTextView);
        satFatTextView.setText(convert(meal.getSaturatedFat(), 1));

        TextView monoFatTextView = view.findViewById(R.id.monoFatTextView);
        monoFatTextView.setText(convert(meal.getMonounsaturatedFat(), 1));

        TextView polyFatTextView = view.findViewById(R.id.polyFatTextView);
        polyFatTextView.setText(convert(meal.getPolyunsaturatedFat(), 1));

        TextView saltTextView = view.findViewById(R.id.saltTextView);
        saltTextView.setText(convert(meal.getSalt(), 2));

        TextView fibreTextView = view.findViewById(R.id.fibreTextView);
        fibreTextView.setText(convert(meal.getFibre(), 1));
    }
}
