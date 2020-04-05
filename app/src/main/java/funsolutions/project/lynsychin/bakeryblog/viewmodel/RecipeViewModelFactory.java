package funsolutions.project.lynsychin.bakeryblog.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import funsolutions.project.lynsychin.bakeryblog.database.RecipeDatabase;

public class RecipeViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RecipeDatabase mDB;

    public RecipeViewModelFactory(RecipeDatabase db){
        mDB = db;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RecipeViewModel(mDB);
    }
}
