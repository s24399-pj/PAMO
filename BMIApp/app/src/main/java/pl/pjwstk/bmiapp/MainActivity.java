package pl.pjwstk.bmiapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ustawiamy temat bezpośrednio przed setContentView
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_DarkActionBar);

        setContentView(R.layout.activity_main);

        try {
            // Setup bottom navigation
            BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);

            // Configure app bar
            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.homeFragment, R.id.bmiCalculatorFragment,
                    R.id.calorieCalculatorFragment, R.id.recipesFragment
            ).build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
            NavigationUI.setupWithNavController(bottomNav, navController);
        } catch (Exception e) {
            Log.e(TAG, "Błąd podczas konfiguracji nawigacji", e);
            e.printStackTrace();
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