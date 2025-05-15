package pl.pjwstk.bmiapp.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pl.pjwstk.bmiapp.R;
import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment;
import pl.pjwstk.bmiapp.utils.NavigationHelper;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    NavController navController;
    BottomNavigationView bottomNav;
    static final String TAG = "MainActivity";
    boolean isNavigatingFromBottomNav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_DarkActionBar);
        setTitle("FitApp");
        setContentView(R.layout.activity_main);

        try {
            setupNavigation();
        } catch (Exception e) {
            Log.e(TAG, "Błąd podczas konfiguracji nawigacji", e);
        }
    }

    private void setupNavigation() {
        bottomNav = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.homeFragment, R.id.bmiCalculatorFragment,
                    R.id.calorieCalculatorFragment
            ).build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
            bottomNav.setOnItemSelectedListener(this);
            adjustFragmentContainerMargins();
            setupDestinationChangedListener(navHostFragment);
        }
    }

    private void adjustFragmentContainerMargins() {
        findViewById(R.id.nav_host_fragment).post(() -> {
            ViewGroup.MarginLayoutParams layoutParams =
                    (ViewGroup.MarginLayoutParams) findViewById(R.id.nav_host_fragment).getLayoutParams();
            layoutParams.topMargin = 0;
            findViewById(R.id.nav_host_fragment).setLayoutParams(layoutParams);
        });
    }

    private void setupDestinationChangedListener(NavHostFragment navHostFragment) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
            if (currentFragment instanceof BaseFragment) {
                ((BaseFragment) currentFragment).refreshLayout();
            }

            if (!isNavigatingFromBottomNav) {
                int destId = destination.getId();
                bottomNav.setOnItemSelectedListener(null);

                if (destId == R.id.homeFragment) {
                    bottomNav.setSelectedItemId(R.id.homeFragment);
                } else if (destId == R.id.bmiCalculatorFragment) {
                    bottomNav.setSelectedItemId(R.id.bmiCalculatorFragment);
                } else if (destId == R.id.calorieCalculatorFragment) {
                    bottomNav.setSelectedItemId(R.id.calorieCalculatorFragment);
                }

                bottomNav.setOnItemSelectedListener(this);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            isNavigatingFromBottomNav = true;
            return NavigationHelper.handleNavigation(item.getItemId(), navController);
        } finally {
            isNavigatingFromBottomNav = false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController != null && navController.navigateUp() || super.onSupportNavigateUp();
    }
}