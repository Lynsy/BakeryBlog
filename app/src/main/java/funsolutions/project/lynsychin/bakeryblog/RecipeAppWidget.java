package funsolutions.project.lynsychin.bakeryblog;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;
import java.util.Random;

import funsolutions.project.lynsychin.bakeryblog.database.RecipeDatabase;
import funsolutions.project.lynsychin.bakeryblog.database.RecipeEntry;
import funsolutions.project.lynsychin.bakeryblog.helper.AppExecutors;
import funsolutions.project.lynsychin.bakeryblog.network.model.Ingredient;
import funsolutions.project.lynsychin.bakeryblog.viewmodel.RecipeViewModel;
import funsolutions.project.lynsychin.bakeryblog.viewmodel.RecipeViewModelFactory;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_name, pendingIntent);
        views.setOnClickPendingIntent(R.id.imageView, pendingIntent);

        RecipeDatabase db = RecipeDatabase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<RecipeEntry> entries = db.recipeDao().getAllRecipes();
                if(entries != null && !entries.isEmpty()){
                    RecipeEntry randomRecipe = entries.get(new Random().nextInt(entries.size()));
                    views.setTextViewText(R.id.appwidget_name, context.getString(R.string.lbl_today_bake, randomRecipe.getName()));
                } else {
                    views.setTextViewText(R.id.appwidget_name, context.getString(R.string.lbl_check_new_recipe_in_app));
                }

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        });
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

