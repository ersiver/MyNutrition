package com.breiter.mynutrition.ui.mealDetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static com.breiter.mynutrition.ui.tool.DoubleToString.convert;

class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private OnIngredientListener listener;

    MealDetailsAdapter() {
        ingredients = new ArrayList<>();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    void setListener(OnIngredientListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealDetailsAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    Ingredient getIngredientAt(int position) {
        return ingredients.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout caloriesLayout;
        private TextView ingredientNameTextView;
        private TextView kcalTextView;
        private TextView quantityTextView;
        private TextView quantityUnitTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredientNameTextView);
            kcalTextView = itemView.findViewById(R.id.kcalTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            quantityUnitTextView = itemView.findViewById(R.id.quantityUnitTextView);
            caloriesLayout = itemView.findViewById(R.id.caloriesLayout);
            caloriesLayout.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION)
                    listener.onIngredientClicked(ingredients.get(position));
            });
        }

        void bind(Ingredient ingredient) {
            ingredientNameTextView.setText(ingredient.getName());
            kcalTextView.setText(convert(ingredient.getEnergyKcal(), 0));
            quantityTextView.setText(convert(ingredient.getQuantity(), 2));
            quantityUnitTextView.setText(ingredient.getQuantityUnit());
        }
    }

    interface OnIngredientListener {
        void onIngredientClicked(Ingredient ingredient);
    }
}
