package pl.pjwstk.bmiapp.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment

class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.btnBmiCalculator).setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.action_homeFragment_to_bmiCalculatorFragment)
        }

        view.findViewById<View>(R.id.btnCalorieCalculator).setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.action_homeFragment_to_calorieCalculatorFragment)
        }

        // Nowy przycisk do wykresu BMI
        view.findViewById<View>(R.id.btnBmiChart).setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.action_homeFragment_to_bmiChartFragment)
        }
    }

    override fun fixLayout() {
        super.fixLayout()

        rootView?.let { root ->
            val logoView = root.findViewById<View>(R.id.logoImageView)
            logoView?.let {
                val params = it.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = (48 * resources.displayMetrics.density).toInt() // 48dp
                it.layoutParams = params
            }
        }
    }
}