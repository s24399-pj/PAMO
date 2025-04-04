package pl.pjwstk.bmiapp.ui.fragments.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pl.pjwstk.bmiapp.R;
import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment;

public class BmiCalculatorFragment extends BaseFragment {

    private EditText weightEditText, heightEditText;
    private TextView resultTextView, categoryTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> calculateBMI());
    }

    @Override
    protected void fixLayout() {
        super.fixLayout();

        if (rootView != null) {
            TextView titleView = rootView.findViewById(R.id.titleTextView);
            if (titleView != null) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleView.getLayoutParams();
                params.topMargin = (int) (32 * getResources().getDisplayMetrics().density);
                titleView.setLayoutParams(params);
            }
        }
    }

    private void initViews(View view) {
        weightEditText = view.findViewById(R.id.weightEditText);
        heightEditText = view.findViewById(R.id.heightEditText);
        resultTextView = view.findViewById(R.id.resultTextView);
        categoryTextView = view.findViewById(R.id.categoryTextView);
    }

    private void calculateBMI() {
        String weightStr = weightEditText.getText().toString();
        String heightStr = heightEditText.getText().toString();

        if (!validateInput(weightStr, heightStr)) {
            return;
        }

        try {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr) / 100;

            float bmi = weight / (height * height);
            displayResult(bmi);

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(String weightStr, String heightStr) {
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(getContext(), R.string.enter_values, Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr);

            if (weight <= 0 || height <= 0) {
                Toast.makeText(getContext(), R.string.enter_valid_values, Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void displayResult(float bmi) {
        resultTextView.setText(String.format(getString(R.string.your_bmi), bmi));
        categoryTextView.setText(getBmiCategory(bmi));
    }

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