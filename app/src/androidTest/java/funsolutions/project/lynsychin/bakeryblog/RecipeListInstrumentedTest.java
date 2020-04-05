package funsolutions.project.lynsychin.bakeryblog;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
//import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class RecipeListInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void performClickOnRecyclerView() {
//        Espresso.onView(ViewMatchers.withId(R.id.rvRecipesList))
//                .inRoot(RootMatchers.withDecorView(
//                        Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testCaseForRecyclerScroll() {
        // Get total item of RecyclerView
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.rvRecipesList);
        int itemCount = recyclerView.getAdapter().getItemCount();

        // Scroll to end of page with position
//        Espresso.onView(ViewMatchers.withId(R.id.rvRecipesList))
//                .inRoot(RootMatchers.withDecorView(
//                        Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView())))
//                .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
    }

}
