package funsolutions.project.lynsychin.bakeryblog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import funsolutions.project.lynsychin.bakeryblog.R;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private Context mContext;
    private List<RecipeEntry> mData = new ArrayList<>();
    private OnRecipeAdapterListener mListener;

    public interface OnRecipeAdapterListener {
        void onRecipeSelected(RecipeEntry recipe);
    }

    public RecipeAdapter(Context context, OnRecipeAdapterListener listener){
        mContext = context;
        mListener = listener;
    }

    public void setData(List<RecipeEntry> data){
        mData = data;
        notifyDataSetChanged();
    }

    public List<RecipeEntry> getData() {
        return mData;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bakery, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeEntry recipe = mData.get(position);

        if(recipe.getImage().isEmpty()){
            holder.imgRecipe.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ph_no_image));
        } else {
            Picasso.get()
                    .load(recipe.getImage())
                    .error(R.drawable.ph_no_image)
                    .into(holder.imgRecipe);
        }

        holder.tvRecipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imgRecipe) ImageView imgRecipe;
        @BindView(R.id.tvRecipeName) TextView tvRecipeName;

        private RecipeViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onRecipeSelected(mData.get(getAdapterPosition()));
            }
        }
    }
}
