package pl.pjwstk.bmiapp.data.models

data class ShoppingItem(
    val id: Int,
    val name: String,
    val quantity: String,
    val recipeId: Int,
    var isPurchased: Boolean = false
)
