package pl.pjwstk.bmiapp.data.models

import pl.pjwstk.bmiapp.data.repositories.RecipeRepository

data class Recipe(
    val title: String = "",
    val calories: Int = 0,
    val ingredients: List<String> = emptyList(),
    val instructions: String = "",
    val dietType: Int = DIET_STANDARD,
    val isLowCalorie: Boolean = false
) {
    companion object {
        const val DIET_STANDARD = 0
        const val DIET_VEGETARIAN = 1
        const val DIET_LOW_CARB = 2

        fun getSampleRecipes(): Array<Recipe> {
            val recipes = RecipeRepository.getInstance().getRecipesForDiet(DIET_STANDARD)
            return if (recipes.size >= 2) {
                arrayOf(recipes[0], recipes[1])
            } else {
                emptyArray()
            }
        }
    }
}