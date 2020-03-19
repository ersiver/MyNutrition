package com.breiter.mynutrition.data.logic;

import com.breiter.mynutrition.data.entity.Ingredient;
import com.breiter.mynutrition.data.entity.Meal;

import java.util.ArrayList;
import java.util.List;

public class CalculatorMealNutrition {
    private double totalMealQuantity;
    private double kcal;
    private double kJ;
    private double protein;
    private double carbohydrate;
    private double sugars;
    private double starch;
    private double fat;
    private double saturates;
    private double monounsat;
    private double polyunsat;
    private double fibre;
    private double salt;

    public void calculateNutritionForMeal(Meal meal, List<Ingredient> ingredients) {
        List<Ingredient> ingredientList = new ArrayList<>(ingredients);

        for (Ingredient ingredient : ingredientList) {
            //sum up individual nutrients of all ingredient
            kcal += ingredient.getEnergyKcal();
            protein += ingredient.getProtein();
            kJ += ingredient.getEnergyKJ();
            carbohydrate += ingredient.getCarbohydrate();
            sugars += ingredient.getSugars();
            starch += ingredient.getStarch();
            fat += ingredient.getFat();
            saturates += ingredient.getSaturatedFat();
            monounsat += ingredient.getMonounsaturatedFat();
            polyunsat += ingredient.getPolyunsaturatedFat();
            fibre += ingredient.getFibre();
            salt += ingredient.getSalt();
            //sum up quantities of all ingredients in grams
            totalMealQuantity += getQuantityInGram(ingredient); //1
        }

        setEnergyKcal(meal);
        setEnergyKJ(meal);
        setProtein(meal);
        setCarbohydrate(meal);
        setSugars(meal);
        setStarch(meal);
        setFat(meal);
        setSaturatedFat(meal);
        setMonounsaturatedFat(meal);
        setPolyunsaturatedFat(meal);
        setFibre(meal);
        setSalt(meal);
    }

    //1
    private double getQuantityInGram(Ingredient ingredient) {
        double kilo = 1000;
        String massUnit = ingredient.getQuantityUnit();

        if (massUnit.equals("kg") || massUnit.equals("l"))
            return ingredient.getQuantity() * kilo;
        else
            return ingredient.getQuantity();
    }

    //Calculate nutrient value per 100g of meal
    private double getNutrientPer100g(double nutrient) {
        double multiplier = 100;
        return multiplier * nutrient / totalMealQuantity;
    }

    private void setEnergyKcal(Meal meal) {
        kcal = getNutrientPer100g(kcal);
        meal.setEnergyKcal(kcal);
    }

    private void setEnergyKJ(Meal meal) {
        kJ = getNutrientPer100g(kJ);
        meal.setEnergyKJ(kJ);
    }

    private void setProtein(Meal meal) {
        protein = getNutrientPer100g(protein);
        meal.setProtein(protein);
    }

    private void setCarbohydrate(Meal meal) {
        carbohydrate = getNutrientPer100g(carbohydrate);
        meal.setCarbohydrate(carbohydrate);
    }

    private void setSugars(Meal meal) {
        sugars = getNutrientPer100g(sugars);
        meal.setSugars(sugars);
    }

    private void setStarch(Meal meal) {
        starch = getNutrientPer100g(starch);
        meal.setStarch(starch);
    }

    private void setFat(Meal meal) {
        fat = getNutrientPer100g(fat);
        meal.setFat(fat);
    }

    private void setSaturatedFat(Meal meal) {
        saturates = getNutrientPer100g(saturates);
        meal.setSaturatedFat(saturates);
    }

    private void setMonounsaturatedFat(Meal meal) {
        monounsat = getNutrientPer100g(monounsat);
        meal.setMonounsaturatedFat(monounsat);
    }

    private void setPolyunsaturatedFat(Meal meal) {
        polyunsat = getNutrientPer100g(polyunsat);
        meal.setPolyunsaturatedFat(polyunsat);
    }

    private void setFibre(Meal meal) {
        salt = getNutrientPer100g(salt);
        meal.setFibre(fibre);
    }

    private void setSalt(Meal meal) {
        fibre = getNutrientPer100g(fibre);
        meal.setSalt(salt);
    }
}
