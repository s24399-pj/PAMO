package pl.pjwstk.bmiapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Znajdź przyciski i ustaw na nich listenery
        view.findViewById(R.id.btnBmiCalculator).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.bmiCalculatorFragment));

        view.findViewById(R.id.btnCalorieCalculator).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.calorieCalculatorFragment));
    }
}