package pl.pjwstk.bmiapp;

/**
 * Klasa odpowiedzialna za obliczanie dziennego zapotrzebowania kalorycznego
 * według wzoru Benedicta-Harrisa
 */
public class CalorieCalculator {

    // Poziomy aktywności fizycznej i ich mnożniki
    public static final double ACTIVITY_SEDENTARY = 1.2;        // Siedzący tryb życia
    public static final double ACTIVITY_LIGHT = 1.375;          // Lekka aktywność
    public static final double ACTIVITY_MODERATE = 1.55;        // Umiarkowana aktywność
    public static final double ACTIVITY_HIGH = 1.725;           // Wysoka aktywność
    public static final double ACTIVITY_VERY_HIGH = 1.9;        // Bardzo wysoka aktywność

    /**
     * Oblicza dzienne zapotrzebowanie kaloryczne według wzoru Benedicta-Harrisa
     *
     * @param weight waga w kg
     * @param height wzrost w cm
     * @param age wiek w latach
     * @param isMale płeć (true dla mężczyzny, false dla kobiety)
     * @param activityLevel poziom aktywności fizycznej (jako mnożnik)
     * @return dzienne zapotrzebowanie kaloryczne w kcal
     */
    public static double calculateCalories(double weight, double height, int age, boolean isMale, double activityLevel) {
        double bmr;

        if (isMale) {
            // Wzór dla mężczyzn: 66 + (13.7 × waga) + (5 × wzrost) - (6.8 × wiek)
            bmr = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        } else {
            // Wzór dla kobiet: 655 + (9.6 × waga) + (1.8 × wzrost) - (4.7 × wiek)
            bmr = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }

        // Mnożenie BMR przez współczynnik aktywności
        return bmr * activityLevel;
    }

    /**
     * Zwraca mnożnik aktywności na podstawie pozycji z listy
     *
     * @param position pozycja na liście (0-4)
     * @return odpowiedni mnożnik
     */
    public static double getActivityMultiplier(int position) {
        switch (position) {
            case 0: return ACTIVITY_SEDENTARY;
            case 1: return ACTIVITY_LIGHT;
            case 2: return ACTIVITY_MODERATE;
            case 3: return ACTIVITY_HIGH;
            case 4: return ACTIVITY_VERY_HIGH;
            default: return ACTIVITY_MODERATE; // domyślnie
        }
    }
}