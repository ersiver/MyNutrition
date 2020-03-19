package com.breiter.mynutrition.data.logic;

import com.breiter.mynutrition.data.entity.Ingredient;

public class CalculatorIngredientNutrition {

    public void calculateNutritionForIngredient(Ingredient ingredient) {
        performCalculationForIngredient(ingredient);
    }

    private void performCalculationForIngredient(Ingredient ingredient) {
        double divisor;
        double quantity = ingredient.getQuantity();
        String quantityUnit = ingredient.getQuantityUnit();
        divisor = getDivisor(quantityUnit);

        setKcal(ingredient, quantity, divisor);
        setKJ(ingredient, quantity, divisor);
        setProtein(ingredient, quantity, divisor);
        setCarbo(ingredient, quantity, divisor);
        setFat(ingredient, quantity, divisor);
        setSugars(ingredient, quantity, divisor);
        setSaturates(ingredient, quantity, divisor);
        setMonoUnsaturates(ingredient, quantity, divisor);
        setPolyUnsaturates(ingredient, quantity, divisor);
        setStarch(ingredient, quantity, divisor);
        setFibre(ingredient, quantity, divisor);
        setSalt(ingredient, quantity, divisor);
    }

    private double getDivisor(String quantityUnit) {
        if (quantityUnit.equals("kg") || quantityUnit.equals("l"))
            return 0.1;
        else if (quantityUnit.equals("ml"))
            return 1_000_000;
        else
            return 100;
    }

    private void setKcal(Ingredient ingredient, double quantity, double divisor) {
        double kcal = ingredient.getEnergyKcalPer100G() * quantity / divisor;
        ingredient.setEnergyKcal(kcal);
    }

    private void setKJ(Ingredient ingredient, double quantity, double divisor) {
        double kJ = ingredient.getEnergykJPer100G() * quantity / divisor;
        ingredient.setEnergyKJ(kJ);
    }

    private void setSalt(Ingredient ingredient, double quantity, double divisor) {
        double saltContent = ingredient.getSaltPer100G() * quantity / divisor;
        ingredient.setSalt(saltContent);
    }

    private void setCarbo(Ingredient ingredient, double quantity, double divisor) {
        double carbohydrateContent = ingredient.getCarbohydratePer100G() * quantity / divisor;
        ingredient.setCarbohydrate(carbohydrateContent);
    }


    private void setFat(Ingredient ingredient, double quantity, double divisor) {
        double fatContent = ingredient.getFatPer100G() * quantity / divisor;
        ingredient.setFat(fatContent);
    }

    private void setSugars(Ingredient ingredient, double quantity, double divisor) {
        double sugarContent = ingredient.getSugarsPer100G() * quantity / divisor;
        ingredient.setSugars(sugarContent);
    }

    private void setSaturates(Ingredient ingredient, double quantity, double divisor) {
        double saturaresContent = ingredient.getSaturatedFatPer100G() * quantity / divisor;
        ingredient.setSaturatedFat(saturaresContent);
    }

    private void setMonoUnsaturates(Ingredient ingredient, double quantity, double divisor) {
        double monoUnsaturatesContent = ingredient.getMonounsaturatedFatPer100G() * quantity / divisor;
        ingredient.setMonounsaturatedFat(monoUnsaturatesContent);
    }

    private void setPolyUnsaturates(Ingredient ingredient, double quantity, double divisor) {
        double polyUnsaturatesContent = ingredient.getPolyunsaturatedFatPer100G() * quantity / divisor;
        ingredient.setPolyunsaturatedFat(polyUnsaturatesContent);
    }

    private void setStarch(Ingredient ingredient, double quantity, double divisor) {
        double starchContent = ingredient.getStarchPer100G() * quantity / divisor;
        ingredient.setStarch(starchContent);
    }

    private void setFibre(Ingredient ingredient, double quantity, double divisor) {
        double fibreContent = ingredient.getFibrePer100G() * quantity / divisor;
        ingredient.setFibre(fibreContent);
    }

    private void setProtein(Ingredient ingredient, double quantity, double divisor) {
        double proteinContent = ingredient.getProteinPer100G() * quantity / divisor;
        ingredient.setProtein(proteinContent);
    }
}
