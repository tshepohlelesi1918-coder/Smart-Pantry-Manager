package com.app.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Adapters.ingredientsAdapter;
import Inventory.DbHelper;
import Models.ingredientsModel;

public class Pantry extends AppCompatActivity {
    //declare variables
    ImageView backBtn;
    TextView noDataTxt;
    RecyclerView ingredientsRecyclerView;
    DbHelper dbHelper;
    List<ingredientsModel> ingredientsModelList;
    ingredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //calling method initialiseVariables()
        initialiseVariables();
        //calling method getAllIngredients()
        getAllIngredients();
        //calling method userOnClickListener()
        userOnClickListener();
    }
    //create method initialiseVariables()
    private void initialiseVariables() {
        //Initialise Variables
        backBtn = findViewById(R.id.backBtn);
        noDataTxt = findViewById(R.id.noDataTxt);
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        dbHelper = new DbHelper(this);

    }
    //create method getAllIngredients()
    private void getAllIngredients() {
        ingredientsModelList = dbHelper.getIngredients();
        //check if ingredientsModelList is empty
        if(ingredientsModelList.isEmpty()){
            ingredientsRecyclerView.setVisibility(View.GONE);
            noDataTxt.setVisibility(View.VISIBLE);
        }else{
            ingredientsRecyclerView.setVisibility(View.VISIBLE);
            noDataTxt.setVisibility(View.GONE);
            //create and set layoutManager
            ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            //create and set ingredientsAdapter
            ingredientsAdapter = new ingredientsAdapter(ingredientsModelList, this);
            ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        }
    }
    //create method userOnClickListener()
    private void userOnClickListener() {
        //set onClickListener to backBtn
        backBtn.setOnClickListener( v ->{
            Intent intent = new Intent(Pantry.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}