package funsolutions.project.lynsychin.bakeryblog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import funsolutions.project.lynsychin.bakeryblog.adapter.StepAdapter;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnRecipeClickListener{

    public static final String KEY_RECIPE_ID = "recipe_id";

    @BindView(R.id.master_list_fragment) View masterListFragment;

    @Nullable
    @BindView(R.id.recipeDetailContainer)
    ConstraintLayout detailContainer;

    @Nullable
    @BindView(R.id.rvSteps)
    RecyclerView rvSteps;

    @Nullable
    @BindView(R.id.nav_back) ImageView btnBackArrow;

    public boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isTwoPane = findViewById(R.id.recipeDetailContainer) != null;

        if(isTwoPane && isLandScapeMode()){
            assert btnBackArrow != null;
            btnBackArrow.setVisibility(View.GONE);

            assert detailContainer != null;
            detailContainer.setVisibility(View.GONE);
        } else if(isTwoPane && !isLandScapeMode()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            masterListFragment.setLayoutParams(params);
        }

        if(getIntent().hasExtra(KEY_RECIPE_ID)){
            RecipeEntry recipe = (RecipeEntry) getIntent().getSerializableExtra(KEY_RECIPE_ID);
            onRecipeSelected(recipe);
        }
    }

    private boolean isLandScapeMode() {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
    }

    @Override
    public void onRecipeSelected(RecipeEntry recipe) {
        if(isTwoPane && isLandScapeMode()){
            assert detailContainer != null;
            detailContainer.setVisibility(View.VISIBLE);

            setSecondPaneData(recipe);
        } else {
            Intent openRecipeDetailIntent = new Intent(this, RecipeDetailActivity.class);
            openRecipeDetailIntent.putExtra(KEY_RECIPE_ID, recipe.getId());
            startActivity(openRecipeDetailIntent);
        }
    }

    private void setSecondPaneData(RecipeEntry recipe) {
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        rvSteps.setLayoutManager(manager);

        StepAdapter stepAdaper = new StepAdapter(MainActivity.this, recipe.getSteps(), recipe);
        rvSteps.setAdapter(stepAdaper);
    }
}
