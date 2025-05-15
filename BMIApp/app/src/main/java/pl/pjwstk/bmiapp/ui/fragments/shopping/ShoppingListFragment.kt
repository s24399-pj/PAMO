package pl.pjwstk.bmiapp.ui.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.data.models.Recipe
import pl.pjwstk.bmiapp.data.repositories.RecipeRepository
import pl.pjwstk.bmiapp.data.repositories.ShoppingListRepository

class ShoppingListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShoppingListAdapter
    private lateinit var titleTextView: TextView
    private val shoppingListRepository = ShoppingListRepository()
    private val recipeRepository = RecipeRepository.getInstance()
    private val TAG = "ShoppingListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called with arguments: $arguments")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView called")
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")

        recyclerView = view.findViewById(R.id.shoppingRecyclerView)
        titleTextView = view.findViewById(R.id.shoppingListTitle)

        setupRecyclerView()
        processRecipeArgument()
    }

    private fun processRecipeArgument() {
        Log.d(TAG, "Processing recipe argument. Arguments bundle: $arguments")

        if (arguments == null) {
            Log.e(TAG, "Arguments bundle is null!")
        }

        val recipeId = arguments?.getInt("recipeId", -1) ?: -1
        Log.d(TAG, "Extracted recipeId from arguments: $recipeId")

        if (recipeId != -1) {
            tryLoadRecipeById(recipeId)
        } else {
            tryFindRecipeFromNavigation()
        }
    }

    private fun tryLoadRecipeById(recipeId: Int) {
        val recipes = Recipe.getSampleRecipes()
        Log.d(TAG, "Trying to load recipe by ID: $recipeId, available recipes: ${recipes.size}")

        if (recipeId < recipes.size) {
            val recipe = recipes[recipeId]
            Log.d(TAG, "Found recipe: ${recipe.title}")
            generateShoppingListForRecipe(recipe)
        } else {
            Log.e(TAG, "Recipe index out of bounds: $recipeId")
            showEmptyList("Nie znaleziono przepisu")
        }
    }

    private fun tryFindRecipeFromNavigation() {
        Log.d(TAG, "Recipe ID not provided, checking if there are any previously saved items")
        val existingItems = shoppingListRepository.getShoppingItems()

        if (existingItems.isNotEmpty()) {
            Log.d(TAG, "Found ${existingItems.size} existing items")
            adapter.updateItems(existingItems)
            titleTextView.text = "Lista zakupów"
        } else {
            Log.d(TAG, "No existing items found, loading default recipe")
            val recipes = recipeRepository.getAllRecipes()
            if (recipes.isNotEmpty()) {
                val defaultRecipe = recipes[0]
                generateShoppingListForRecipe(defaultRecipe)
            } else {
                showEmptyList("Lista zakupów")
            }
        }
    }

    private fun generateShoppingListForRecipe(recipe: Recipe) {
        Log.d(TAG, "Generating shopping list for recipe: ${recipe.title}")
        val shoppingItems = shoppingListRepository.generateShoppingListFromRecipe(recipe)
        Log.d(TAG, "Generated ${shoppingItems.size} shopping items")

        titleTextView.text = "Lista zakupów - ${recipe.title}"
        adapter.updateItems(shoppingItems)
    }

    private fun showEmptyList(title: String) {
        titleTextView.text = title
        adapter.updateItems(emptyList())
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter(emptyList()) { item, isChecked ->
            shoppingListRepository.updateItemPurchasedStatus(item.id, isChecked)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ShoppingListFragment.adapter
            setHasFixedSize(true)
        }
    }
}