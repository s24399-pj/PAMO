package pl.pjwstk.bmiapp.data.repositories

import pl.pjwstk.bmiapp.data.models.BmiRecord
import java.util.Calendar
import java.util.Date

class BmiHistoryRepository {
    private val bmiRecords = mutableListOf<BmiRecord>()

    init {
        generateMockData()
    }

    private fun generateMockData() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -12)

        for (i in 0 until 12) {
            val date = calendar.time

            val trend = when {
                i < 4 -> 26f - (i * 0.5f)
                i < 8 -> 24f - ((i-4) * 0.3f)
                else -> 22.8f + ((i-8) * 0.1f)
            }

            val bmiValue = trend + (Math.random() * 0.6 - 0.3).toFloat()

            val height = 1.75f
            val weight = bmiValue * (height * height)

            bmiRecords.add(
                BmiRecord(
                    id = i,
                    date = date,
                    bmiValue = bmiValue,
                    weight = weight,
                    height = height
                )
            )

            calendar.add(Calendar.MONTH, 1)
        }
    }

    fun getAllBmiRecords(): List<BmiRecord> {
        return bmiRecords.sortedBy { it.date }
    }

    companion object {
        private var instance: BmiHistoryRepository? = null

        fun getInstance(): BmiHistoryRepository {
            if (instance == null) {
                instance = BmiHistoryRepository()
            }
            return instance!!
        }
    }
}