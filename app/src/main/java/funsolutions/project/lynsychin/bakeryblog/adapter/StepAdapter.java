package funsolutions.project.lynsychin.bakeryblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import funsolutions.project.lynsychin.bakeryblog.DisplayMediaActivity;
import funsolutions.project.lynsychin.bakeryblog.R;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;
import funsolutions.project.lynsychin.bakeryblog.network.model.Ingredient;
import funsolutions.project.lynsychin.bakeryblog.network.model.Step;

public class StepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Step> mData;

    // For the header
    private RecipeEntry mRecipe;

    private Context mContext;

    public StepAdapter(Context context, List<Step> data, RecipeEntry recipe) {
        mContext = context;
        mData = data;

        mRecipe = recipe;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step, parent, false);
            return new StepViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_detail_header, parent, false);
            return new HeaderViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            if(mRecipe.getImage().isEmpty()){
                headerViewHolder.imgRecipe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ph_no_image));
            } else {
                Picasso.get()
                        .load(mRecipe.getImage())
                        .error(R.drawable.ph_no_image)
                        .into(headerViewHolder.imgRecipe);
            }

            StringBuilder fancyName = getFancyName(mRecipe.getName());

            headerViewHolder.tvRecipeName.setText(Html.fromHtml(String.valueOf(fancyName)));
            headerViewHolder.tvServings.setText(mContext.getString(R.string.lbl_serving, mRecipe.getServings()));

            for(Ingredient ingredient : mRecipe.getIngredients()){
                headerViewHolder.containerIngredients.addView(getListOfLabels(ingredient));
            }
        }
        else if (holder instanceof StepViewHolder){
            StepViewHolder stepViewHolder = (StepViewHolder) holder;

            Step step = mData.get(position-1);

            if(!step.getVideoURL().isEmpty()){
                stepViewHolder.tvFullScreen.setVisibility(View.VISIBLE);
                stepViewHolder.pvMedia.setVisibility(View.VISIBLE);
                initializePlayer(stepViewHolder.pvMedia, step.getVideoURL());
            } else {
                stepViewHolder.tvFullScreen.setVisibility(View.GONE);
                stepViewHolder.pvMedia.setVisibility(View.GONE);
            }

            stepViewHolder.tvShortDescription.setText(step.getShortDescription());

            if(step.getDescription().equals(step.getShortDescription())){
                stepViewHolder.tvDescription.setVisibility(View.GONE);
            } else {
                stepViewHolder.tvDescription.setVisibility(View.VISIBLE);
                stepViewHolder.tvDescription.setText(step.getDescription());
            }
        }
    }

    private void initializePlayer(PlayerView pvMedia, String url) {
        SimpleExoPlayer mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        pvMedia.setPlayer(mPlayer);

        MediaSource mediaSource = new ExtractorMediaSource
                .Factory(new DefaultHttpDataSourceFactory(mContext.getString(R.string.app_name)))
                .createMediaSource(Uri.parse(url));
        mPlayer.prepare(mediaSource, true, false);
    }

    private LinearLayout getListOfLabels(Ingredient ingredient) {
        LinearLayout ingredientContainer = new LinearLayout(mContext);
        ingredientContainer.setOrientation(LinearLayout.HORIZONTAL);
        ingredientContainer.setPadding(0, 0, 0, 16);

        TextView bullet = new TextView(mContext);
        bullet.setPadding(0, 0, 48, 0);
        bullet.setTextAppearance(mContext, R.style.itemTitle);
        bullet.setText(R.string.bullet);
        ingredientContainer.addView(bullet);

        TextView ingredientLabel = new TextView(mContext);
        ingredientLabel.setTextAppearance(mContext, R.style.itemTitle);
        ingredientLabel.setText(mContext.getString(R.string.list_ingredients,
                String.valueOf(ingredient.getQuantity()),
                ingredient.getMeasure(),
                ingredient.getIngredient()));
        ingredientContainer.addView(ingredientLabel);

        return ingredientContainer;
    }

    private StringBuilder getFancyName(String name) {
        String[] nameSplit = name.split(" ");
        if(nameSplit.length <= 1){
            return new StringBuilder(name);
        } else {
            String firstPart = nameSplit[0];

            StringBuilder spanBuilder = new StringBuilder(firstPart + " <b>");

            for(int idx = 1; idx < nameSplit.length; idx++){
                String aPart = nameSplit[idx];
                spanBuilder.append(aPart).append(" ");
            }
            spanBuilder.append("<\b>");
            return spanBuilder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgRecipe) ImageView imgRecipe;
        @BindView(R.id.lblServings) TextView tvServings;
        @BindView(R.id.tvRecipeName) TextView tvRecipeName;
        @BindView(R.id.containerIngredients) LinearLayout containerIngredients;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvFullScreen) TextView tvFullScreen;
        @BindView(R.id.pvMedia) PlayerView pvMedia;
        @BindView(R.id.tvShortDescription) TextView tvShortDescription;
        @BindView(R.id.tvDescription) TextView tvDescription;

        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tvFullScreen.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String url = mData.get(getAdapterPosition()-1).getVideoURL();
            if(!url.isEmpty()) {
                Intent redirectToMedieIntent = new Intent(mContext, DisplayMediaActivity.class);
                redirectToMedieIntent.putExtra(DisplayMediaActivity.KEY_MEDIA_URL, url);
                mContext.startActivity(redirectToMedieIntent);
            }

        }
    }
}
