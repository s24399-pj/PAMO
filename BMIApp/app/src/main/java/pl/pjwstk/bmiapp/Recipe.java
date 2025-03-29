package pl.pjwstk.bmiapp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model danych reprezentujący przepis kulinarny
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    // Typy diet
    public static final int DIET_STANDARD = 0;
    public static final int DIET_VEGETARIAN = 1;
    public static final int DIET_LOW_CARB = 2;

    private String title;
    private int calories;
    private List<String> ingredients;
    private String instructions;
    private String imageUrl; // Opcjonalnie - URL do obrazka
    private int dietType; // Typ diety
    private boolean isLowCalorie; // Czy przepis jest niskokaloryczny

    // Metoda kompatybilności dla starego kodu
    public static Recipe[] getSampleRecipes() {
        List<Recipe> recipes = RecipeRepository.getInstance().getRecipesForDiet(DIET_STANDARD);
        if (recipes.size() >= 2) {
            return new Recipe[]{recipes.get(0), recipes.get(1)};
        } else {
            return new Recipe[0];
        }
    }
}