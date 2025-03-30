package pl.pjwstk.bmiapp.data.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pjwstk.bmiapp.data.repositories.RecipeRepository;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    public static final int DIET_STANDARD = 0;
    public static final int DIET_VEGETARIAN = 1;
    public static final int DIET_LOW_CARB = 2;

    private String title;
    private int calories;
    private List<String> ingredients;
    private String instructions;
    private int dietType;
    private boolean isLowCalorie;

    public static Recipe[] getSampleRecipes() {
        List<Recipe> recipes = RecipeRepository.getInstance().getRecipesForDiet(DIET_STANDARD);
        if (recipes.size() >= 2) {
            return new Recipe[]{recipes.get(0), recipes.get(1)};
        } else {
            return new Recipe[0];
        }
    }
}