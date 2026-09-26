package com.app.smartpantrymanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapters.RecipesAdapter;
import Adapters.recipeIngredientsAdapter;
import Inventory.DbHelper;
import Inventory.recipeSelectListener;
import Models.recipesModel;

public class SuggestedRecipes extends AppCompatActivity implements recipeSelectListener {
    //declare variables
    private RecyclerView suggestedRecipesRecyclerView;
    private RecipesAdapter recipeAdapter;
    private ArrayList<recipesModel> recipesModelLists;
    private ImageView backBtn;
    private TextView noDataTxt;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //calling method initialiseVariables()
        initialiseVariables();
        //calling method userOnClickListener()
        userOnClickListener();
        //calling method loadSuggestedRecipes()
        loadSuggestedRecipes();
    }

    //create method initialliseVariables()
    private void initialiseVariables() {
        //Initialise variables
        backBtn = findViewById(R.id.backBtn);
        noDataTxt = findViewById(R.id.noDataTxt);
        suggestedRecipesRecyclerView = findViewById(R.id.suggestedRecipesRecyclerView);
        dbHelper = new DbHelper(this);

        //Continue here
        recipesModelLists = new ArrayList<>();

        //create and set recipesAdapter
        recipeAdapter = new RecipesAdapter(dbHelper.getAvailableRecipes(), this, this);
        //set RecyclerView
        if(dbHelper.getAvailableRecipes().isEmpty()){
            noDataTxt.setVisibility(View.VISIBLE);
            suggestedRecipesRecyclerView.setVisibility(View.GONE);
        }
        else{
            noDataTxt.setVisibility(View.GONE);
            suggestedRecipesRecyclerView.setVisibility(View.VISIBLE);
        }
        suggestedRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        suggestedRecipesRecyclerView.setAdapter(recipeAdapter);


    }
    //create method userOnClickListener
    private void userOnClickListener() {
        //create onClickListener for backBtn
        backBtn.setOnClickListener(v -> {
            //redirect user to MainActivity
            Intent intent = new Intent(SuggestedRecipes.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
    //create method loadSuggestedRecipes()
    private void loadSuggestedRecipes() {
        //clear arrayList
        recipesModelLists.clear();

        recipesModelLists.addAll(dbHelper.getAvailableRecipes());
    }
    private void displayRecipeInfo(int recipeId){
        recipesModel recipesModel = dbHelper.getRecipeInfo(String.valueOf(recipeId));
        //create layoutInflator
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        //create view
        View view = layoutInflater.inflate(R.layout.item_recipe_detail_screen, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        //Initialise Variables
        TextView txtRecipeName = view.findViewById(R.id.txtRecipeName);
        TextView txtRecipeCategory = view.findViewById(R.id.txtRecipeCategory);
        TextView txtRecipeDescription = view.findViewById(R.id.txtRecipeDescription);
        TextView txtMinutes = view.findViewById(R.id.txtMinutes);
        TextView txtRecipeSteps = view.findViewById(R.id.txtRecipeSteps);
        RecyclerView ingredientsRecycler = view.findViewById(R.id.ingredientsRecycler);
        Button cookBtn = view.findViewById(R.id.cookBtn);
        Button closeBtn = view.findViewById(R.id.closeBtn);
        //set text
        txtRecipeName.setText(recipesModel.getName());
        txtRecipeCategory.setText(recipesModel.getCategory());
        txtRecipeDescription.setText(recipesModel.getDescription());
        txtMinutes.setText(recipesModel.getPrepTime() + " min");
        txtRecipeSteps.setText(recipesModel.getPreparationSteps());

        //create and set LayoutManager
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        //create and set ingredientsAdapter
        recipeIngredientsAdapter ingredientsAdapter = new recipeIngredientsAdapter(dbHelper.getRecipeIngredients(recipeId), this);
        ingredientsRecycler.setAdapter(ingredientsAdapter);

        //create dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        //make dialog fullScreen
        Window window = dialog.getWindow();
        if(window != null)
        {
            window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }

        //create onClickListener for cookBtn
        cookBtn.setOnClickListener( v-> {
            if(dbHelper.canCookRecipe(recipeId)){
                dbHelper.cookRecipe(recipeId);
                Toast.makeText(this, "Recipe cooked successfully!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "You don't have enough ingredients to cook this recipe!", Toast.LENGTH_SHORT).show();
            }
        });
        //create onClickListener for closeBtn
        closeBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        if(dbHelper != null && recipesModelLists != null){
            //calling method loadSuggestedRecipes()
            loadSuggestedRecipes();
        }
    }

    @Override
    public void onItemClickedListener(recipesModel recipesModel) {
        //calling method displayRecipeInfo()
        displayRecipeInfo(recipesModel.getId());
    }
}