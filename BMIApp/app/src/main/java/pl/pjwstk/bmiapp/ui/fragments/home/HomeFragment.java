package pl.pjwstk.bmiapp.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment;
import pl.pjwstk.bmiapp.R;

public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Znajdź przyciski i ustaw na nich listenery z akcjami
        view.findViewById(R.id.btnBmiCalculator).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_bmiCalculatorFragment));

        view.findViewById(R.id.btnCalorieCalculator).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_calorieCalculatorFragment));
    }

    @Override
    protected void fixLayout() {
        // Wywołaj bazową implementację
        super.fixLayout();

        // Dodaj własną logikę naprawy layoutu
        if (rootView != null) {
            View logoView = rootView.findViewById(R.id.logoImageView);
            if (logoView != null) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) logoView.getLayoutParams();
                params.topMargin = (int) (48 * getResources().getDisplayMetrics().density); // 48dp
                logoView.setLayoutParams(params);
            }
        }
    }
}