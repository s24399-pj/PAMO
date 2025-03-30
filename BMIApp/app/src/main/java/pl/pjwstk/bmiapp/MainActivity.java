package pl.pjwstk.bmiapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavController navController;
    private BottomNavigationView bottomNav;
    private static final String TAG = "MainActivity";

    // Flaga do śledzenia źródła nawigacji - zapobiega zapętleniu
    private boolean isNavigatingFromBottomNav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ustawiamy temat bezpośrednio przed setContentView
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_DarkActionBar);

        // Ustawiamy tytuł aplikacji
        setTitle("FitApp");

        setContentView(R.layout.activity_main);

        try {
            // Setup bottom navigation
            bottomNav = findViewById(R.id.bottomNavigationView);

            // Ważne: Bezpiecznie pobieramy NavController
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);

            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();

                // Configure app bar
                AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                        R.id.homeFragment, R.id.bmiCalculatorFragment,
                        R.id.calorieCalculatorFragment
                ).build();

                NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);

                // Ważna zmiana - ręczna obsługa kliknięć menu zamiast automatycznej
                bottomNav.setOnNavigationItemSelectedListener(this);

                // Ustawienie prawidłowych marginesów dla kontenera fragmentów
                View fragmentContainer = findViewById(R.id.nav_host_fragment);
                if (fragmentContainer != null) {
                    ViewGroup.MarginLayoutParams layoutParams =
                            (ViewGroup.MarginLayoutParams) fragmentContainer.getLayoutParams();
                    layoutParams.topMargin = 0;
                    fragmentContainer.setLayoutParams(layoutParams);
                }

                // Dodajemy listener zmiany fragmentu
                navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                    // Resetowanie layoutu dla każdego fragmentu
                    Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
                    if (currentFragment instanceof BaseFragment) {
                        ((BaseFragment) currentFragment).fixLayout();
                    }

                    // Aktualizacja zaznaczonego elementu w dolnym menu TYLKO gdy nie nawigujemy z menu
                    if (!isNavigatingFromBottomNav) {
                        int destId = destination.getId();
                        // Tymczasowo usuwamy listener aby uniknąć pętli
                        bottomNav.setOnNavigationItemSelectedListener(null);

                        if (destId == R.id.homeFragment) {
                            bottomNav.setSelectedItemId(R.id.homeFragment);
                        } else if (destId == R.id.bmiCalculatorFragment) {
                            bottomNav.setSelectedItemId(R.id.bmiCalculatorFragment);
                        } else if (destId == R.id.calorieCalculatorFragment) {
                            bottomNav.setSelectedItemId(R.id.calorieCalculatorFragment);
                        }

                        // Przywracamy listener
                        bottomNav.setOnNavigationItemSelectedListener(this);
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Błąd podczas konfiguracji nawigacji", e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        try {
            // Ustawiamy flagę że nawigacja pochodzi z menu
            isNavigatingFromBottomNav = true;

            // Tworzenie opcji nawigacji, które czyszczą stos nawigacji
            NavOptions navOptions = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(navController.getGraph().getStartDestination(), false)
                    .build();

            // Obsługa kliknięć na elementy dolnego menu
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment, null, navOptions);
                return true;
            } else if (itemId == R.id.bmiCalculatorFragment) {
                navController.navigate(R.id.bmiCalculatorFragment, null, navOptions);
                return true;
            } else if (itemId == R.id.calorieCalculatorFragment) {
                navController.navigate(R.id.calorieCalculatorFragment, null, navOptions);
                return true;
            }
            return false;
        } finally {
            // Resetujemy flagę po zakończeniu nawigacji
            isNavigatingFromBottomNav = false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return navController.navigateUp() || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }
}