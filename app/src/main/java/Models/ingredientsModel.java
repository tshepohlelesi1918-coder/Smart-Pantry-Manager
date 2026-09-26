package Models;

public class ingredientsModel {
    //declare variables
    private int ingredientId;
    private int recipeId;
    private String ingredientName;
    private String category;
    private double quantity;
    private String units;
    private String expiryDate;

    //default constructor
    public ingredientsModel() {
    }

    public ingredientsModel(int ingredientId, int recipeId,String ingredientName, String category, double quantity, String units, String expiryDate) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.category = category;
        this.quantity = quantity;
        this.units = units;
        this.expiryDate = expiryDate;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
