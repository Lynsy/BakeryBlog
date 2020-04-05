package funsolutions.project.lynsychin.bakeryblog.network.model;

import java.util.List;

public class Recipe {

    Integer id;
    String name;
    List<Ingredient> ingredients;
    List<Step> steps;
    Integer servings;
    String image;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
