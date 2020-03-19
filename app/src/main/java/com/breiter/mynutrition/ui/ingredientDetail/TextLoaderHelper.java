package com.breiter.mynutrition.ui.ingredientDetail;

import android.view.View;
import android.widget.TextView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Ingredient;

import static com.breiter.mynutrition.ui.tool.DoubleToString.convert;

class TextLoaderHelper {
    private View view;
    private Ingredient ingredient;

    TextLoaderHelper(View view, Ingredient ingredient) {
        this.view = view;
        this.ingredient = ingredient;
    }

    void displayStarterData() {
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(ingredient.getDescription());

        TextView quantityTextView = view.findViewById(R.id.quantityTextView);
        quantityTextView.setText(convert(ingredient.getQuantity(), 0));

        TextView quantityUnitTextView = view.findViewById(R.id.quantityUnitTextView);
        quantityUnitTextView.setText(ingredient.getQuantityUnit());

        TextView kcalTextView = view.findViewById(R.id.kcalTextView);
        kcalTextView.setText(convert(ingredient.getEnergyKcalPer100G(), 0));

        TextView kJTextView = view.findViewById(R.id.kJTextView);
        kJTextView.setText(convert(ingredient.getEnergykJPer100G(), 0));

        TextView proteinTextView = view.findViewById(R.id.proteinTextView);
        proteinTextView.setText(convert(ingredient.getProteinPer100G(), 1));

        TextView carboTextView = view.findViewById(R.id.carboTextView);
        carboTextView.setText(convert(ingredient.getCarbohydratePer100G(), 1));

        TextView sugarsTextView = view.findViewById(R.id.sugarsTextView);
        sugarsTextView.setText(convert(ingredient.getSugarsPer100G(), 1));

        TextView starchTextView = view.findViewById(R.id.starchTextView);
        starchTextView.setText(convert(ingredient.getStarchPer100G(), 1));

        TextView fatTextView = view.findViewById(R.id.fatTextView);
        fatTextView.setText(convert(ingredient.getFatPer100G(), 1));

        TextView satFatTextView = view.findViewById(R.id.satFatTextView);
        satFatTextView.setText(convert(ingredient.getSaturatedFatPer100G(), 1));

        TextView monoFatTextView = view.findViewById(R.id.monoFatTextView);
        monoFatTextView.setText(convert(ingredient.getMonounsaturatedFatPer100G(), 1));

        TextView polyFatTextView = view.findViewById(R.id.polyFatTextView);
        polyFatTextView.setText(convert(ingredient.getPolyunsaturatedFatPer100G(), 1));

        TextView saltTextView = view.findViewById(R.id.saltTextView);
        saltTextView.setText(convert(ingredient.getSaltPer100G(), 2));

        TextView fibreTextView = view.findViewById(R.id.fibreTextView);
        fibreTextView.setText(convert(ingredient.getFibrePer100G(), 1));
    }

    void displayIngredientData() {
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(ingredient.getDescription());

        TextView quantityTextView = view.findViewById(R.id.quantityTextView);
        quantityTextView.setText(convert(ingredient.getQuantity(), 2));

        TextView quantityUnitTextView = view.findViewById(R.id.quantityUnitTextView);
        quantityUnitTextView.setText(ingredient.getQuantityUnit());

        TextView kcalTextView = view.findViewById(R.id.kcalTextView);
        kcalTextView.setText(convert(ingredient.getEnergyKcal(), 0));

        TextView kJTextView = view.findViewById(R.id.kJTextView);
        kJTextView.setText(convert(ingredient.getEnergyKJ(), 0));

        TextView proteinTextView = view.findViewById(R.id.proteinTextView);
        proteinTextView.setText(convert(ingredient.getProtein(), 1));

        TextView carboTextView = view.findViewById(R.id.carboTextView);
        carboTextView.setText(convert(ingredient.getCarbohydrate(), 1));

        TextView sugarsTextView = view.findViewById(R.id.sugarsTextView);
        sugarsTextView.setText(convert(ingredient.getSugars(), 1));

        TextView starchTextView = view.findViewById(R.id.starchTextView);
        starchTextView.setText(convert(ingredient.getStarch(), 1));

        TextView fatTextView = view.findViewById(R.id.fatTextView);
        fatTextView.setText(convert(ingredient.getFat(), 1));

        TextView satFatTextView = view.findViewById(R.id.satFatTextView);
        satFatTextView.setText(convert(ingredient.getSaturatedFat(), 1));

        TextView monoFatTextView = view.findViewById(R.id.monoFatTextView);
        monoFatTextView.setText(convert(ingredient.getMonounsaturatedFat(), 1));

        TextView polyFatTextView = view.findViewById(R.id.polyFatTextView);
        polyFatTextView.setText(convert(ingredient.getPolyunsaturatedFat(), 1));

        TextView saltTextView = view.findViewById(R.id.saltTextView);
        saltTextView.setText(convert(ingredient.getSalt(), 2));

        TextView fibreTextView = view.findViewById(R.id.fibreTextView);
        fibreTextView.setText(convert(ingredient.getFibre(), 1));
    }
}
