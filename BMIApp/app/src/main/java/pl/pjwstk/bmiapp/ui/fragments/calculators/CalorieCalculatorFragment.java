package pl.pjwstk.bmiapp.ui.fragments.calculators;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment;
import pl.pjwstk.bmiapp.data.calculators.CalorieCalculator;
import pl.pjwstk.bmiapp.R;
import pl.pjwstk.bmiapp.data.models.Recipe;
import pl.pjwstk.bmiapp.data.repositories.RecipeRepository;

public class CalorieCalculatorFragment extends BaseFragment {

    private EditText weightEditText, heightEditText, ageEditText;
    private RadioGroup genderRadioGroup;
    private Spinner activityLevelSpinner;
    private TextView resultTextView;
    private LinearLayout recipesContainer;
    private ScrollView scrollView;
    private double calculatedCalories = 0;

    private RecipeRepository recipeRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calorie_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        recipeRepository = RecipeRepository.getInstance();

        setupActivityLevelSpinner();

        Button calculateButton = view.findViewById(R.id.calculateCaloriesButton);
        calculateButton.setOnClickListener(v -> calculateCalories());
    }

    @Override
    protected void fixLayout() {
        super.fixLayout();

        if (rootView != null) {
            TextView titleView = rootView.findViewById(R.id.titleTextView);
            if (titleView != null) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleView.getLayoutParams();
                params.topMargin = (int) (16 * getResources().getDisplayMetrics().density);
                titleView.setLayoutParams(params);
            }
        }
    }

    private void initViews(View view) {
        weightEditText = view.findViewById(R.id.weightEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        ageEditText = view.findViewById(R.id.ageEditText);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        activityLevelSpinner = view.findViewById(R.id.activityLevelSpinner);
        resultTextView = view.findViewById(R.id.calorieResultTextView);
        recipesContainer = view.findViewById(R.id.recipesContainer);
        scrollView = view.findViewById(R.id.calorieScrollView);
    }

    private void setupActivityLevelSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.activity_levels,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(adapter);
    }

    private void calculateCalories() {
        if (!validateInputs()) {
            return;
        }

        try {
            double weight = Double.parseDouble(weightEditText.getText().toString());
            double height = Double.parseDouble(heightEditText.getText().toString());
            int age = Integer.parseInt(ageEditText.getText().toString());

            boolean isMale = ((RadioButton) requireView().findViewById(
                    genderRadioGroup.getCheckedRadioButtonId())).getText()
                    .equals(getString(R.string.male));

            int activityPosition = activityLevelSpinner.getSelectedItemPosition();
            double activityMultiplier = CalorieCalculator.getActivityMultiplier(activityPosition);

            calculatedCalories = CalorieCalculator.calculateCalories(
                    weight, height, age, isMale, activityMultiplier);

            resultTextView.setText(String.format(getString(R.string.calorie_result), calculatedCalories));
            resultTextView.setVisibility(View.VISIBLE);

            showDietSelectionDialog();

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDietSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_diet_selection, null);
        builder.setView(dialogView);

        Dialog dialog = builder.create();

        RadioGroup dietRadioGroup = dialogView.findViewById(R.id.dietRadioGroup);
        Button showRecipesButton = dialogView.findViewById(R.id.showRecipesButton);

        showRecipesButton.setOnClickListener(v -> {
            int selectedId = dietRadioGroup.getCheckedRadioButtonId();
            int dietType;

            if (selectedId == R.id.vegetarianDietRadioButton) {
                dietType = Recipe.DIET_VEGETARIAN;
            } else if (selectedId == R.id.lowCarbDietRadioButton) {
                dietType = Recipe.DIET_LOW_CARB;
            } else {
                dietType = Recipe.DIET_STANDARD;
            }

            dialog.dismiss();
            showRecipesForDiet(dietType);
        });

        dialog.show();
    }

    private void showRecipesForDiet(int dietType) {
        recipesContainer.removeAllViews();

        List<Recipe> recipes = recipeRepository.getRecipesForDietAndCalories(dietType, calculatedCalories);

        TextView headerTextView = new TextView(requireContext());
        headerTextView.setText("Rekomendowane przepisy");
        headerTextView.setTextSize(18);
        headerTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        headerTextView.setPadding(0, 32, 0, 16);
        recipesContainer.addView(headerTextView);

        if (recipes.isEmpty()) {
            TextView noRecipesTextView = new TextView(requireContext());
            noRecipesTextView.setText("Brak przepisów pasujących do wybranej diety");
            noRecipesTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            recipesContainer.addView(noRecipesTextView);
        } else {
            for (Recipe recipe : recipes) {
                View recipeView = createRecipeView(recipe);
                recipesContainer.addView(recipeView);
            }
        }

        scrollView.post(() -> scrollView.smoothScrollTo(0, resultTextView.getBottom()));
    }

    /**
     * Tworzenie widoku dla pojedynczego przepisu
     */
    private View createRecipeView(Recipe recipe) {
        View recipeView = getLayoutInflater().inflate(R.layout.item_recipe, recipesContainer, false);

        TextView titleTextView = recipeView.findViewById(R.id.recipeTitleTextView);
        titleTextView.setText(recipe.getTitle());

        TextView caloriesTextView = recipeView.findViewById(R.id.recipeCaloriesTextView);
        caloriesTextView.setText(String.format(getString(R.string.recipe_calories), recipe.getCalories()));

        TextView ingredientsTextView = recipeView.findViewById(R.id.recipeIngredientsTextView);
        StringBuilder ingredientsBuilder = new StringBuilder();
        for (String ingredient : recipe.getIngredients()) {
            ingredientsBuilder.append("• ").append(ingredient).append("\n");
        }
        ingredientsTextView.setText(ingredientsBuilder.toString());

        TextView instructionsTextView = recipeView.findViewById(R.id.recipeInstructionsTextView);
        instructionsTextView.setText(recipe.getInstructions());

        return recipeView;
    }

    private boolean validateInputs() {
        if (weightEditText.getText().toString().isEmpty() ||
                heightEditText.getText().toString().isEmpty() ||
                ageEditText.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.enter_values, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Wybierz płeć", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            double weight = Double.parseDouble(weightEditText.getText().toString());
            double height = Double.parseDouble(heightEditText.getText().toString());
            int age = Integer.parseInt(ageEditText.getText().toString());

            if (weight <= 0 || height <= 0 || age <= 0 || age > 120) {
                Toast.makeText(getContext(), R.string.enter_valid_values, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}