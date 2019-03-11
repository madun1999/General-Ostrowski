package ostrowski;

import java.util.ArrayList;

/**
 * This class includes helper functions for Ostrowski numeration system.
 */
public class OstrowskiCalculator{

    /**
     * Calculates the max range for each input.
     * @param range The range of inputs in different positions.
     * @return The max range for each input.
     */
    static int[] maxRange(int[][] range, int nonRepeatedLength, int[] multiplier) {
        if (range.length == 0) return new int[]{};
        int[] result = new int[range[0].length];
        for (int i = 0; i < range.length; i++) {
            for (int j = 0; j < range[0].length; j++) {
                if (i == 0 && nonRepeatedLength != 0) result[j] = range[i][j]-multiplier[j];
                else if (i == 0 || result[j] < range[i][j]) {result[j] = range[i][j];}
            }
        }
        return result;
    }
}