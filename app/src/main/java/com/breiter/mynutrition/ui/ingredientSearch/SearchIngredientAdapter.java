package com.breiter.mynutrition.ui.ingredientSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static com.breiter.mynutrition.ui.tool.DoubleToString.convert;


public class SearchIngredientAdapter extends RecyclerView.Adapter<SearchIngredientAdapter.ViewHolder> {
    private List<Ingredient> ingredients;
    private OnIngredientClickListener listener;

    SearchIngredientAdapter() {
        this.ingredients = new ArrayList<>();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    void setListener(OnIngredientClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout descriptionLayout;
        private TextView ingredientNameTextView;
        private TextView kcalTextView;
        private TextView descriptionTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredientNameTextView);
            kcalTextView = itemView.findViewById(R.id.kcalParamTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            descriptionLayout = itemView.findViewById(R.id.descriptionLayout);
            descriptionLayout.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION)
                    listener.onIngredientClicked(ingredients.get(position));
            });
        }

        void bind(Ingredient ingredient) {
            ingredientNameTextView.setText(ingredient.getName());
            descriptionTextView.setText(ingredient.getDescription());
            kcalTextView.setText(convert(ingredient.getEnergyKcalPer100G(), 0));
        }
    }

    interface OnIngredientClickListener {
        void onIngredientClicked(Ingredient ingredient);
    }
}
