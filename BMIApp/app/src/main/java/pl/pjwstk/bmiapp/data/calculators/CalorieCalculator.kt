package pl.pjwstk.bmiapp.data.calculators

object CalorieCalculator {

    enum class ActivityLevel(val multiplier: Double, val displayName: String) {
        SEDENTARY(1.2, "Siedzący tryb życia"),
        LIGHT(1.375, "Lekka aktywność"),
        MODERATE(1.55, "Umiarkowana aktywność"),
        HIGH(1.725, "Wysoka aktywność"),
        VERY_HIGH(1.9, "Bardzo wysoka aktywność");

        companion object {
            fun fromPosition(position: Int): ActivityLevel {
                return if (position >= 0 && position < values().size) {
                    values()[position]
                } else {
                    MODERATE
                }
            }
        }
    }

    fun calculateCalories(weight: Double, height: Double, age: Int, isMale: Boolean, activityLevel: Double): Double {
        val bmr: Double

        bmr = if (isMale) {
            66 + (13.7 * weight) + (5 * height) - (6.8 * age)
        } else {
            655 + (9.6 * weight) + (1.8 * height) - (4.7 * age)
        }

        return bmr * activityLevel
    }

    fun calculateCalories(weight: Double, height: Double, age: Int, isMale: Boolean, activityLevel: ActivityLevel): Double {
        return calculateCalories(weight, height, age, isMale, activityLevel.multiplier)
    }

    fun getActivityMultiplier(position: Int): Double {
        return ActivityLevel.fromPosition(position).multiplier
    }

    fun getActivityLevelName(position: Int): String {
        return ActivityLevel.fromPosition(position).displayName
    }
}