package com.app.smartpantrymanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import Adapters.RecipesAdapter;
import Adapters.recipeIngredientsAdapter;
import Inventory.DbHelper;
import Inventory.recipeSelectListener;
import Models.recipesModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, recipeSelectListener {
    //Declare Variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recipeRecyclerView;
    private Toolbar toolbar;
    //Implement Database
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //make activity fullScreen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //calling method initialiseVariables()
        initialiseVariables();
        //calling method getRecipes()
        getRecipes();
        //calling method userOnClickListener()
        userOnClickListener();
    }
    //create method initialiseVariables()
    private void initialiseVariables() {
        //Initialise variables
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.main);
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);

        //Initialise Database
        dbHelper = new DbHelper(this);
    }
    //create method getRecipes()
    private void getRecipes() {
        //create and set layoutManager
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //create and set recipesAdapter
        RecipesAdapter recipesAdapter = new RecipesAdapter(dbHelper.getRecipes(), this,this);
        recipeRecyclerView.setAdapter(recipesAdapter);
    }
  //create method displayRecipeInfo()
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

                Toast.makeText(this, "Please Refer to Suggested Recipes", Toast.LENGTH_SHORT).show();
        });
        //create onClickListener for closeBtn
        closeBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }
    private void userOnClickListener() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.nav_home){
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        } else if (menuItem.getItemId() == R.id.nav_suggested) {
            Intent intent = new Intent(MainActivity.this, SuggestedRecipes.class);
            startActivity(intent);
            finish();
        }
        else if (menuItem.getItemId() == R.id.nav_pantry) {
            Intent intent = new Intent(MainActivity.this, Pantry.class);
            startActivity(intent);
            finish();
        }
        else if (menuItem.getItemId() == R.id.nav_addIngredient) {
            Intent intent = new Intent(MainActivity.this, AddIngredients.class);
            startActivity(intent);
            finish();
        }
        else if (menuItem.getItemId() == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            finish();
        }

        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    @Override
    public void onItemClickedListener(recipesModel recipesModel) {
            //calling method displayRecipeInfo()
            displayRecipeInfo(recipesModel.getId());
    }
}
