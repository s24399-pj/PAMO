package pl.pjwstk.bmiapp.data.repositories

import pl.pjwstk.bmiapp.data.models.Recipe
import pl.pjwstk.bmiapp.data.models.ShoppingItem

class ShoppingListRepository {
    private val shoppingItems = mutableListOf<ShoppingItem>()
    private var nextItemId = 1

    fun getShoppingItems(): List<ShoppingItem> = shoppingItems

    fun generateShoppingListFromRecipe(recipe: Recipe): List<ShoppingItem> {
        shoppingItems.clear()

        recipe.ingredients.forEachIndexed { index, ingredient ->
            val parts = parseIngredient(ingredient)
            val name = parts.first
            val quantity = parts.second

            val item = ShoppingItem(
                id = nextItemId++,
                name = name,
                quantity = quantity,
                recipeId = Recipe.getSampleRecipes().indexOf(recipe),
                isPurchased = false
            )
            shoppingItems.add(item)
        }

        return shoppingItems
    }

    private fun parseIngredient(ingredient: String): Pair<String, String> {
        val regex = Regex("^(\\d+[\\d/]*)\\s*([a-zżźćńółęąśA-ZŻŹĆŃÓŁĘĄŚ]+)\\s*(.*)$")
        val match = regex.find(ingredient)

        if (match != null) {
            val amount = match.groupValues[1]
            val unit = match.groupValues[2]
            val itemName = match.groupValues[3]

            return Pair(
                itemName.trim(),
                "$amount $unit".trim()
            )
        } else {
            return Pair(ingredient, "1")
        }
    }

    fun updateItemPurchasedStatus(itemId: Int, isPurchased: Boolean) {
        val item = shoppingItems.find { it.id == itemId }
        item?.isPurchased = isPurchased
    }

    fun clearShoppingList() {
        shoppingItems.clear()
    }
}