package pl.pjwstk.bmiapp.ui.fragments.calculators

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.data.calculators.CalorieCalculator
import pl.pjwstk.bmiapp.data.models.Recipe
import pl.pjwstk.bmiapp.data.repositories.RecipeRepository
import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment

class CalorieCalculatorFragment : BaseFragment() {

    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var activityLevelSpinner: Spinner
    private lateinit var resultTextView: TextView
    private lateinit var recipesContainer: LinearLayout
    private lateinit var scrollView: ScrollView
    private var calculatedCalories: Double = 0.0

    private lateinit var recipeRepository: RecipeRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calorie_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        recipeRepository = RecipeRepository.getInstance()

        setupActivityLevelSpinner()

        val calculateButton = view.findViewById<Button>(R.id.calculateCaloriesButton)
        calculateButton.setOnClickListener { calculateCalories() }
    }

    override fun fixLayout() {
        super.fixLayout()

        rootView?.let { root ->
            val titleView = root.findViewById<TextView>(R.id.titleTextView)
            titleView?.let {
                val params = it.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = (16 * resources.displayMetrics.density).toInt()
                it.layoutParams = params
            }
        }
    }

    private fun initViews(view: View) {
        weightEditText = view.findViewById(R.id.weightEditText)
        heightEditText = view.findViewById(R.id.heightEditText)
        ageEditText = view.findViewById(R.id.ageEditText)
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup)
        activityLevelSpinner = view.findViewById(R.id.activityLevelSpinner)
        resultTextView = view.findViewById(R.id.calorieResultTextView)
        recipesContainer = view.findViewById(R.id.recipesContainer)
        scrollView = view.findViewById(R.id.calorieScrollView)
    }

    private fun setupActivityLevelSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.activity_levels,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityLevelSpinner.adapter = adapter
    }

    private fun calculateCalories() {
        if (!validateInputs()) {
            return
        }

        try {
            val weight = weightEditText.text.toString().toDouble()
            val height = heightEditText.text.toString().toDouble()
            val age = ageEditText.text.toString().toInt()

            val isMale = (requireView().findViewById<RadioButton>(
                genderRadioGroup.checkedRadioButtonId
            )).text == getString(R.string.male)

            val activityPosition = activityLevelSpinner.selectedItemPosition
            val activityMultiplier = CalorieCalculator.getActivityMultiplier(activityPosition)

            calculatedCalories = CalorieCalculator.calculateCalories(
                weight, height, age, isMale, activityMultiplier
            )

            resultTextView.text =
                String.format(getString(R.string.calorie_result), calculatedCalories)
            resultTextView.visibility = View.VISIBLE

            showDietSelectionDialog()

        } catch (e: NumberFormatException) {
            Toast.makeText(context, R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDietSelectionDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_diet_selection, null)
        builder.setView(dialogView)

        val dialog = builder.create()

        val dietRadioGroup = dialogView.findViewById<RadioGroup>(R.id.dietRadioGroup)
        val showRecipesButton = dialogView.findViewById<Button>(R.id.showRecipesButton)

        showRecipesButton.setOnClickListener {
            val selectedId = dietRadioGroup.checkedRadioButtonId
            val dietType = when (selectedId) {
                R.id.vegetarianDietRadioButton -> Recipe.DIET_VEGETARIAN
                R.id.lowCarbDietRadioButton -> Recipe.DIET_LOW_CARB
                else -> Recipe.DIET_STANDARD
            }

            dialog.dismiss()
            showRecipesForDiet(dietType)
        }

        dialog.show()
    }

    private fun showRecipesForDiet(dietType: Int) {
        recipesContainer.removeAllViews()

        val recipes = recipeRepository.getRecipesForDietAndCalories(dietType, calculatedCalories)

        val headerTextView = TextView(requireContext()).apply {
            text = "Rekomendowane przepisy"
            textSize = 18f
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setPadding(0, 32, 0, 16)
        }
        recipesContainer.addView(headerTextView)

        if (recipes.isEmpty()) {
            val noRecipesTextView = TextView(requireContext()).apply {
                text = "Brak przepisów pasujących do wybranej diety"
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            recipesContainer.addView(noRecipesTextView)
        } else {
            for (recipe in recipes) {
                val recipeView = createRecipeView(recipe)
                recipesContainer.addView(recipeView)
            }
        }

        scrollView.post { scrollView.smoothScrollTo(0, resultTextView.bottom) }
    }

    private fun createRecipeView(recipe: Recipe): View {
        val recipeView = layoutInflater.inflate(R.layout.item_recipe, recipesContainer, false)

        val titleTextView = recipeView.findViewById<TextView>(R.id.recipeTitleTextView)
        titleTextView.text = recipe.title

        val caloriesTextView = recipeView.findViewById<TextView>(R.id.recipeCaloriesTextView)
        caloriesTextView.text = String.format(getString(R.string.recipe_calories), recipe.calories)

        val ingredientsTextView = recipeView.findViewById<TextView>(R.id.recipeIngredientsTextView)
        val ingredientsBuilder = StringBuilder()
        for (ingredient in recipe.ingredients) {
            ingredientsBuilder.append("• ").append(ingredient).append("\n")
        }
        ingredientsTextView.text = ingredientsBuilder.toString()

        val instructionsTextView =
            recipeView.findViewById<TextView>(R.id.recipeInstructionsTextView)
        instructionsTextView.text = recipe.instructions

        // Dodajemy tutaj przycisk do generowania listy zakupów
        val generateShoppingListButton =
            recipeView.findViewById<Button>(R.id.generateShoppingListButton)
        generateShoppingListButton?.setOnClickListener {
            val navController = androidx.navigation.Navigation.findNavController(
                requireActivity(),
                R.id.nav_host_fragment
            )
            val recipeIndex = Recipe.getSampleRecipes().indexOf(recipe)
            pl.pjwstk.bmiapp.utils.NavigationHelper.navigateToShoppingList(
                navController,
                recipeIndex
            )
        }

        return recipeView
    }

    private fun validateInputs(): Boolean {
        if (weightEditText.text.toString().isEmpty() ||
            heightEditText.text.toString().isEmpty() ||
            ageEditText.text.toString().isEmpty()
        ) {
            Toast.makeText(context, R.string.enter_values, Toast.LENGTH_SHORT).show()
            return false
        }

        if (genderRadioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(context, "Wybierz płeć", Toast.LENGTH_SHORT).show()
            return false
        }

        try {
            val weight = weightEditText.text.toString().toDouble()
            val height = heightEditText.text.toString().toDouble()
            val age = ageEditText.text.toString().toInt()

            if (weight <= 0 || height <= 0 || age <= 0 || age > 120) {
                Toast.makeText(context, R.string.enter_valid_values, Toast.LENGTH_SHORT).show()
                return false
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(context, R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}