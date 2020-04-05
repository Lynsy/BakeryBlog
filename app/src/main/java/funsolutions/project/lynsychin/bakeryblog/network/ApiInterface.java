package funsolutions.project.lynsychin.bakeryblog.network;

import java.util.List;

import funsolutions.project.lynsychin.bakeryblog.network.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
