package Inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import Models.ingredientsModel;
import Models.recipesModel;

public class DbHelper extends SQLiteOpenHelper {
    //constructor
    public DbHelper (Context context){
     super(context, "SmartPantryManager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        db.execSQL("CREATE TABLE PANTRY(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT,Quantity REAL, Unit TEXT,ExpiryDate TEXT)");
        db.execSQL("CREATE TABLE RECIPES(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, "+
                "Category TEXT, Description TEXT, Prep_Time INTEGER,Preparation_Steps TEXT)");
        db.execSQL("CREATE TABLE RECIPE_INGREDIENTS(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Recipe_Id INTEGER, Ingredient TEXT,Required_Quantity REAL, Unit TEXT )");

        //calling method addDefaultRecipes()
        addDefaultRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists INGREDIENTS");
    }
    //create method addDefaultRecipes()
    public void addDefaultRecipes(SQLiteDatabase db){

        // 1. Spaghetti Bolognese
        long spaghettiBolognese = insertRecipe( db, "Spaghetti Bolognese",
                "Classic spaghetti served with a rich tomato and mince sauce.",
                "Pasta",
                40,
                "1. Boil the spaghetti in salted water until tender.\n" +
                        "2. Heat cooking oil in a pan and fry the chopped onion.\n" +
                        "3. Add garlic and cook for about 30 seconds.\n" +
                        "4. Add the minced meat and cook until browned.\n" +
                        "5. Add the chopped tomatoes and simmer for 10–15 minutes.\n" +
                        "6. Season with salt, pepper and herbs.\n" +
                        "7. Drain the spaghetti and serve with the Bolognese sauce.");
        addIngredient(db, spaghettiBolognese, "Spaghetti", 1, "packet");
        addIngredient(db, spaghettiBolognese, "Tomato", 2, "pieces");
        addIngredient(db, spaghettiBolognese, "Minced Meat", 250, "g");
        addIngredient(db, spaghettiBolognese, "Onion", 1, "piece");
        addIngredient(db, spaghettiBolognese, "Garlic", 2, "cloves");

        // 2. Chicken Curry
        long chickenCurry = insertRecipe( db, "Chicken Curry",
                "Tender chicken cooked in a flavorful curry sauce.",
                "Chicken",
                45,
                "1. Cut the chicken into bite-sized pieces.\n" +
                        "2. Heat cooking oil in a large pan.\n" +
                        "3. Fry the chopped onion until soft.\n" +
                        "4. Add garlic and curry powder and stir for 30 seconds.\n" +
                        "5. Add the chicken and cook until lightly browned.\n" +
                        "6. Add chopped tomatoes and stir well.\n" +
                        "7. Cover and simmer until the chicken is fully cooked.\n" +
                        "8. Taste and adjust the seasoning before serving.");
        addIngredient(db, chickenCurry, "Chicken", 500, "g");
        addIngredient(db, chickenCurry, "Onion", 1, "piece");
        addIngredient(db, chickenCurry, "Tomato", 2, "pieces");
        addIngredient(db, chickenCurry, "Garlic", 2, "cloves");
        addIngredient(db, chickenCurry, "Curry Powder", 2, "tbsp");
        addIngredient(db, chickenCurry, "Cooking Oil", 2, "tbsp");

        // 3. Beef Burger
        long beefBurger = insertRecipe( db, "Beef Burger",
                "Juicy homemade beef burger with fresh toppings.",
                "Fast Food",
                25,
                "1. Season the minced meat with salt and pepper.\n" +
                        "2. Shape the meat into burger patties.\n" +
                        "3. Heat a pan and cook the patties on both sides until fully cooked.\n" +
                        "4. Place cheese on the patties and allow it to melt.\n" +
                        "5. Slice the tomato and onion.\n" +
                        "6. Toast the burger buns lightly.\n" +
                        "7. Assemble the burger with lettuce, tomato, onion, cheese and the beef patty.\n" +
                        "8. Serve immediately.");
        addIngredient(db, beefBurger, "Burger Buns", 1, "pack");
        addIngredient(db, beefBurger, "Minced Meat", 200, "g");
        addIngredient(db, beefBurger, "Lettuce", 2, "leaves");
        addIngredient(db, beefBurger, "Tomato", 1, "piece");
        addIngredient(db, beefBurger, "Onion", 0.5, "piece");
        addIngredient(db, beefBurger, "Cheese", 1, "slice");

        // 4. Chicken Fried Rice
        long chickenFriedRice = insertRecipe( db, "Chicken Fried Rice",
                "Fried rice with chicken, vegetables and seasoning.",
                "Rice",
                30,
                "1. Cook the rice and allow it to cool slightly.\n" +
                        "2. Cut the chicken into small pieces.\n" +
                        "3. Heat cooking oil in a large frying pan.\n" +
                        "4. Cook the chicken until fully cooked.\n" +
                        "5. Add chopped onion and carrot and stir-fry for several minutes.\n" +
                        "6. Push the ingredients to one side and scramble the eggs.\n" +
                        "7. Add the cooked rice and mix everything together.\n" +
                        "8. Season to taste and stir-fry for another 2–3 minutes.\n" +
                        "9. Serve hot.");
        addIngredient(db, chickenFriedRice, "Rice", 2, "cups");
        addIngredient(db, chickenFriedRice, "Chicken", 250, "g");
        addIngredient(db, chickenFriedRice, "Eggs", 2, "pieces");
        addIngredient(db, chickenFriedRice, "Carrot", 1, "piece");
        addIngredient(db, chickenFriedRice, "Onion", 1, "piece");
        addIngredient(db, chickenFriedRice, "Cooking Oil", 2, "tbsp");

        // 5. Beef Stew
        long beefStew = insertRecipe( db, "Beef Stew",
                "Slow cooked beef with vegetables in a rich gravy.",
                "Beef",
                60,
                "1. Cut the beef into bite-sized pieces.\n" +
                        "2. Heat oil in a large pot and brown the beef.\n" +
                        "3. Remove the beef and fry the chopped onion.\n" +
                        "4. Add chopped tomatoes and cook until softened.\n" +
                        "5. Return the beef to the pot.\n" +
                        "6. Add chopped potatoes and carrots.\n" +
                        "7. Add enough water or stock to cover the ingredients.\n" +
                        "8. Cover and simmer until the beef and vegetables are tender.\n" +
                        "9. Season to taste and serve." );
        addIngredient(db, beefStew, "Beef", 500, "g");
        addIngredient(db, beefStew, "Potato", 3, "pieces");
        addIngredient(db, beefStew, "Carrot", 2, "pieces");
        addIngredient(db, beefStew, "Onion", 1, "piece");
        addIngredient(db, beefStew, "Tomato", 2, "pieces");
        addIngredient(db, beefStew, "Cooking Oil", 2, "tbsp");

        // 6. Chicken Sandwich
        long chickenSandwich = insertRecipe( db, "Chicken Sandwich",
                "Simple toasted sandwich filled with seasoned chicken.",
                "Sandwich",
                20,
                "1. Season the chicken and cook it until fully cooked.\n" +
                        "2. Allow the chicken to cool slightly and slice it.\n" +
                        "3. Toast the bread lightly.\n" +
                        "4. Spread mayonnaise over the bread.\n" +
                        "5. Add lettuce and sliced tomato.\n" +
                        "6. Add the cooked chicken.\n" +
                        "7. Place the second slice of bread on top.\n" +
                        "8. Cut the sandwich and serve." );
        addIngredient(db, chickenSandwich, "Bread", 4, "slices");
        addIngredient(db, chickenSandwich, "Chicken", 200, "g");
        addIngredient(db, chickenSandwich, "Lettuce", 2, "leaves");
        addIngredient(db, chickenSandwich, "Tomato", 1, "piece");
        addIngredient(db, chickenSandwich, "Mayonnaise", 2, "tbsp");

        // 7. Cheese Omelette
        long cheeseOmelette = insertRecipe( db, "Cheese Omelette",
                "Fluffy eggs filled with melted cheese.",
                "Breakfast",
                10,
                "1. Crack the eggs into a bowl.\n" +
                        "2. Beat the eggs with a little salt and pepper.\n" +
                        "3. Chop the onion and tomato.\n" +
                        "4. Heat a little cooking oil in a non-stick pan.\n" +
                        "5. Add the onion and tomato and cook briefly.\n" +
                        "6. Pour the beaten eggs into the pan.\n" +
                        "7. Sprinkle cheese over the eggs.\n" +
                        "8. Cook until the eggs are almost completely set.\n" +
                        "9. Fold the omelette in half and serve.");
        addIngredient(db, cheeseOmelette, "Eggs", 3, "pieces");
        addIngredient(db, cheeseOmelette, "Cheese", 2, "slices");
        addIngredient(db, cheeseOmelette, "Onion", 0.5, "piece");
        addIngredient(db, cheeseOmelette, "Tomato", 1, "piece");
        addIngredient(db, cheeseOmelette, "Cooking Oil", 1, "tbsp");
        // 8. Pancakes
        long pancakes = insertRecipe( db, "Pancakes",
                "Soft and fluffy homemade pancakes.",
                "Breakfast",
                20,
                "1. Add flour and sugar to a mixing bowl.\n" +
                        "2. Add the eggs and milk.\n" +
                        "3. Whisk until a smooth batter forms.\n" +
                        "4. Heat a pan and lightly grease it with butter.\n" +
                        "5. Pour a small amount of batter into the pan.\n" +
                        "6. Cook until bubbles appear on the surface.\n" +
                        "7. Flip the pancake and cook the other side.\n" +
                        "8. Repeat until all the batter is used.\n" +
                        "9. Serve warm with your preferred toppings.");
        addIngredient(db, pancakes, "Flour", 2, "cups");
        addIngredient(db, pancakes, "Eggs", 2, "pieces");
        addIngredient(db, pancakes, "Milk", 1, "cup");
        addIngredient(db, pancakes, "Sugar", 2, "tbsp");
        addIngredient(db, pancakes, "Butter", 2, "tbsp");

        // 9. Chicken Pasta
        long chickenPasta = insertRecipe( db, "Creamy Chicken Pasta",
                "Creamy pasta with tender chicken.",
                "Pasta",
                35,
                "1. Boil the pasta in salted water until tender.\n" +
                        "2. Cut the chicken into small pieces.\n" +
                        "3. Melt butter in a large pan.\n" +
                        "4. Add garlic and cook briefly.\n" +
                        "5. Add chicken and cook until fully cooked.\n" +
                        "6. Add milk and bring it to a gentle simmer.\n" +
                        "7. Add cheese and stir until the sauce becomes creamy.\n" +
                        "8. Drain the pasta and add it to the sauce.\n" +
                        "9. Stir everything together and serve.");
        addIngredient(db, chickenPasta, "Pasta", 250, "g");
        addIngredient(db, chickenPasta, "Chicken", 250, "g");
        addIngredient(db, chickenPasta, "Milk", 1, "cup");
        addIngredient(db, chickenPasta, "Cheese", 50, "g");
        addIngredient(db, chickenPasta, "Garlic", 2, "cloves");
        addIngredient(db, chickenPasta, "Butter", 1, "tbsp");

        // 10. Fish and Chips
        long fishAndChips = insertRecipe( db, "Fish and Chips",
                "Crispy fish served with golden homemade chips.",
                "Fast Food",
                40,
                "1. Peel and cut the potatoes into chips.\n" +
                        "2. Wash and dry the potato chips.\n" +
                        "3. Season the fish with salt and pepper.\n" +
                        "4. Coat the fish lightly with flour.\n" +
                        "5. Heat cooking oil in a frying pan.\n" +
                        "6. Fry the potatoes until golden and crispy.\n" +
                        "7. Fry the fish until golden and cooked through.\n" +
                        "8. Drain excess oil using paper towels.\n" +
                        "9. Serve the fish with the chips.");
        addIngredient(db, fishAndChips, "Fish", 300, "g");
        addIngredient(db, fishAndChips, "Potato", 3, "pieces");
        addIngredient(db, fishAndChips, "Flour", 1, "cup");
        addIngredient(db, fishAndChips, "Cooking Oil", 3, "tbsp");

        // 11. Beef Tacos
        long beefTacos = insertRecipe( db, "Beef Tacos",
                "Seasoned beef tacos with fresh vegetables.",
                "Mexican",
                30 ,
                "1. Heat a pan with a little cooking oil.\n" +
                        "2. Add the minced meat and cook until browned.\n" +
                        "3. Add chopped onion and cook until soft.\n" +
                        "4. Season the beef with your preferred taco seasoning.\n" +
                        "5. Chop the tomato and lettuce.\n" +
                        "6. Warm the taco shells according to the package instructions.\n" +
                        "7. Fill each taco shell with the cooked beef.\n" +
                        "8. Add lettuce, tomato and cheese.\n" +
                        "9. Serve immediately.");
        addIngredient(db, beefTacos, "Minced Meat", 300, "g");
        addIngredient(db, beefTacos, "Taco Shells", 6, "pieces");
        addIngredient(db, beefTacos, "Tomato", 2, "pieces");
        addIngredient(db, beefTacos, "Lettuce", 3, "leaves");
        addIngredient(db, beefTacos, "Cheese", 50, "g");
        addIngredient(db, beefTacos, "Onion", 1, "piece");

        // 12. Vegetable Pasta
        long vegetablePasta = insertRecipe( db, "Vegetable Pasta",
                "Pasta mixed with fresh vegetables.",
                "Vegetarian",
                30,
                "1. Boil the pasta in salted water until tender.\n" +
                        "2. Chop the tomato, onion, carrot and bell pepper.\n" +
                        "3. Heat cooking oil in a large pan.\n" +
                        "4. Fry the onion until soft.\n" +
                        "5. Add the carrot and bell pepper and cook for several minutes.\n" +
                        "6. Add the chopped tomatoes and simmer until softened.\n" +
                        "7. Drain the pasta and add it to the vegetables.\n" +
                        "8. Mix well and season to taste.\n" +
                        "9. Serve hot.");
        addIngredient(db, vegetablePasta, "Pasta", 250, "g");
        addIngredient(db, vegetablePasta, "Tomato", 2, "pieces");
        addIngredient(db, vegetablePasta, "Onion", 1, "piece");
        addIngredient(db, vegetablePasta, "Carrot", 1, "piece");
        addIngredient(db, vegetablePasta, "Bell Pepper", 1, "piece");
        addIngredient(db, vegetablePasta, "Cooking Oil", 1, "tbsp");

        // 13. Tomato Soup
        long tomatoSoup = insertRecipe( db, "Tomato Soup",
                "Warm homemade soup made with fresh tomatoes.",
                "Soup",
                25 ,
                "1. Chop the tomatoes and onion.\n" +
                        "2. Melt butter in a large pot.\n" +
                        "3. Add the onion and cook until soft.\n" +
                        "4. Add garlic and cook briefly.\n" +
                        "5. Add the chopped tomatoes.\n" +
                        "6. Add enough water or stock to cover the tomatoes.\n" +
                        "7. Simmer for 15–20 minutes.\n" +
                        "8. Blend the soup until smooth.\n" +
                        "9. Stir in the milk and heat gently.\n" +
                        "10. Season to taste and serve warm."
                ); addIngredient(db, tomatoSoup, "Tomato", 5, "pieces");
                addIngredient(db, tomatoSoup, "Onion", 1, "piece");
                addIngredient(db, tomatoSoup, "Garlic", 2, "cloves");
                addIngredient(db, tomatoSoup, "Butter", 1, "tbsp");
                addIngredient(db, tomatoSoup, "Milk", 0.5, "cup");

                // 14. Chicken Wrap
        long chickenWrap = insertRecipe( db, "Chicken Wrap",
                "Grilled chicken wrapped with fresh vegetables.",
                "Fast Food",
                25,
                "1. Season the chicken with salt, pepper and your preferred spices.\n" +
                        "2. Cook the chicken until fully cooked.\n" +
                        "3. Slice the cooked chicken into strips.\n" +
                        "4. Chop the tomato and lettuce.\n" +
                        "5. Warm the wraps in a pan.\n" +
                        "6. Spread mayonnaise over each wrap.\n" +
                        "7. Add chicken, lettuce, tomato and cheese.\n" +
                        "8. Fold the sides of the wrap inward.\n" +
                        "9. Roll the wrap tightly and serve.");
        addIngredient(db, chickenWrap, "Chicken", 200, "g");
        addIngredient(db, chickenWrap, "Wraps", 2, "pieces");
        addIngredient(db, chickenWrap, "Lettuce", 2, "leaves");
        addIngredient(db, chickenWrap, "Tomato", 1, "piece");
        addIngredient(db, chickenWrap, "Mayonnaise", 2, "tbsp");
        addIngredient(db, chickenWrap, "Cheese", 2, "slices");

        // 15. Homemade Pizza
        long pizza = insertRecipe( db, "Homemade Pizza",
                "Homemade pizza topped with cheese, tomato and vegetables.",
                "Pizza",
                50,
                "1. Mix flour with water and a little salt to make the dough.\n" +
                        "2. Knead the dough until smooth.\n" +
                        "3. Allow the dough to rest for about 20–30 minutes.\n" +
                        "4. Roll the dough into a thin pizza base.\n" +
                        "5. Prepare a simple tomato sauce using chopped tomatoes.\n" +
                        "6. Spread the tomato sauce over the pizza base.\n" +
                        "7. Add chopped onion, bell pepper and cheese.\n" +
                        "8. Preheat the oven to about 220°C.\n" +
                        "9. Bake the pizza until the crust is golden and the cheese has melted.\n" +
                        "10. Remove from the oven, slice and serve.");
        addIngredient(db, pizza, "Flour", 2, "cups");
        addIngredient(db, pizza, "Tomato", 2, "pieces");
        addIngredient(db, pizza, "Cheese", 100, "g");
        addIngredient(db, pizza, "Onion", 1, "piece");
        addIngredient(db, pizza, "Bell Pepper", 1, "piece");
        addIngredient(db, pizza, "Cooking Oil", 1, "tbsp");
        }

    //create method canCookRecipe()
    public boolean canCookRecipe(int recipeId){
        //create database
        SQLiteDatabase db = this.getReadableDatabase();
        //create cursor
        Cursor cursor = db.rawQuery("SELECT Ingredient, Required_Quantity, Unit FROM RECIPE_INGREDIENTS " +
                "WHERE Recipe_Id = ?", new String[]{String.valueOf(recipeId)});

        while (cursor.moveToNext()){
            //declare variables
            String ingredient = cursor.getString(0);
            double quantity = cursor.getDouble(1);
            String units = cursor.getString(2);

            //create pantry cursor
            Cursor pantryCursor = db.rawQuery("SELECT Quantity FROM PANTRY WHERE Name = ?" +
                    "AND Unit = ?", new String[]{ingredient, units});

            if(!pantryCursor.moveToFirst()){
                pantryCursor.close();
                cursor.close();
                return false;
            }

            double available = pantryCursor.getDouble(0);
            pantryCursor.close();

            if(available < quantity){
                cursor.close();
                return false;
            }
            }
        cursor.close();
        return true;
        }
    //create method getPantryQuantity()
    public double getPantryQuantity(String ingredient, String units){
        //create database
        SQLiteDatabase db = this.getReadableDatabase();

        //create cursor
        Cursor cursor = db.rawQuery("SELECT Quantity FROM PANTRY WHERE Name = ? AND Unit = ?",
                new String[]{ingredient, units});
        //declare variables
        double quantity = 0;
        if(cursor.moveToFirst()){
            quantity = cursor.getDouble(0);
        }
        cursor.close();
        return quantity;
    }

    //create method cookRecipe()
    public boolean cookRecipe(int recipeId){
        //create database
        SQLiteDatabase db = this.getWritableDatabase();
        //begin transaction
        db.beginTransaction();
        //create cursor and set it to null
        Cursor cursor = null;
        try {
            //create cursor
            cursor = db.rawQuery("SELECT Ingredient, Required_Quantity, Unit" +
                    " FROM RECIPE_INGREDIENTS WHERE Recipe_Id = ?",
                    new String[]{String.valueOf(recipeId)});

            while (cursor.moveToNext()){
                //declare variables
                String ingredient = cursor.getString(0);
                double quantity = cursor.getDouble(1);
                String units = cursor.getString(2);

                ContentValues contentValues = new ContentValues();
                contentValues.put("Quantity", getPantryQuantity(ingredient, units) - quantity);

                db.update("PANTRY", contentValues, "Name = ? AND Unit = ?",
                        new String[]{ingredient, units});
            }
            db.setTransactionSuccessful();
            return true;
        } finally {
            if(cursor != null){
                cursor.close();
            }
            db.endTransaction();
        }

    }

    //create method insertRecipe()
   public long insertRecipe(SQLiteDatabase db, String name, String description, String category, int prepTime,  String preparationSteps){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Category", category);
        contentValues.put("Description", description);
        contentValues.put("Prep_Time", prepTime);
       contentValues.put("Preparation_Steps", preparationSteps);
        long result = db.insert("RECIPES", null, contentValues);
        return result;
    }
    //create method addIngredient()
    public void addIngredient(SQLiteDatabase db,long recipeId,String ingredient,double quantity,
                              String units){
        ContentValues values = new ContentValues();
        values.put("Recipe_Id", recipeId);
        values.put("Ingredient", ingredient);
        values.put("Required_Quantity", quantity);
        values.put("Unit", units);
        db.insert("RECIPE_INGREDIENTS", null, values);
    }
    //create method addStep()
    public void addStep(SQLiteDatabase db, long recipeId, int stepNumber, String step){
        ContentValues values = new ContentValues();
        values.put("Recipe_Id", recipeId);
        values.put("Step_Number", stepNumber);
        values.put("Step", step);
        db.insert("RECIPE_STEPS", null, values);
    }
    //create method savePantryCategories()
    public boolean savePantryCategories(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        long result = db.insert("PANTRY", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    //create method saveIngredients()
    public boolean saveIngredients(String name, int quantity, String units, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Quantity", quantity);
        contentValues.put("Unit", units);
        contentValues.put("ExpiryDate", expiryDate);
        long result = db.insert("PANTRY", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    //create method getIngredients()
    public ArrayList<ingredientsModel> getIngredients(){
        //create arrayList
        ArrayList<ingredientsModel> ingredientsModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //create cursor
        Cursor cursor = db.rawQuery("SELECT * FROM PANTRY",null);
        if(cursor.moveToFirst()){
         do{
             ingredientsModel ingredientsModel = new ingredientsModel();
             ingredientsModel.setIngredientId(cursor.getInt(0));
             ingredientsModel.setIngredientName(cursor.getString(1));
             ingredientsModel.setQuantity(cursor.getInt(2));
             ingredientsModel.setUnits(cursor.getString(3));
             ingredientsModel.setExpiryDate(cursor.getString(4));
             ingredientsModelList.add(ingredientsModel);
         }while (cursor.moveToNext());

        }
        cursor.close();
        return ingredientsModelList;
    }
    //create method getSelectedIngredient()
    public ingredientsModel getSelectedIngredient(int ingredientId){
        //create arrayList
        ingredientsModel ingredientsModel = new ingredientsModel();
        SQLiteDatabase db = this.getReadableDatabase();
        //create cursor
        Cursor cursor = db.rawQuery("SELECT * FROM PANTRY WHERE Id = ?", new String[]{String.valueOf(ingredientId)});
        if(cursor.moveToFirst()){
            ingredientsModel.setIngredientId(cursor.getInt(0));
            ingredientsModel.setIngredientName(cursor.getString(1));
            ingredientsModel.setQuantity(cursor.getInt(2));
            ingredientsModel.setUnits(cursor.getString(3));
            ingredientsModel.setExpiryDate(cursor.getString(4));
        }
        cursor.close();
        return ingredientsModel;
        }
   //create method updateIngredient()
   public void updateIngredient(int ingredientId,String name, double quantity, String units, String expiryDate){
        SQLiteDatabase db = this.getWritableDatabase();
        //create content values
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Quantity", quantity);
        contentValues.put("Unit", units);
        contentValues.put("ExpiryDate", expiryDate);
        //update values in database
        db.update("PANTRY", contentValues, "Id = ?", new String[]{String.valueOf(ingredientId)});
   }



    //create method getRecipes()
    public ArrayList<recipesModel> getRecipes(){
        //create arrayList
        ArrayList<recipesModel> recipesModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //create cursor
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES",null);
        if(cursor.moveToFirst()){
            do{
                recipesModel recipesModel = new recipesModel();
                recipesModel.setId(cursor.getInt(0));
                recipesModel.setName(cursor.getString(1));
                recipesModel.setCategory(cursor.getString(2));
                recipesModel.setDescription(cursor.getString(3));
                recipesModel.setPrepTime(cursor.getInt(4));
                recipesModel.setPreparationSteps(cursor.getString(5));
                recipesModelList.add(recipesModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return recipesModelList;
    }
    //create method getRecipeInfo()
    public recipesModel getRecipeInfo(String recipeId){
        //create recipesModel
        recipesModel recipesModel = new recipesModel();
        SQLiteDatabase db = this.getReadableDatabase();
        //create cursor
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPES WHERE Id = ?", new String[]{recipeId});
        if(cursor.moveToFirst()){
            recipesModel.setId(cursor.getInt(0));
            recipesModel.setName(cursor.getString(1));
            recipesModel.setCategory(cursor.getString(2));
            recipesModel.setDescription(cursor.getString(3));
            recipesModel.setPrepTime(cursor.getInt(4));
            recipesModel.setPreparationSteps(cursor.getString(5));
        }
        cursor.close();
        return recipesModel;
    }
    //create method getRecipeIngredients()
    public ArrayList<ingredientsModel> getRecipeIngredients(int recipeId){
        //create ingredientsModel
        ArrayList<ingredientsModel> ingredientsModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //create cursor
        Cursor cursor = db.rawQuery("SELECT * FROM RECIPE_INGREDIENTS WHERE Recipe_Id = ?",
                new String[]{String.valueOf(recipeId)});
        if(cursor.moveToFirst()){
            do{
                ingredientsModel ingredientsModel = new ingredientsModel();
                ingredientsModel.setRecipeId(cursor.getInt(1));
                ingredientsModel.setIngredientName(cursor.getString(2));
                ingredientsModel.setQuantity(cursor.getDouble(3));
                ingredientsModel.setUnits(cursor.getString(4));
                ingredientsModelList.add(ingredientsModel);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return ingredientsModelList;
    }
    //create method getAvailableRecipes()
    public ArrayList<recipesModel> getAvailableRecipes(){
        //create arrayList
        ArrayList<recipesModel> recipesModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //create cursor
        Cursor cursor = db.rawQuery("SELECT r.Id, r.Name, r.Category, r.Description, r.Prep_Time, r.Preparation_Steps " +
                "FROM RECIPES r " +
                "WHERE NOT EXISTS (" +
                "SELECT 1 " +
                "FROM RECIPE_INGREDIENTS ri " +
                "LEFT JOIN PANTRY p " +
                " ON LOWER(TRIM(p.Name)) = LOWER(TRIM(ri.Ingredient)) " +
                " AND LOWER(TRIM(p.Unit)) = LOWER(TRIM(ri.Unit)) " +
                " WHERE ri.Recipe_Id = r.Id " +
                " AND (" +
                " p.Id IS NULL " +
                " OR p.Quantity < ri.Required_Quantity" +
                "  )" +
                ") " +
                "ORDER BY r.Name ASC",null);
     try{
         if(cursor.moveToFirst()){
             do{
                 int id = cursor.getInt(0);
                 String name = cursor.getString(1);
                 String category = cursor.getString(2);
                 String description = cursor.getString(3);
                 int prepTime = cursor.getInt(4);
                 String preparationSteps = cursor.getString(5);
                 recipesModel recipesModel = new recipesModel(id, name, category, description, prepTime, preparationSteps);
                 recipesModelList.add(recipesModel);
             }while (cursor.moveToNext());
         }
         }finally {
         cursor.close();
     }
        return recipesModelList;
     }
     //create method deleteIngredient()
     public void deleteIngredient(int ingredientId){
         SQLiteDatabase db = this.getWritableDatabase();
         db.delete("PANTRY", "Id = ?", new String[]{String.valueOf(ingredientId)});
     }
}

