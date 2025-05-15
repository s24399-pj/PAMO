package pl.pjwstk.bmiapp.ui.fragments.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment

class BmiCalculatorFragment : BaseFragment() {

    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var categoryTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmi_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        val calculateButton = view.findViewById<Button>(R.id.calculateButton)
        calculateButton.setOnClickListener { calculateBMI() }
    }

    override fun fixLayout() {
        super.fixLayout()

        rootView?.let { root ->
            val titleView = root.findViewById<TextView>(R.id.titleTextView)
            titleView?.let {
                val params = it.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = (32 * resources.displayMetrics.density).toInt()
                it.layoutParams = params
            }
        }
    }

    private fun initViews(view: View) {
        weightEditText = view.findViewById(R.id.weightEditText)
        heightEditText = view.findViewById(R.id.heightEditText)
        resultTextView = view.findViewById(R.id.resultTextView)
        categoryTextView = view.findViewById(R.id.categoryTextView)
    }

    private fun calculateBMI() {
        val weightStr = weightEditText.text.toString()
        val heightStr = heightEditText.text.toString()

        if (!validateInput(weightStr, heightStr)) {
            return
        }

        try {
            val weight = weightStr.toFloat()
            val height = heightStr.toFloat() / 100

            val bmi = weight / (height * height)
            displayResult(bmi)

        } catch (e: NumberFormatException) {
            Toast.makeText(context, R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInput(weightStr: String, heightStr: String): Boolean {
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(context, R.string.enter_values, Toast.LENGTH_SHORT).show()
            return false
        }

        try {
            val weight = weightStr.toFloat()
            val height = heightStr.toFloat()

            if (weight <= 0 || height <= 0) {
                Toast.makeText(context, R.string.enter_valid_values, Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        } catch (e: NumberFormatException) {
            Toast.makeText(context, R.string.enter_valid_numbers, Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun displayResult(bmi: Float) {
        resultTextView.text = String.format(getString(R.string.your_bmi), bmi)
        categoryTextView.text = getBmiCategory(bmi)
    }

    private fun getBmiCategory(bmi: Float): String {
        return when {
            bmi < 16 -> "Wygłodzenie"
            bmi < 17 -> "Wychudzenie"
            bmi < 18.5 -> "Niedowaga"
            bmi < 25 -> "Waga prawidłowa"
            bmi < 30 -> "Nadwaga"
            bmi < 35 -> "Otyłość I stopnia"
            bmi < 40 -> "Otyłość II stopnia"
            else -> "Otyłość III stopnia"
        }
    }
}