package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartpantrymanager.R;

import java.util.List;

import Inventory.recipeSelectListener;
import Models.ingredientsModel;

public class recipeIngredientsAdapter extends RecyclerView.Adapter<recipeIngredientsAdapter.ViewHolder> {
    //declare variables
    private final List<ingredientsModel> ingredientsModelList;
    private final Context context;

    public recipeIngredientsAdapter(List<ingredientsModel> ingredientsModelList, Context context) {
        this.ingredientsModelList = ingredientsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public recipeIngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_ingredients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recipeIngredientsAdapter.ViewHolder holder, int position) {
        ingredientsModel ingredientsModel = ingredientsModelList.get(position);
        holder.txtIngredienName.setText(ingredientsModel.getIngredientName());
        holder.txtQuantity.setText(String.valueOf(ingredientsModel.getQuantity()));
        holder.txtUnit.setText(ingredientsModel.getUnits());
    }

    @Override
    public int getItemCount() {
        return ingredientsModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        //declare variables
        TextView txtIngredienName, txtQuantity, txtUnit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initialise variables
            txtIngredienName = itemView.findViewById(R.id.txtIngredienName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtUnit = itemView.findViewById(R.id.txtUnit);
        }
    }
}
