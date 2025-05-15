package pl.pjwstk.bmiapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment
import pl.pjwstk.bmiapp.utils.NavigationHelper

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView
    private var isNavigatingFromBottomNav = false

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_DarkActionBar)
        title = "FitApp"
        setContentView(R.layout.activity_main)

        try {
            setupNavigation()
        } catch (e: Exception) {
            Log.e(TAG, "Błąd podczas konfiguracji nawigacji", e)
        }
    }

    private fun setupNavigation() {
        bottomNav = findViewById(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        navHostFragment?.let {
            navController = it.navController

            val appBarConfig = AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.bmiCalculatorFragment,
                R.id.calorieCalculatorFragment
            ).build()

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)
            bottomNav.setOnItemSelectedListener(this)
            adjustFragmentContainerMargins()
            setupDestinationChangedListener(it)
        }
    }

    private fun adjustFragmentContainerMargins() {
        findViewById<ViewGroup>(R.id.nav_host_fragment).post {
            val layoutParams = findViewById<ViewGroup>(R.id.nav_host_fragment).layoutParams
                    as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = 0
            findViewById<ViewGroup>(R.id.nav_host_fragment).layoutParams = layoutParams
        }
    }

    private fun setupDestinationChangedListener(navHostFragment: NavHostFragment) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val currentFragment = navHostFragment.childFragmentManager.fragments[0]
            if (currentFragment is BaseFragment) {
                currentFragment.refreshLayout()
            }

            if (!isNavigatingFromBottomNav) {
                val destId = destination.id
                bottomNav.setOnItemSelectedListener(null)

                when (destId) {
                    R.id.homeFragment -> bottomNav.selectedItemId = R.id.homeFragment
                    R.id.bmiCalculatorFragment -> bottomNav.selectedItemId =
                        R.id.bmiCalculatorFragment

                    R.id.calorieCalculatorFragment -> bottomNav.selectedItemId =
                        R.id.calorieCalculatorFragment

                    R.id.shoppingListFragment -> bottomNav.selectedItemId =
                        R.id.shoppingListFragment
                }

                bottomNav.setOnItemSelectedListener(this)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        try {
            isNavigatingFromBottomNav = true
            return NavigationHelper.handleNavigation(item.itemId, navController)
        } finally {
            isNavigatingFromBottomNav = false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}