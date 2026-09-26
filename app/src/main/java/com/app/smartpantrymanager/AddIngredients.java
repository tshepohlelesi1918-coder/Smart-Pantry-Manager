package com.app.smartpantrymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Locale;

import Inventory.DbHelper;
import Models.ingredientsModel;

public class AddIngredients extends AppCompatActivity {

    //Declare Variables
    private final Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private EditText ingredientNameEdt, quantityEdt, unitsEdt, expiryDateEdt;
    private TextView txtHead;
    private CheckBox expiryDateCb;
    private String expiryDate;
    private  String ingredientId;
    ImageView backBtn;
    Button addBtn;
    //Implement Database
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ingredients);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        //calling method initialiseVariables()
        initialiseVariables();
        //calling method userOnClickListener()
        userOnClickListener();
    }

   //create method initialiseVariables()
    private void initialiseVariables() {

        //initialise variables
        ingredientNameEdt = findViewById(R.id.ingredientNameEdt);
        quantityEdt = findViewById(R.id.quantityEdt);
        unitsEdt = findViewById(R.id.unitsEdt);
        expiryDateEdt = findViewById(R.id.expiryDateEdt);
        addBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.backBtn);
        expiryDateCb = findViewById(R.id.expiryDateCb);
        txtHead = findViewById(R.id.txtHead);

        //implement database
        dbHelper = new DbHelper(this);

        //get Extra
        Intent intent = getIntent();
         ingredientId = intent.getStringExtra("ingredientId");
        if(ingredientId != null){
            txtHead.setText("Edit Ingredient");
            addBtn.setText("UPDATE");
            ingredientsModel ingredientsModel = dbHelper.getSelectedIngredient(Integer.parseInt(ingredientId));
            ingredientNameEdt.setText(ingredientsModel.getIngredientName());
            quantityEdt.setText(String.valueOf(ingredientsModel.getQuantity()));
            unitsEdt.setText(String.valueOf(ingredientsModel.getUnits()));
            expiryDateEdt.setText(String.valueOf(ingredientsModel.getExpiryDate()));
            expiryDateCb.setChecked(true);
        }
        else{
            txtHead.setText("Add Ingredient");
        }
    }

    //create method userOnClickListener()
    private void userOnClickListener() {
        //setOnCheckedChangeListener for expiryDateCb
        expiryDateCb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                expiryDateEdt.setEnabled(true);
                expiryDateEdt.setVisibility(View.VISIBLE);
                expiryDate = expiryDateEdt.getText().toString();

                if(expiryDate.isEmpty()) {
                    expiryDateEdt.setError("Expiry Date Required");
                    expiryDateEdt.requestFocus();
                    return;
                }

            } else {

                expiryDateEdt.setVisibility(View.GONE);
                expiryDate = "No Date";
            }
        });
        //create onClickListener for expiryDateEdt to display Date Dialog
        expiryDateEdt.setOnClickListener(v1 -> {
            DatePickerDialog dateDialog = new DatePickerDialog(this, R.style.DialogTheme, (
                    view, year, month, dayOfMonth) ->
                    expiryDateEdt.setText(String.format(Locale.getDefault(),
                            "%04d-%02d-%02d", year, month + 1, dayOfMonth)),
                    year, month, day);
            dateDialog.show();
        });
        //create onClickListener for addBtn
        addBtn.setOnClickListener(v -> {
            //create variables
            String ingredientName = ingredientNameEdt.getText().toString();
            String quantity = quantityEdt.getText().toString();
            String units = unitsEdt.getText().toString();
            expiryDate = expiryDateEdt.getText().toString();

            //if statement for error validation
            if(ingredientName.isEmpty() || ingredientName.length() < 3){
              ingredientNameEdt.setError("Name Required");
              ingredientNameEdt.requestFocus();
              return;
            }
            else if(quantity.isEmpty() || quantity.equals("0")){
              quantityEdt.setError("Quantity Required");
              quantityEdt.requestFocus();
              return;
            }
            else if(units.isEmpty() || units.equals("0")){
              unitsEdt.setError("Units Required");
              unitsEdt.requestFocus();
              return;
            }

            else{
                if(addBtn.getText().toString().equals("UPDATE")){
                 dbHelper.updateIngredient(Integer.parseInt(ingredientId),ingredientName,
                         Double.parseDouble(quantity), units, expiryDate);

                     Toast.makeText(this, "Ingredient Updated Successfully", Toast.LENGTH_SHORT).show();
                } else if (addBtn.getText().toString().equals("ADD")) {
                    boolean success = dbHelper.saveIngredients(ingredientName, Integer.parseInt(quantity),
                            units, expiryDate);
                    if(success){
                        Toast.makeText(this, "Ingredient Added Successfully", Toast.LENGTH_SHORT).show();
                        ingredientNameEdt.setText("");
                        quantityEdt.setText("");
                        unitsEdt.setText("");
                        expiryDateEdt.setText("");
                    }
                    else{
                        Toast.makeText(this, "Failed to add ingredient", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

        //create onClickListener for backBtn
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AddIngredients.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

}