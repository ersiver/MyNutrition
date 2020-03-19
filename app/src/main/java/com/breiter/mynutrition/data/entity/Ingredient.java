package com.breiter.mynutrition.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "ingredients",
        foreignKeys = @ForeignKey(entity = Meal.class,
                parentColumns = "mealId",
                childColumns = "mealContainingId",
                onDelete = CASCADE),
        indices = {@Index("mealContainingId")})

public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private long ingredientId;
    private long mealContainingId;
    private double quantity;
    private String quantityUnit;
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

    @SerializedName("Name")
    private String name;
    @SerializedName("Description")
    private String description;
    @SerializedName("Protein")
    private double proteinPer100G;
    @SerializedName("Fat")
    private double fatPer100G;
    @SerializedName("Carbohydrate")
    private double carbohydratePer100G;
    @SerializedName("kcal")
    private double energyKcalPer100G;
    @SerializedName("kJ")
    private double energykJPer100G;
    @SerializedName("Starch")
    private double starchPer100G;
    @SerializedName("Sugars")
    private double sugarsPer100G;
    @SerializedName("Fibre")
    private double fibrePer100G;
    @SerializedName("Saturated_fat")
    private double saturatedFatPer100G;
    @SerializedName("Monounsaturated_fat")
    private double monounsaturatedFatPer100G;
    @SerializedName("Polyunsaturated_fat")
    private double polyunsaturatedFatPer100G;
    @SerializedName("Salt")
    private double saltPer100G;

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public long getMealContainingId() {
        return mealContainingId;
    }

    public void setMealContainingId(long mealContainingId) {
        this.mealContainingId = mealContainingId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProteinPer100G() {
        return proteinPer100G;
    }

    public void setProteinPer100G(double proteinPer100G) {
        this.proteinPer100G = proteinPer100G;
    }

    public double getFatPer100G() {
        return fatPer100G;
    }

    public void setFatPer100G(double fatPer100G) {
        this.fatPer100G = fatPer100G;
    }

    public double getCarbohydratePer100G() {
        return carbohydratePer100G;
    }

    public void setCarbohydratePer100G(double carbohydratePer100G) {
        this.carbohydratePer100G = carbohydratePer100G;
    }

    public double getEnergyKcalPer100G() {
        return energyKcalPer100G;
    }

    public void setEnergyKcalPer100G(double energyKcalPer100G) {
        this.energyKcalPer100G = energyKcalPer100G;
    }

    public double getEnergykJPer100G() {
        return energykJPer100G;
    }

    public void setEnergykJPer100G(double energykJPer100G) {
        this.energykJPer100G = energykJPer100G;
    }

    public double getStarchPer100G() {
        return starchPer100G;
    }

    public void setStarchPer100G(double starchPer100G) {
        this.starchPer100G = starchPer100G;
    }

    public double getSugarsPer100G() {
        return sugarsPer100G;
    }

    public void setSugarsPer100G(double sugarsPer100G) {
        this.sugarsPer100G = sugarsPer100G;
    }

    public double getFibrePer100G() {
        return fibrePer100G;
    }

    public void setFibrePer100G(double fibrePer100G) {
        this.fibrePer100G = fibrePer100G;
    }

    public double getSaturatedFatPer100G() {
        return saturatedFatPer100G;
    }

    public void setSaturatedFatPer100G(double saturatedFatPer100G) {
        this.saturatedFatPer100G = saturatedFatPer100G;
    }

    public double getMonounsaturatedFatPer100G() {
        return monounsaturatedFatPer100G;
    }

    public void setMonounsaturatedFatPer100G(double monounsaturatedFatPer100G) {
        this.monounsaturatedFatPer100G = monounsaturatedFatPer100G;
    }

    public double getPolyunsaturatedFatPer100G() {
        return polyunsaturatedFatPer100G;
    }

    public void setPolyunsaturatedFatPer100G(double polyunsaturatedFatPer100G) {
        this.polyunsaturatedFatPer100G = polyunsaturatedFatPer100G;
    }

    public double getSaltPer100G() {
        return saltPer100G;
    }

    public void setSaltPer100G(double saltPer100G) {
        this.saltPer100G = saltPer100G;
    }
}
