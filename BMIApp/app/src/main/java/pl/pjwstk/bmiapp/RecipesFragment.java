package pl.pjwstk.bmiapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecipesFragment extends Fragment {

    private LinearLayout recipesContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipesContainer = view.findViewById(R.id.recipesContainer);

        // Załadowanie przykładowych przepisów
        loadRecipes();
    }

    /**
     * Załadowanie przepisów do UI
     */
    private void loadRecipes() {
        Recipe[] recipes = Recipe.getSampleRecipes();

        for (Recipe recipe : recipes) {
            View recipeView = createRecipeView(recipe);
            recipesContainer.addView(recipeView);
        }
    }

    /**
     * Tworzenie widoku dla pojedynczego przepisu
     */
    private View createRecipeView(Recipe recipe) {
        View recipeView = getLayoutInflater().inflate(R.layout.item_recipe, recipesContainer, false);

        // Ustawienie tytułu przepisu
        TextView titleTextView = recipeView.findViewById(R.id.recipeTitleTextView);
        titleTextView.setText(recipe.getTitle());

        // Ustawienie kalorii
        TextView caloriesTextView = recipeView.findViewById(R.id.recipeCaloriesTextView);
        caloriesTextView.setText(String.format(getString(R.string.recipe_calories), recipe.getCalories()));

        // Ustawienie składników
        TextView ingredientsTextView = recipeView.findViewById(R.id.recipeIngredientsTextView);
        StringBuilder ingredientsBuilder = new StringBuilder();
        for (String ingredient : recipe.getIngredients()) {
            ingredientsBuilder.append("• ").append(ingredient).append("\n");
        }
        ingredientsTextView.setText(ingredientsBuilder.toString());

        // Ustawienie instrukcji
        TextView instructionsTextView = recipeView.findViewById(R.id.recipeInstructionsTextView);
        instructionsTextView.setText(recipe.getInstructions());

        return recipeView;
    }
}