package pl.pjwstk.bmiapp.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.data.models.Recipe
import pl.pjwstk.bmiapp.utils.NavigationHelper

class RecipesFragment : Fragment() {
    private val TAG = "RecipesFragment"
    private lateinit var recipesContainer: LinearLayout
    private lateinit var recipesList: Array<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipesContainer = view.findViewById(R.id.recipesContainer)

        // Zapisujemy referencję do oryginalnej tablicy przepisów
        recipesList = Recipe.getSampleRecipes()

        loadRecipes()
    }

    private fun loadRecipes() {
        for (recipe in recipesList) {
            val recipeView = createRecipeView(recipe)
            recipesContainer.addView(recipeView)
        }
    }

    private fun createRecipeView(recipe: Recipe): View {
        val recipeView = layoutInflater.inflate(R.layout.item_recipe, recipesContainer, false)

        val titleTextView: TextView = recipeView.findViewById(R.id.recipeTitleTextView)
        titleTextView.text = recipe.title

        val caloriesTextView: TextView = recipeView.findViewById(R.id.recipeCaloriesTextView)
        caloriesTextView.text = getString(R.string.recipe_calories, recipe.calories)

        val ingredientsTextView: TextView = recipeView.findViewById(R.id.recipeIngredientsTextView)
        val ingredientsBuilder = StringBuilder()
        for (ingredient in recipe.ingredients) {
            ingredientsBuilder.append("• ").append(ingredient).append("\n")
        }
        ingredientsTextView.text = ingredientsBuilder.toString()

        val instructionsTextView: TextView =
            recipeView.findViewById(R.id.recipeInstructionsTextView)
        instructionsTextView.text = recipe.instructions

        // Dodanie przycisku do generowania listy zakupów
        val generateShoppingListButton: Button =
            recipeView.findViewById(R.id.generateShoppingListButton)
        generateShoppingListButton.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

            // Używamy tablicy recipesList zamiast wywoływać getSampleRecipes ponownie
            val recipeIndex = recipesList.indexOf(recipe)
            Log.d(TAG, "Generuję listę zakupów dla przepisu: ${recipe.title}, indeks: $recipeIndex")

            // Używamy pomocnika do nawigacji, jak było wcześniej
            NavigationHelper.navigateToShoppingList(navController, recipeIndex)
        }

        return recipeView
    }
}