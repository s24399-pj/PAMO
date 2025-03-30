package pl.pjwstk.bmiapp.utils;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import lombok.experimental.UtilityClass;
import pl.pjwstk.bmiapp.R;

@UtilityClass
public final class NavigationHelper {

    public boolean handleNavigation(int itemId, NavController navController) {
        NavOptions navOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(navController.getGraph().getStartDestination(), false)
                .build();

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
    }
}