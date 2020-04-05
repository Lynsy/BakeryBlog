package funsolutions.project.lynsychin.bakeryblog.watcher;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;

import funsolutions.project.lynsychin.bakeryblog.adapter.RecipeAdapter;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;
import funsolutions.project.lynsychin.bakeryblog.network.model.Recipe;

public class FilterRecipeWatcher implements TextWatcher {

    private Context mContext;
    private RecipeAdapter mAdapter;
    private List<RecipeEntry> mOriginalData;

    public FilterRecipeWatcher(Context context, RecipeAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
        mOriginalData = mAdapter.getData();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.toString().isEmpty()){
            mAdapter.setData(mOriginalData);
        } else {
            filterList(s.toString());
        }
    }

    private void filterList(String query) {
        List<RecipeEntry> temp = new ArrayList<>();
        for(RecipeEntry recipe : mOriginalData){
            String name = recipe.getName().toLowerCase();
            if(name.contains(query.toLowerCase()))
                temp.add(recipe);
        }
        mAdapter.setData(temp);
    }
}
