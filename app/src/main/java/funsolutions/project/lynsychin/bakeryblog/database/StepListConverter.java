package funsolutions.project.lynsychin.bakeryblog.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import funsolutions.project.lynsychin.bakeryblog.network.model.Step;

public class StepListConverter {

    @TypeConverter
    public static List<Step> fromString(String value) {
        Type listType = new TypeToken<List<Step>>() {}.getType();
        List<Step> steps = new Gson().fromJson(value,listType);
        return steps;
    }

    @TypeConverter
    public static String listToString(List<Step> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
