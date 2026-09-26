package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.smartpantrymanager.R;

import java.util.List;

import Inventory.recipeSelectListener;
import Models.recipesModel;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    //Declare Variables
    private final List<recipesModel> recipesModelList;
    private final Context context;
    private final recipeSelectListener recipeSelectListener;
    public RecipesAdapter(List<recipesModel> recipesModelList, Context context, recipeSelectListener recipeSelectListener) {
        this.recipesModelList = recipesModelList;
        this.context = context;
        this.recipeSelectListener = recipeSelectListener;
    }
    @NonNull
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.ViewHolder holder, int position) {
        recipesModel recipesModel = recipesModelList.get(position);
        //bind variables to string models
        holder.recipeNameTxt.setText(recipesModel.getName());
        holder.categoryTxt.setText(recipesModel.getCategory());
        holder.descriptionTxt.setText(recipesModel.getDescription());
        holder.prepTimeTxt.setText(String.valueOf(recipesModel.getPrepTime()) + " min");

        //create onClickListener for recipeCard
        holder.recipeCard.setOnClickListener( v ->{
            int pos = holder.getBindingAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                recipeSelectListener.onItemClickedListener(recipesModelList.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipesModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Declare Variables
        ConstraintLayout recipeCard;
        TextView recipeNameTxt, categoryTxt, descriptionTxt, prepTimeTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initialise variables
            recipeCard = itemView.findViewById(R.id.recipeCard);
            recipeNameTxt = itemView.findViewById(R.id.txtRecipeCategory);
            categoryTxt = itemView.findViewById(R.id.txtCategoryName);
            descriptionTxt = itemView.findViewById(R.id.txtRecipeDescription);
            prepTimeTxt = itemView.findViewById(R.id.txtMinutes);
        }
    }
}
