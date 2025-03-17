package pl.pjwstk.bmiapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Komponenty UI
    private EditText weightEditText, heightEditText;
    private TextView resultTextView, categoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja komponentów
        initViews();

        // Ustawienie nasłuchiwacza zdarzeń
        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> calculateBMI());
    }

    /**
     * Inicjalizacja widoków
     */
    private void initViews() {
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        resultTextView = findViewById(R.id.resultTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
    }

    /**
     * Obliczanie BMI
     */
    private void calculateBMI() {
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        // Walidacja danych wejściowych
        if (!validateInput(weightStr, heightStr)) {
            return;
        }

        try {
            // Konwersja danych
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr) / 100; // Konwersja cm na metry

            // Obliczanie BMI
            float bmi = weight / (height * height);

            // Wyświetlanie wyniku
            displayResult(bmi);

        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Walidacja danych wejściowych
     */
    private boolean validateInput(String weightStr, String heightStr) {
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, R.string.enter_values, Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr);

            if (weight <= 0 || height <= 0) {
                Toast.makeText(this, R.string.enter_valid_values, Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Wyświetlanie wyników BMI
     */
    private void displayResult(float bmi) {
        // Wyświetlanie wyniku BMI
        resultTextView.setText(String.format(getString(R.string.your_bmi), bmi));

        // Określanie i wyświetlanie kategorii BMI
        categoryTextView.setText(getBmiCategory(bmi));
    }

    /**
     * Określenie kategorii BMI
     */
    private String getBmiCategory(float bmi) {
        if (bmi < 16) return "Wygłodzenie";
        if (bmi < 17) return "Wychudzenie";
        if (bmi < 18.5) return "Niedowaga";
        if (bmi < 25) return "Waga prawidłowa";
        if (bmi < 30) return "Nadwaga";
        if (bmi < 35) return "Otyłość I stopnia";
        if (bmi < 40) return "Otyłość II stopnia";
        return "Otyłość III stopnia";
    }
}