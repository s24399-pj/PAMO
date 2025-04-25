package pl.pjwstk.bmiapp.data.calculators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalorieCalculatorTest {

    private static final double DELTA = 0.001;

    @Nested
    @DisplayName("Calculate Calories Tests")
    class CalculateCaloriesTests {

        @Test
        @DisplayName("Calculate calories for male")
        void calculateCaloriesForMale() {
            // Example: 80kg, 180cm, 30 years, male, moderate activity
            double result = CalorieCalculator.calculateCalories(80, 180, 30, true, 1.55);
            double expected = (66 + (13.7 * 80) + (5 * 180) - (6.8 * 30)) * 1.55;

            assertEquals(expected, result, DELTA);
        }

        @Test
        @DisplayName("Calculate calories for female")
        void calculateCaloriesForFemale() {
            // Example: 65kg, 165cm, 25 years, female, light activity
            double result = CalorieCalculator.calculateCalories(65, 165, 25, false, 1.375);
            double expected = (655 + (9.6 * 65) + (1.8 * 165) - (4.7 * 25)) * 1.375;

            assertEquals(expected, result, DELTA);
        }

        @Test
        @DisplayName("Calculate calories using ActivityLevel enum")
        void calculateCaloriesUsingActivityLevelEnum() {
            // Example: 70kg, 175cm, 35 years, male, HIGH activity
            double result = CalorieCalculator.calculateCalories(
                    70, 175, 35, true, CalorieCalculator.ActivityLevel.HIGH);

            double expected = (66 + (13.7 * 70) + (5 * 175) - (6.8 * 35)) * 1.725;

            assertEquals(expected, result, DELTA);
        }

        @ParameterizedTest
        @DisplayName("Calculate calories for various activity levels")
        @EnumSource(CalorieCalculator.ActivityLevel.class)
        void calculateCaloriesForVariousActivityLevels(CalorieCalculator.ActivityLevel level) {
            // Example: 75kg, 170cm, 40 years, male, various activity levels
            double result = CalorieCalculator.calculateCalories(75, 170, 40, true, level);
            double expected = (66 + (13.7 * 75) + (5 * 170) - (6.8 * 40)) * level.getMultiplier();

            assertEquals(expected, result, DELTA);
        }
    }

    @Nested
    @DisplayName("ActivityLevel Tests")
    class ActivityLevelTests {

        @Test
        @DisplayName("All activity levels have correct properties")
        void activityLevelsHaveCorrectProperties() {
            for (CalorieCalculator.ActivityLevel level : CalorieCalculator.ActivityLevel.values()) {
                assertNotNull(level.getDisplayName());
                assertTrue(level.getMultiplier() > 0);
            }
        }

        @ParameterizedTest
        @DisplayName("fromPosition returns correct ActivityLevel for valid positions")
        @ValueSource(ints = {0, 1, 2, 3, 4})
        void fromPositionReturnsCorrectActivityLevelForValidPosition(int position) {
            CalorieCalculator.ActivityLevel level = CalorieCalculator.ActivityLevel.fromPosition(position);
            assertEquals(CalorieCalculator.ActivityLevel.values()[position], level);
        }

        @ParameterizedTest
        @DisplayName("fromPosition returns MODERATE for invalid positions")
        @ValueSource(ints = {-1, -5, 5, 10})
        void fromPositionReturnsModerateForInvalidPosition(int position) {
            CalorieCalculator.ActivityLevel level = CalorieCalculator.ActivityLevel.fromPosition(position);
            assertEquals(CalorieCalculator.ActivityLevel.MODERATE, level);
        }

        @ParameterizedTest
        @DisplayName("getActivityMultiplier returns correct multiplier")
        @CsvSource({
                "0, 1.2",
                "1, 1.375",
                "2, 1.55",
                "3, 1.725",
                "4, 1.9"
        })
        void getActivityMultiplierReturnsCorrectMultiplier(int position, double expectedMultiplier) {
            double multiplier = CalorieCalculator.getActivityMultiplier(position);
            assertEquals(expectedMultiplier, multiplier, DELTA);
        }

        @ParameterizedTest
        @DisplayName("getActivityLevelName returns correct display name")
        @CsvSource({
                "0, Siedzący tryb życia",
                "1, Lekka aktywność",
                "2, Umiarkowana aktywność",
                "3, Wysoka aktywność",
                "4, Bardzo wysoka aktywność"
        })
        void getActivityLevelNameReturnsCorrectDisplayName(int position, String expectedName) {
            String name = CalorieCalculator.getActivityLevelName(position);
            assertEquals(expectedName, name);
        }
    }
}