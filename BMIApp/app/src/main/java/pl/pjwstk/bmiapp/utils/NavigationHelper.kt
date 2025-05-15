package pl.pjwstk.bmiapp.utils

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import pl.pjwstk.bmiapp.R

object NavigationHelper {
    private const val TAG = "NavigationHelper"

    fun handleNavigation(itemId: Int, navController: NavController): Boolean {
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setPopUpTo(navController.graph.startDestinationId, false)
            .build()

        when (itemId) {
            R.id.homeFragment -> {
                navController.navigate(R.id.homeFragment, null, navOptions)
                return true
            }

            R.id.bmiCalculatorFragment -> {
                navController.navigate(R.id.bmiCalculatorFragment, null, navOptions)
                return true
            }

            R.id.calorieCalculatorFragment -> {
                navController.navigate(R.id.calorieCalculatorFragment, null, navOptions)
                return true
            }

            R.id.shoppingListFragment -> {
                navController.navigate(R.id.shoppingListFragment, null, navOptions)
                return true
            }

            else -> return false
        }
    }

    fun navigateToShoppingList(navController: NavController, recipeId: Int) {
        Log.d(TAG, "Nawigacja do listy zakupów z recipeId: $recipeId")

        val bundle = Bundle()
        bundle.putInt("recipeId", recipeId)

        navController.navigate(R.id.shoppingListFragment, bundle)
    }
}