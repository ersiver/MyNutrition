package com.breiter.mynutrition.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.breiter.mynutrition.data.logic.ColorProvider;

import java.util.Objects;

@Entity (tableName = "meals")
public class Meal {

    @PrimaryKey
    private long mealId;
    private String mealName;
    private double protein;
    private double fat;
    private double carbohydrate;
    private double energyKcal;
    private double energyKJ;
    private double starch;
    private double sugars;
    private double fibre;
    private double saturatedFat;
    private double monounsaturatedFat;
    private double polyunsaturatedFat;
    private double salt;
    private double quantity;
    private double quantityUnit;
    private String color;

    public Meal(String mealName) {
        this.mealName = mealName;
        this.mealId = System.currentTimeMillis();
        this.color = ColorProvider.getColor();
    }

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getEnergyKcal() {
        return energyKcal;
    }

    public void setEnergyKcal(double energyKcal) {
        this.energyKcal = energyKcal;
    }

    public double getEnergyKJ() {
        return energyKJ;
    }

    public void setEnergyKJ(double energyKJ) {
        this.energyKJ = energyKJ;
    }

    public double getStarch() {
        return starch;
    }

    public void setStarch(double starch) {
        this.starch = starch;
    }

    public double getSugars() {
        return sugars;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public double getFibre() {
        return fibre;
    }

    public void setFibre(double fibre) {
        this.fibre = fibre;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public double getMonounsaturatedFat() {
        return monounsaturatedFat;
    }

    public void setMonounsaturatedFat(double monounsaturatedFat) {
        this.monounsaturatedFat = monounsaturatedFat;
    }

    public double getPolyunsaturatedFat() {
        return polyunsaturatedFat;
    }

    public void setPolyunsaturatedFat(double polyunsaturatedFat) {
        this.polyunsaturatedFat = polyunsaturatedFat;
    }

    public double getSalt() {
        return salt;
    }

    public void setSalt(double salt) {
        this.salt = salt;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(double quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        Meal meal = (Meal) o;
        return getMealId() == meal.getMealId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMealId());
    }
}
