package com.breiter.mynutrition.ui.mealSaved;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.mynutrition.R;
import com.breiter.mynutrition.data.entity.Meal;

import java.util.ArrayList;
import java.util.List;

import static com.breiter.mynutrition.ui.tool.DoubleToString.convert;

class SavedMealsAdapter extends RecyclerView.Adapter<SavedMealsAdapter.ViewHolder> {
    private List<Meal> savedMeals;
    private OnMealClickListener listener;

    SavedMealsAdapter() {
        savedMeals = new ArrayList<>();
    }

    void setSavedMeals(List<Meal> savedMeals) {
        this.savedMeals = savedMeals;
        notifyDataSetChanged();
    }

    void setListener(OnMealClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = savedMeals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return savedMeals.size();
    }

    int getItemPosition(Meal meal) {
        int position = 0;
        for (Meal aMeal : savedMeals) {
            if (meal.equals(aMeal))
                position = savedMeals.indexOf(aMeal);
        }
        return position;
    }

    Meal getMealAt(int position) {
        return savedMeals.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mealNameTextView;
        private TextView kcalTextView;
        private TextView kjTextView;
        TextView mealColorTextView;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mealNameTextView = itemView.findViewById(R.id.mealNameTextView);
            kcalTextView = itemView.findViewById(R.id.kcalTextView);
            kjTextView = itemView.findViewById(R.id.kjTextView);
            mealColorTextView = itemView.findViewById(R.id.mealColorTextView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION)
                    listener.onMealClicked(savedMeals.get(position));
            });
        }

        void bind(Meal meal) {
            mealNameTextView.setText(meal.getMealName());
            kcalTextView.setText(convert(meal.getEnergyKcal(), 0));
            kjTextView.setText(convert(meal.getEnergyKJ(), 0));

            loadColorTextView(meal);
        }

        private void loadColorTextView(Meal meal) {
            GradientDrawable draw = new GradientDrawable();
            draw.setShape(GradientDrawable.OVAL);
            draw.setColor(Color.parseColor(meal.getColor()));
            mealColorTextView.setBackground(draw);
            mealColorTextView.setText(meal.getMealName().substring(0, 1));
        }
    }

    interface OnMealClickListener {
        void onMealClicked(Meal meal);
    }
}
