package funsolutions.project.lynsychin.bakeryblog.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import funsolutions.project.lynsychin.bakeryblog.network.model.Ingredient;

public class IngredientListConverter {

    @TypeConverter
    public static List<Ingredient> fromString(String value) {
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String listToString(List<Ingredient> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
