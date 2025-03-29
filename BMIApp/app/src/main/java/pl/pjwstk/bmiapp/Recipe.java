package pl.pjwstk.bmiapp;

import java.util.List;

/**
 * Model danych reprezentujący przepis kulinarny
 */
public class Recipe {
    private String title;
    private int calories;
    private List<String> ingredients;
    private String instructions;
    private String imageUrl; // Opcjonalnie - URL do obrazka

    public Recipe(String title, int calories, List<String> ingredients, String instructions) {
        this.title = title;
        this.calories = calories;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(String title, int calories, List<String> ingredients, String instructions, String imageUrl) {
        this(title, calories, ingredients, instructions);
        this.imageUrl = imageUrl;
    }

    // Gettery
    public String getTitle() {
        return title;
    }

    public int getCalories() {
        return calories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Metoda tworząca przykładowe przepisy
    public static Recipe[] getSampleRecipes() {
        // Niskokaloryczny przepis
        Recipe lowCalRecipe = new Recipe(
                "Sałatka z grillowanym kurczakiem",
                450,
                List.of(
                        "150g piersi z kurczaka",
                        "Mieszanka sałat (rukola, szpinak, roszponka)",
                        "1 pomidor",
                        "1/4 ogórka",
                        "1/2 papryki",
                        "1 łyżka oliwy z oliwek",
                        "Sok z cytryny",
                        "Przyprawy (sól, pieprz, oregano)"
                ),
                "1. Przypraw pierś z kurczaka i grilluj przez około 6-8 minut z każdej strony.\n" +
                        "2. Pokrój warzywa i umieść je w misce.\n" +
                        "3. Pokrój ugotowanego kurczaka w kostkę i dodaj do warzyw.\n" +
                        "4. Dodaj oliwę, sok z cytryny i przyprawy, wymieszaj i podawaj."
        );

        // Wysokokaloryczny przepis
        Recipe highCalRecipe = new Recipe(
                "Makaron pełnoziarnisty z sosem bolońskim",
                850,
                List.of(
                        "100g makaronu pełnoziarnistego",
                        "200g mielonej wołowiny",
                        "1 cebula",
                        "2 ząbki czosnku",
                        "1 marchewka",
                        "1 łodyga selera naciowego",
                        "400g pomidorów z puszki",
                        "2 łyżki oliwy z oliwek",
                        "Przyprawy (sól, pieprz, bazylia, oregano)",
                        "Parmezan do posypania"
                ),
                "1. Ugotuj makaron zgodnie z instrukcjami na opakowaniu.\n" +
                        "2. Na patelni rozgrzej oliwę i zeszklij posiekaną cebulę i czosnek.\n" +
                        "3. Dodaj mieloną wołowinę i smaż, aż będzie brązowa.\n" +
                        "4. Dodaj pokrojoną marchewkę i seler, smaż przez 5 minut.\n" +
                        "5. Dodaj pomidory i przyprawy, gotuj na małym ogniu przez 20 minut.\n" +
                        "6. Podawaj sos na makaronie, posypany parmezanem."
        );

        return new Recipe[] { lowCalRecipe, highCalRecipe };
    }
}