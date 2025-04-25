package pl.pjwstk.bmiapp.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.pjwstk.bmiapp.R;
import pl.pjwstk.bmiapp.ui.activities.MainActivity;
import pl.pjwstk.bmiapp.ui.fragments.calculators.BmiCalculatorFragment;
import pl.pjwstk.bmiapp.ui.fragments.calculators.CalorieCalculatorFragment;
import pl.pjwstk.bmiapp.ui.fragments.home.HomeFragment;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setup() {
        scenario = activityScenarioRule.getScenario();
    }

    @Test
    public void testActivityLaunch() {
        onView(withId(R.id.bottomNavigationView)).check(matches(isDisplayed()));

        scenario.onActivity(activity -> {
            assertEquals("FitApp", activity.getTitle());
        });
    }

    @Test
    public void testNavigationToHomeFragment() {
        onView(withId(R.id.homeFragment)).perform(click());

        scenario.onActivity(activity -> {
            Fragment currentFragment = getCurrentFragment(activity);
            assertNotNull(currentFragment);
            assertEquals(HomeFragment.class, currentFragment.getClass());
        });
    }

    @Test
    public void testNavigationToBmiCalculatorFragment() {
        onView(withId(R.id.bmiCalculatorFragment)).perform(click());

        scenario.onActivity(activity -> {
            Fragment currentFragment = getCurrentFragment(activity);
            assertNotNull(currentFragment);
            assertEquals(BmiCalculatorFragment.class, currentFragment.getClass());
        });

        onView(withId(R.id.weightEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.heightEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.calculateButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationToCalorieCalculatorFragment() {
        onView(withId(R.id.calorieCalculatorFragment)).perform(click());

        scenario.onActivity(activity -> {
            Fragment currentFragment = getCurrentFragment(activity);
            assertNotNull(currentFragment);
            assertEquals(CalorieCalculatorFragment.class, currentFragment.getClass());
        });

        onView(withId(R.id.weightEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.heightEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.ageEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.genderRadioGroup)).check(matches(isDisplayed()));
        onView(withId(R.id.activityLevelSpinner)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationSequence() {
        onView(withId(R.id.homeFragment)).perform(click());

        scenario.onActivity(activity -> {
            Fragment currentFragment = getCurrentFragment(activity);
            assertEquals(HomeFragment.class, currentFragment.getClass());
        });

        onView(withId(R.id.bmiCalculatorFragment)).perform(click());

        scenario.onActivity(activity -> {
            Fragment currentFragment = getCurrentFragment(activity);
            assertEquals(BmiCalculatorFragment.class, currentFragment.getClass());
        });

        onView(withId(R.id.calorieCalculatorFragment)).perform(click());

        scenario.onActivity(activity -> {
            Fragment currentFragment = getCurrentFragment(activity);
            assertEquals(CalorieCalculatorFragment.class, currentFragment.getClass());
        });

        onView(withId(R.id.homeFragment)).perform(click());

        scenario.onActivity(activity -> {
            Fragment currentFragment = getCurrentFragment(activity);
            assertEquals(HomeFragment.class, currentFragment.getClass());
        });
    }

    @Test
    public void testAppBarTitle() {
        scenario.onActivity(activity -> {
            assertEquals("FitApp", activity.getTitle().toString());
        });

        onView(withId(R.id.bmiCalculatorFragment)).perform(click());
        scenario.onActivity(activity -> {
            assertEquals("FitApp", activity.getTitle().toString());
        });

        onView(withId(R.id.calorieCalculatorFragment)).perform(click());
        scenario.onActivity(activity -> {
            assertEquals("FitApp", activity.getTitle().toString());
        });
    }

    private Fragment getCurrentFragment(MainActivity activity) {
        return activity.getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment)
                .getChildFragmentManager()
                .getFragments().get(0);
    }


}