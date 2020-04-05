package funsolutions.project.lynsychin.bakeryblog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import funsolutions.project.lynsychin.bakeryblog.adapter.RecipeAdapter;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeDatabase;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;
import funsolutions.project.lynsychin.bakeryblog.helper.AppExecutors;
import funsolutions.project.lynsychin.bakeryblog.network.model.Recipe;
import funsolutions.project.lynsychin.bakeryblog.network.ApiClient;
import funsolutions.project.lynsychin.bakeryblog.network.ApiInterface;
import funsolutions.project.lynsychin.bakeryblog.viewmodel.RecipeViewModel;
import funsolutions.project.lynsychin.bakeryblog.viewmodel.RecipeViewModelFactory;
import funsolutions.project.lynsychin.bakeryblog.watcher.FilterRecipeWatcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterListFragment extends Fragment implements RecipeAdapter.OnRecipeAdapterListener {

    @BindView(R.id.etSearchRecipe) EditText etSearchRecipe;
    @BindView(R.id.rvRecipesList) RecyclerView rvRecipeList;

    private RecipeAdapter mAdapter;

    private OnRecipeClickListener mListener;

    private ApiInterface mApiInterface;
    private RecipeViewModel mRecipeViewModel;
    private boolean fetchFromNetwork = true;

    public interface OnRecipeClickListener {
        void onRecipeSelected(RecipeEntry recipe);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        int spanCount = 2;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        rvRecipeList.setLayoutManager(gridLayoutManager);
        rvRecipeList.setHasFixedSize(true);

        mAdapter = new RecipeAdapter(getActivity(), this);
        rvRecipeList.setAdapter(mAdapter);

        setViewModel();
        if(fetchFromNetwork) {
            fetchFromNetwork = false;
            updateRecipes();
        }

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mListener = (OnRecipeClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onRecipeSelected(RecipeEntry recipe) {
        mListener.onRecipeSelected(recipe);
    }

    private void setViewModel(){
        RecipeDatabase db = RecipeDatabase.getInstance(getActivity());
        RecipeViewModelFactory factory = new RecipeViewModelFactory(db);

        mRecipeViewModel = new ViewModelProvider(MasterListFragment.this, factory).get(RecipeViewModel.class);
        mRecipeViewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> entries) {
                mAdapter.setData(entries);
                etSearchRecipe.addTextChangedListener(new FilterRecipeWatcher(getActivity(), mAdapter));
            }
        });
    }

    private void updateRecipes(){
        Call<List<Recipe>> call = mApiInterface.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    List<Recipe> recipeList = response.body();
                    if (recipeList != null && !recipeList.isEmpty()) {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                for (Recipe newRecipe : recipeList) {
                                    if (!mRecipeViewModel.hasRecipe(newRecipe.getId())) {
                                        RecipeEntry recipe = new RecipeEntry(newRecipe);
                                        mRecipeViewModel.addRecipe(recipe);
                                    }
                                }
                            }
                        });
                    } else {
                        // TODO: lOAD FROM LOCAL DB
                        Toast.makeText(getActivity(), "We found nothing...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // TODO: LOAD FROM LOCAL DB
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                // TODO: LOAD FROM LOCAL DB
                Toast.makeText(getActivity(), "WHAT!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
