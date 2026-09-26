package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartpantrymanager.AddIngredients;
import com.app.smartpantrymanager.R;

import java.util.List;

import Inventory.DbHelper;
import Models.ingredientsModel;

public class ingredientsAdapter extends RecyclerView.Adapter<ingredientsAdapter.ViewHolder> {
    //Declare Variables
    private final List<ingredientsModel> ingredientsModelList;
    private final Context context;
    private DbHelper dbHelper;
    public ingredientsAdapter(List<ingredientsModel> ingredientsModelList, Context context) {
        this.ingredientsModelList = ingredientsModelList;
        this.context = context;
        dbHelper = new DbHelper(context);
    }
    @NonNull
    @Override
    public ingredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ingredientsAdapter.ViewHolder holder, int position) {
        ingredientsModel ingredientsModel = ingredientsModelList.get(position);
        //bind variables to string models
        holder.ingredientNameTxt.setText(ingredientsModel.getIngredientName());
        holder.quantityTxt.setText(String.valueOf(ingredientsModel.getQuantity()));
        holder.unitsTxt.setText(String.valueOf(ingredientsModel.getUnits()));

        //create onClickListener for editBtn
        holder.editBtn.setOnClickListener(v -> {
            //create intent
            Intent intent = new Intent(context, AddIngredients.class);
            intent.putExtra("ingredientId", String.valueOf(ingredientsModel.getIngredientId()));
            context.startActivity(intent);
        });
        //create onClickListener for deleteBtn
        holder.deleteBtn.setOnClickListener(v -> {
            //ask user if they sure
            new AlertDialog.Builder(context).setTitle("Delete Ingredient")
                    .setMessage("Are you sure you want to delete this Ingreient " +
                            ingredientsModel.getIngredientName() + "?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        //calling method deleteIngredient()
                        dbHelper.deleteIngredient(ingredientsModel.getIngredientId());
                        Toast.makeText(context, "Ingredient Deleted Successfully",
                                Toast.LENGTH_SHORT).show();
                        //remove from RecyclerView List
                        int pos = holder.getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                         ingredientsModelList.remove(pos);

                         //refresh recyclerView
                            notifyItemRemoved(pos);
                        }
                    })
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return ingredientsModelList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Declare Variables
        TextView ingredientNameTxt, quantityTxt, unitsTxt;
        ImageView editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initialise variables
            ingredientNameTxt = itemView.findViewById(R.id.ingredientNameTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            unitsTxt = itemView.findViewById(R.id.unitsTxt);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
