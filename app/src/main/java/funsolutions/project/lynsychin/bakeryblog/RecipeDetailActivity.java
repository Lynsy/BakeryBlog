package funsolutions.project.lynsychin.bakeryblog;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import funsolutions.project.lynsychin.bakeryblog.adapter.StepAdapter;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeDatabase;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;
import funsolutions.project.lynsychin.bakeryblog.viewmodel.RecipeViewModel;
import funsolutions.project.lynsychin.bakeryblog.viewmodel.RecipeViewModelFactory;
import funsolutions.project.lynsychin.bakeryblog.watcher.FilterRecipeWatcher;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final int DEFAULT_RECIPE_ID = -99;

    @BindView(R.id.rvSteps) RecyclerView rvSteps;

    private RecipeViewModel mRecipeViewModel;
    private RecipeEntry mRecipe;
    private int mRecipeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mRecipeId = getIntent().getIntExtra(MainActivity.KEY_RECIPE_ID, DEFAULT_RECIPE_ID);

        setViewModel();
    }

    private void setViewModel(){
        RecipeDatabase db = RecipeDatabase.getInstance(this);
        RecipeViewModelFactory factory = new RecipeViewModelFactory(db);

        mRecipeViewModel = new ViewModelProvider(RecipeDetailActivity.this, factory).get(RecipeViewModel.class);
        mRecipeViewModel.loadRecipeById(mRecipeId).observe(RecipeDetailActivity.this, new Observer<RecipeEntry>() {
            @Override
            public void onChanged(@Nullable RecipeEntry entry) {
                mRecipeViewModel.loadRecipeById(mRecipeId).removeObserver(this);
                mRecipe = entry;

                if(mRecipe != null){
                    LinearLayoutManager manager = new LinearLayoutManager(RecipeDetailActivity.this);
                    rvSteps.setLayoutManager(manager);

                    StepAdapter stepAdaper = new StepAdapter(RecipeDetailActivity.this, mRecipe.getSteps(), mRecipe);
                    rvSteps.setAdapter(stepAdaper);
                }
            }
        });
    }

    public void onBack(View view) {
        onBackPressed();
    }
}
