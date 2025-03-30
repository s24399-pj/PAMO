package pl.pjwstk.bmiapp.data.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.pjwstk.bmiapp.data.models.Recipe;


public class RecipeRepository {

    private static RecipeRepository instance;

    private final List<Recipe> allRecipes;

    private RecipeRepository() {
        allRecipes = initializeRecipes();
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private List<Recipe> initializeRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(Recipe.builder()
                .title("Sałatka z grillowanym kurczakiem")
                .calories(450)
                .ingredients(List.of(
                        "150g piersi z kurczaka",
                        "Mieszanka sałat (rukola, szpinak, roszponka)",
                        "1 pomidor",
                        "1/4 ogórka",
                        "1/2 papryki",
                        "1 łyżka oliwy z oliwek",
                        "Sok z cytryny",
                        "Przyprawy (sól, pieprz, oregano)"
                ))
                .instructions("1. Przypraw pierś z kurczaka i grilluj przez około 6-8 minut z każdej strony.\n" +
                        "2. Pokrój warzywa i umieść je w misce.\n" +
                        "3. Pokrój ugotowanego kurczaka w kostkę i dodaj do warzyw.\n" +
                        "4. Dodaj oliwę, sok z cytryny i przyprawy, wymieszaj i podawaj.")
                .dietType(Recipe.DIET_STANDARD)
                .isLowCalorie(true)
                .build());

        recipes.add(Recipe.builder()
                .title("Zupa krem z pomidorów")
                .calories(320)
                .ingredients(List.of(
                        "500g pomidorów (świeżych lub z puszki)",
                        "1 cebula",
                        "2 ząbki czosnku",
                        "1 marchewka",
                        "500ml bulionu warzywnego",
                        "1 łyżka oliwy",
                        "Bazylia, sól, pieprz",
                        "Opcjonalnie: 2 łyżki śmietany 18%"
                ))
                .instructions("1. Pokrój cebulę, czosnek i marchewkę.\n" +
                        "2. Na oliwie zeszklij cebulę i czosnek.\n" +
                        "3. Dodaj marchewkę i pomidory, duś przez 5 minut.\n" +
                        "4. Dodaj bulion i gotuj 15 minut.\n" +
                        "5. Zblenduj zupę na krem.\n" +
                        "6. Dopraw solą, pieprzem i bazylią.\n" +
                        "7. Opcjonalnie dodaj śmietanę przed podaniem.")
                .dietType(Recipe.DIET_STANDARD)
                .isLowCalorie(true)
                .build());

        recipes.add(Recipe.builder()
                .title("Makaron pełnoziarnisty z sosem bolońskim")
                .calories(850)
                .ingredients(List.of(
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
                ))
                .instructions("1. Ugotuj makaron zgodnie z instrukcjami na opakowaniu.\n" +
                        "2. Na patelni rozgrzej oliwę i zeszklij posiekaną cebulę i czosnek.\n" +
                        "3. Dodaj mieloną wołowinę i smaż, aż będzie brązowa.\n" +
                        "4. Dodaj pokrojoną marchewkę i seler, smaż przez 5 minut.\n" +
                        "5. Dodaj pomidory i przyprawy, gotuj na małym ogniu przez 20 minut.\n" +
                        "6. Podawaj sos na makaronie, posypany parmezanem.")
                .dietType(Recipe.DIET_STANDARD)
                .isLowCalorie(false)
                .build());

        recipes.add(Recipe.builder()
                .title("Kurczak z ryżem i warzywami")
                .calories(780)
                .ingredients(List.of(
                        "200g piersi z kurczaka",
                        "100g ryżu basmati",
                        "1 papryka",
                        "1 cukinia",
                        "1 marchewka",
                        "1 cebula",
                        "2 ząbki czosnku",
                        "2 łyżki oleju roślinnego",
                        "Sos sojowy",
                        "Przyprawy (sól, pieprz, kurkuma)"
                ))
                .instructions("1. Ugotuj ryż zgodnie z instrukcją na opakowaniu.\n" +
                        "2. Pokrój kurczaka w kostkę i przypraw.\n" +
                        "3. Na patelni rozgrzej olej i usmaż kurczaka.\n" +
                        "4. Dodaj pokrojone warzywa i smaż przez 5-7 minut.\n" +
                        "5. Dodaj sos sojowy i przyprawy.\n" +
                        "6. Podawaj z ryżem.")
                .dietType(Recipe.DIET_STANDARD)
                .isLowCalorie(false)
                .build());

        recipes.add(Recipe.builder()
                .title("Sałatka grecka")
                .calories(380)
                .ingredients(List.of(
                        "1 ogórek",
                        "2 pomidory",
                        "1/2 czerwonej cebuli",
                        "100g sera feta",
                        "10 czarnych oliwek",
                        "1 łyżka oliwy z oliwek",
                        "1 łyżka soku z cytryny",
                        "Oregano, sól, pieprz"
                ))
                .instructions("1. Pokrój ogórka, pomidory i czerwoną cebulę w kostkę.\n" +
                        "2. Pokrój ser feta w kostkę.\n" +
                        "3. Wymieszaj wszystkie składniki w misce.\n" +
                        "4. Skrop oliwą i sokiem z cytryny.\n" +
                        "5. Dopraw oregano, solą i pieprzem.")
                .dietType(Recipe.DIET_VEGETARIAN)
                .isLowCalorie(true)
                .build());

        recipes.add(Recipe.builder()
                .title("Zupa krem z dyni")
                .calories(260)
                .ingredients(List.of(
                        "500g dyni",
                        "1 ziemniak",
                        "1 marchewka",
                        "1 cebula",
                        "2 ząbki czosnku",
                        "500ml bulionu warzywnego",
                        "100ml mleka kokosowego",
                        "Przyprawy (sól, pieprz, kurkuma, imbir)"
                ))
                .instructions("1. Pokrój warzywa w kostkę.\n" +
                        "2. W garnku zeszklij cebulę i czosnek.\n" +
                        "3. Dodaj pozostałe warzywa i duś przez 5 minut.\n" +
                        "4. Wlej bulion i gotuj do miękkości warzyw (ok. 20 minut).\n" +
                        "5. Zblenduj zupę, dodaj mleko kokosowe.\n" +
                        "6. Dopraw przyprawami.")
                .dietType(Recipe.DIET_VEGETARIAN)
                .isLowCalorie(true)
                .build());

        recipes.add(Recipe.builder()
                .title("Risotto z grzybami")
                .calories(720)
                .ingredients(List.of(
                        "200g ryżu arborio",
                        "300g mieszanych grzybów",
                        "1 cebula",
                        "2 ząbki czosnku",
                        "100ml białego wina",
                        "700ml bulionu warzywnego",
                        "50g parmezanu",
                        "2 łyżki masła",
                        "2 łyżki oliwy z oliwek",
                        "Natka pietruszki, sól, pieprz"
                ))
                .instructions("1. Na patelni rozgrzej oliwę i zeszklij posiekaną cebulę i czosnek.\n" +
                        "2. Dodaj ryż i smaż, aż stanie się przezroczysty.\n" +
                        "3. Wlej wino i gotuj, aż odparuje.\n" +
                        "4. Stopniowo dodawaj bulion, mieszając, aż ryż wchłonie płyn.\n" +
                        "5. Na osobnej patelni podsmaż grzyby i dodaj do ryżu.\n" +
                        "6. Dodaj masło i parmezan, wymieszaj.\n" +
                        "7. Posyp natką pietruszki i podawaj.")
                .dietType(Recipe.DIET_VEGETARIAN)
                .isLowCalorie(false)
                .build());

        recipes.add(Recipe.builder()
                .title("Makaron z sosem serowym")
                .calories(810)
                .ingredients(List.of(
                        "150g makaronu penne",
                        "200g sera (cheddar, gouda, parmezan)",
                        "200ml śmietanki 30%",
                        "1 cebula",
                        "2 ząbki czosnku",
                        "2 łyżki masła",
                        "Sól, pieprz, gałka muszkatołowa"
                ))
                .instructions("1. Ugotuj makaron al dente.\n" +
                        "2. Na patelni rozpuść masło, zeszklij cebulę i czosnek.\n" +
                        "3. Dodaj śmietankę i podgrzej.\n" +
                        "4. Dodaj starty ser i mieszaj do rozpuszczenia.\n" +
                        "5. Dopraw solą, pieprzem i gałką muszkatołową.\n" +
                        "6. Wymieszaj z makaronem i podawaj.")
                .dietType(Recipe.DIET_VEGETARIAN)
                .isLowCalorie(false)
                .build());

        recipes.add(Recipe.builder()
                .title("Jajecznica z warzywami")
                .calories(350)
                .ingredients(List.of(
                        "3 jajka",
                        "1/2 papryki",
                        "1/2 cebuli",
                        "Garść szpinaku",
                        "1 łyżka masła",
                        "Sól, pieprz"
                ))
                .instructions("1. Pokrój paprykę i cebulę w kostkę.\n" +
                        "2. Na patelni rozgrzej masło i zeszklij cebulę.\n" +
                        "3. Dodaj paprykę i smaż przez 2-3 minuty.\n" +
                        "4. Wbij jajka i delikatnie mieszaj.\n" +
                        "5. Pod koniec smażenia dodaj szpinak.\n" +
                        "6. Dopraw solą i pieprzem.")
                .dietType(Recipe.DIET_LOW_CARB)
                .isLowCalorie(true)
                .build());

        recipes.add(Recipe.builder()
                .title("Sałatka z tuńczykiem")
                .calories(380)
                .ingredients(List.of(
                        "1 puszka tuńczyka w wodzie",
                        "1 ogórek",
                        "1 papryka",
                        "50g mix sałat",
                        "1 łyżka oliwy z oliwek",
                        "Sok z cytryny",
                        "Sól, pieprz"
                ))
                .instructions("1. Odcedź tuńczyka z wody.\n" +
                        "2. Pokrój ogórka i paprykę w kostkę.\n" +
                        "3. Umyj i osusz mix sałat.\n" +
                        "4. Wymieszaj wszystkie składniki w misce.\n" +
                        "5. Skrop oliwą i sokiem z cytryny.\n" +
                        "6. Dopraw solą i pieprzem.")
                .dietType(Recipe.DIET_LOW_CARB)
                .isLowCalorie(true)
                .build());

        recipes.add(Recipe.builder()
                .title("Stek z awokado")
                .calories(780)
                .ingredients(List.of(
                        "250g steku wołowego (rostbef lub antrykot)",
                        "1 dojrzałe awokado",
                        "2 łyżki oliwy z oliwek",
                        "1 ząbek czosnku",
                        "Sól, pieprz",
                        "Opcjonalnie: masło ziołowe"
                ))
                .instructions("1. Wyjmij stek z lodówki na 30 minut przed smażeniem.\n" +
                        "2. Natrzyj go oliwą, solą i pieprzem.\n" +
                        "3. Rozgrzej patelnię do bardzo wysokiej temperatury.\n" +
                        "4. Smaż stek 3-4 minuty z każdej strony (medium rare).\n" +
                        "5. Odłóż na 5 minut, aby odpoczął.\n" +
                        "6. Pokrój awokado w plastry.\n" +
                        "7. Podawaj stek z awokado i opcjonalnie z masłem ziołowym na wierzchu.")
                .dietType(Recipe.DIET_LOW_CARB)
                .isLowCalorie(false)
                .build());

        recipes.add(Recipe.builder()
                .title("Kotlety jajeczne z boczkiem")
                .calories(650)
                .ingredients(List.of(
                        "6 jajek",
                        "100g boczku",
                        "1 cebula",
                        "50g tartego sera",
                        "2 łyżki śmietany",
                        "2 łyżki masła",
                        "Sól, pieprz, papryka"
                ))
                .instructions("1. Ugotuj jajka na twardo i posiekaj.\n" +
                        "2. Podsmaż boczek i cebulę na patelni.\n" +
                        "3. Wymieszaj jajka, boczek, cebulę, ser i śmietanę.\n" +
                        "4. Uformuj kotlety i smaż na maśle po 3-4 minuty z każdej strony.\n" +
                        "5. Dopraw według uznania.")
                .dietType(Recipe.DIET_LOW_CARB)
                .isLowCalorie(false)
                .build());

        return recipes;
    }

    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(allRecipes);
    }

    public List<Recipe> getRecipesForDiet(int dietType) {
        return allRecipes.stream()
                .filter(recipe -> recipe.getDietType() == dietType)
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesForDietAndCalories(int dietType, double calories) {
        boolean needLowCalorie = calories < 2000; // Próg kaloryczny

        return allRecipes.stream()
                .filter(recipe -> recipe.getDietType() == dietType)
                .filter(recipe -> recipe.isLowCalorie() == needLowCalorie)
                .limit(2) // Zwróć maksymalnie 2 przepisy
                .collect(Collectors.toList());
    }
}