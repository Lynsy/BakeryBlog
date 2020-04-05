package funsolutions.project.lynsychin.bakeryblog.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import funsolutions.project.lynsychin.bakeryblog.network.model.Ingredient;
import funsolutions.project.lynsychin.bakeryblog.network.model.Recipe;
import funsolutions.project.lynsychin.bakeryblog.network.model.Step;

@Entity(tableName = "recipe")
public class RecipeEntry implements Serializable {

    @PrimaryKey
    Integer id;
    String name;
    List<Ingredient> ingredients;
    List<Step> steps;
    Integer servings;
    String image;

    public RecipeEntry(Integer id, String name, List<Ingredient> ingredients, List<Step> steps, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    @Ignore
    public RecipeEntry(Recipe recipeDB) {
        this.id = recipeDB.getId();
        this.name = recipeDB.getName();
        this.ingredients = recipeDB.getIngredients();
        this.steps = recipeDB.getSteps();
        this.servings = recipeDB.getServings();
        this.image = recipeDB.getImage();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
