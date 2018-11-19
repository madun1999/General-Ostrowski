package ostrowski;

import java.util.ArrayList;

/**
 * This class includes helper functions for Ostrowski numeration system.
 */
class OstrowskiCalculator{
    /**
     * This gives the range for input given continuous fraction of the quadratic number, the size of input, and the multiplier for each input.
     * @param repeated The repeated part of the continuous fraction of the quadratic number.
     * @param nonRepeated The non-repeated part of the continuous fraction.
     * @param size The size of the input.
     * @param multiplier The multiplier for each input. i.e. if multiplier is 2 for input 0, then the range will be doubled for input 0.
     * @return the range of each input at all position.
     */
    public static int[][] rangeCalculator(ArrayList<Integer> repeated, ArrayList<Integer> nonRepeated, int size, int[] multiplier) {
        ArrayList<int[]> result = new ArrayList<>();
        if (repeated.isEmpty()) return new int[][]{};
        while (!nonRepeated.isEmpty() && nonRepeated.get(nonRepeated.size()-1).equals(repeated.get(0))) {
            repeated.add(repeated.get(0));
            repeated.remove(0);
            nonRepeated.remove(nonRepeated.size()-1);
        }
        for (int i = 0; i < nonRepeated.size(); i++) { //the ith position
            int[] range = new int[size];
            for (int j = 0; j < size; j++) { //the jth input
                range[j] = nonRepeated.get(0)*multiplier[j];
            }
            result.add(range);
        }
        for (int i = 0; i < repeated.size(); i++) {
            int[] range = new int[size];
            for (int j = 0; j < size; j++) { //the jth input
                range[j] = repeated.get(0)*multiplier[j];
            }
            result.add(range);
        }
        return result.toArray(new int[][]{});
    }

    /**
     * Calculates the max range for each input.
     * @param range The range of inputs in different positions.
     * @return The max range for each input.
     */
    static int[] maxRange(int[][] range) {
        if (range.length == 0) return new int[]{};
        int[] result = new int[range[0].length];
        for (int i = 0; i < range.length; i++) {
            for (int j = 0; j < range[0].length; j++) {
                if (i == 0 || result[j] < range[i][j]) {result[j] = range[i][j];}
            }
        }
        return result;
    }
}