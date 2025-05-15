package pl.pjwstk.bmiapp.data.calculators;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CalorieCalculator {

    @Getter
    public enum ActivityLevel {
        SEDENTARY(1.2, "Siedzący tryb życia"),
        LIGHT(1.375, "Lekka aktywność"),
        MODERATE(1.55, "Umiarkowana aktywność"),
        HIGH(1.725, "Wysoka aktywność"),
        VERY_HIGH(1.9, "Bardzo wysoka aktywność");

        private final double multiplier;
        private final String displayName;

        ActivityLevel(double multiplier, String displayName) {
            this.multiplier = multiplier;
            this.displayName = displayName;
        }

        public static ActivityLevel fromPosition(int position) {
            if (position >= 0 && position < values().length) {
                return values()[position];
            }
            return MODERATE;
        }
    }

    public static double calculateCalories(double weight, double height, int age, boolean isMale, double activityLevel) {
        double bmr;

        if (isMale) {
            bmr = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        } else {
            bmr = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }

        return bmr * activityLevel;
    }

    public static double calculateCalories(double weight, double height, int age, boolean isMale, ActivityLevel activityLevel) {
        return calculateCalories(weight, height, age, isMale, activityLevel.getMultiplier());
    }

    public static double getActivityMultiplier(int position) {
        return ActivityLevel.fromPosition(position).getMultiplier();
    }

    public static String getActivityLevelName(int position) {
        return ActivityLevel.fromPosition(position).getDisplayName();
    }
}