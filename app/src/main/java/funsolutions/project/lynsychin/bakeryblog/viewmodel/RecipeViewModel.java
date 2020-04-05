package funsolutions.project.lynsychin.bakeryblog.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import funsolutions.project.lynsychin.bakeryblog.database.RecipeDatabase;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;

public class RecipeViewModel extends ViewModel {

    private RecipeDatabase mDB;

    private LiveData<List<RecipeEntry>> recipes;

    public RecipeViewModel(RecipeDatabase db){
        mDB = db;
        recipes = db.recipeDao().loadAllRecipes();
    }

    public LiveData<List<RecipeEntry>> getRecipes(){ return recipes; }

    public void addRecipe(RecipeEntry entry){
        mDB.recipeDao().insertRecipe(entry);
    }

    public LiveData<RecipeEntry> loadRecipeById(int id){
        return mDB.recipeDao().loadRecipeById(id);
    }

    public RecipeEntry getRecipeById(int id){
        return mDB.recipeDao().getRecipeById(id);
    }

    public boolean hasRecipe(int id) {
        RecipeEntry entry = mDB.recipeDao().getRecipeById(id);
        return entry != null;
    }
}
