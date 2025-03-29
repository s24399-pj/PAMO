package pl.pjwstk.bmiapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalorieCalculatorFragment extends Fragment {

    private EditText weightEditText, heightEditText, ageEditText;
    private RadioGroup genderRadioGroup;
    private Spinner activityLevelSpinner;
    private TextView resultTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calorie_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicjalizacja komponentów
        initViews(view);

        // Konfiguracja spinnera dla poziomów aktywności
        setupActivityLevelSpinner();

        // Ustawienie nasłuchiwacza zdarzeń dla przycisku
        Button calculateButton = view.findViewById(R.id.calculateCaloriesButton);
        calculateButton.setOnClickListener(v -> calculateCalories());
    }

    /**
     * Inicjalizacja widoków
     */
    private void initViews(View view) {
        weightEditText = view.findViewById(R.id.weightEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        ageEditText = view.findViewById(R.id.ageEditText);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        activityLevelSpinner = view.findViewById(R.id.activityLevelSpinner);
        resultTextView = view.findViewById(R.id.calorieResultTextView);
    }

    /**
     * Konfiguracja spinnera z poziomami aktywności
     */
    private void setupActivityLevelSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.activity_levels,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(adapter);
    }

    /**
     * Obliczanie dziennego zapotrzebowania kalorycznego
     */
    private void calculateCalories() {
        // Walidacja danych wejściowych
        if (!validateInputs()) {
            return;
        }

        try {
            // Pobranie danych z formularza
            double weight = Double.parseDouble(weightEditText.getText().toString());
            double height = Double.parseDouble(heightEditText.getText().toString());
            int age = Integer.parseInt(ageEditText.getText().toString());

            boolean isMale = ((RadioButton) requireView().findViewById(
                    genderRadioGroup.getCheckedRadioButtonId())).getText()
                    .equals(getString(R.string.male));

            int activityPosition = activityLevelSpinner.getSelectedItemPosition();
            double activityMultiplier = CalorieCalculator.getActivityMultiplier(activityPosition);

            // Obliczenie zapotrzebowania kalorycznego
            double calories = CalorieCalculator.calculateCalories(
                    weight, height, age, isMale, activityMultiplier);

            // Wyświetlenie wyniku
            resultTextView.setText(String.format(getString(R.string.calorie_result), calories));
            resultTextView.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Walidacja danych wejściowych
     */
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