package pl.pjwstk.bmiapp.ui.fragments.chart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import pl.pjwstk.bmiapp.R
import pl.pjwstk.bmiapp.data.repositories.BmiHistoryRepository
import pl.pjwstk.bmiapp.ui.fragments.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Locale

class BmiChartFragment : BaseFragment() {

    private lateinit var bmiChart: LineChart
    private val bmiHistoryRepository = BmiHistoryRepository.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmi_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bmiChart = view.findViewById(R.id.bmiChart)
        setupChart()
        loadBmiData()
    }

    private fun setupChart() {
        bmiChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setDrawGridBackground(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                textSize = 10f
                setDrawGridLines(false)
                granularity = 1f
            }

            axisLeft.apply {
                textSize = 10f
                setDrawGridLines(true)
                axisMinimum = 18f  // Minimalna wartość BMI na wykresie
                axisMaximum = 28f  // Maksymalna wartość BMI na wykresie
            }

            axisRight.isEnabled = false

            legend.textSize = 12f

            animateX(1500)
        }
    }

    private fun loadBmiData() {
        val bmiRecords = bmiHistoryRepository.getAllBmiRecords()

        if (bmiRecords.isEmpty()) return

        val entries = ArrayList<Entry>()

        // Tworzymy punkty wykresu
        bmiRecords.forEachIndexed { index, record ->
            entries.add(Entry(index.toFloat(), record.bmiValue))
        }

        // Konfigurujemy zestaw danych
        val dataSet = LineDataSet(entries, "BMI").apply {
            color = Color.rgb(65, 105, 225) // Royal Blue
            setCircleColor(Color.rgb(65, 105, 225))
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(true)
            valueTextSize = 10f
            setDrawFilled(true)
            fillColor = Color.rgb(65, 105, 225)
            fillAlpha = 30
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        // Formatujemy etykiety osi X jako daty
        val dateFormat = SimpleDateFormat("MMM yy", Locale.getDefault())
        bmiChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index >= 0 && index < bmiRecords.size) {
                    dateFormat.format(bmiRecords[index].date)
                } else {
                    ""
                }
            }
        }

        // Ustawiamy dane
        val lineData = LineData(dataSet)
        bmiChart.data = lineData
        bmiChart.invalidate() // Odświeżamy wykres
    }

    override fun fixLayout() {
        super.fixLayout()
        // Jeśli potrzebne są dodatkowe dostosowania layoutu
    }
}