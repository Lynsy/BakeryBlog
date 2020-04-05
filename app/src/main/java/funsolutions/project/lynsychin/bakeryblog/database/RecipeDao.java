package funsolutions.project.lynsychin.bakeryblog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<RecipeEntry>> loadAllRecipes();

    @Query("SELECT * FROM recipe")
    List<RecipeEntry> getAllRecipes();

    @Insert
    void insertRecipe(RecipeEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(RecipeEntry entry);

    // Using this to verify if movie is already in DB
    @Query("SELECT * FROM recipe WHERE id = :id")
    RecipeEntry getRecipeById(int id);

    @Query("SELECT * FROM recipe WHERE id = :id")
    LiveData<RecipeEntry> loadRecipeById(int id);

    @Query("DELETE FROM recipe")
    void deleteAllRecipes();

    @Query("DELETE FROM recipe WHERE id = :id")
    void deleteRecipeById(int id);

}
