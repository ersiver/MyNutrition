package com.breiter.mynutrition.data.net;

import com.breiter.mynutrition.data.entity.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DatabaseResponse {

    @SerializedName("Foods")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
