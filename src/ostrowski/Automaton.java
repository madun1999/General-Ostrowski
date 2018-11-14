package ostrowski;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The automaton base class.
 *
 * Explanation for Input range:
 * The range of the input might vary based on the position of the input.
 * For example, in recognition automaton for rt3 Ostrowski numeration system, the first input can range from 0 to 1, the second can range from 0 to 2, the third can range from 0 to 1, etc..
 * In this case, the inputRange would be {{1},{2}}, the maxRange would be {2}, and the repeatBeginIndex would be 0.
 * For another example, in algorithm 1 of addition automaton for an Ostrowski numeration system with continuous fraction [5,1,2;3,4] with [3,4] being the repeating part,
 * The input Range would be {{10,5,5},{2,1,1},{4,2,2},{6,3,3},{8,4,4}}
 *
 */
class Automaton{
    /**
     * The State base class.
     */
    class State {
        /**
         * The transition table.
         * Key is the encoding of the input.
         * Value is the destination state.
         */
        HashMap<Integer,State> transitions = new HashMap<>();

        /**
         * The encoding of the state.
         */
        int encoding;

        /**
         * If the state is a final state.
         */
        boolean isFinal;

        /**
         * Constructor given encoding and isFinal.
         * @param a encoding
         * @param f isFinal
         */
        public State(int a, boolean f) {encoding = a; isFinal = f;}

        /**
         * Encode the given input.
         * @param in input.
         * @return Encoding.
         */
        private int encodeInput(int[] in) {
            int sum = 0;
            int multiplier=1;
            for(int i: in) {
                sum+=multiplier*i; multiplier*=inputRange[0][0];
            }
            return sum;
        }

        /**
         * Decode the given input from its encoding.
         * @param in the encoded input.
         * @return the decoded input.
         */
        private ArrayList<Integer> decodeInput(int in) {
            int sum = in;
            int multiplier=inputRange[0][0];
            ArrayList<Integer> inputList = new ArrayList<>();
            while(in > 0) {
                inputList.add(sum%multiplier);
                in -= sum%multiplier;
                multiplier*=inputRange[0][0];

            }
            return inputList;
        }

        /**
         * Add/Update a transition to the transition table.
         * @param input The transition to be added.
         * @param destination The destination of the transition.
         */
        void putTransition(int[] input, State destination) { transitions.put(encodeInput(input),destination); }

        /**
         * Setter for isFinal
         * @param aFinal isFinal
         */
        public void setFinal(boolean aFinal) {isFinal = aFinal;}

        /**
         * Getter of isFinal
         * @return isFinal
         */
        public boolean isFinal() {return isFinal;}

        /**
         * A helper function to translate encoded input to string representing the input.
         * @param a the encoding of input
         * @return the string representation of input corresponding to Walnut's implementation.
         */
        private StringBuilder inputToStringBuilder(int a){
            StringBuilder builder = new StringBuilder();
            for (int i:decodeInput(a)) {
                builder.append(i);
                builder.append(" ");
            }
            return builder;
        }

        /**
         * Outputs a StringBuilder fot the state corresponding Walnut's implementation.
         * @return the resulting StringBuilder.
         */
        public StringBuilder toStringBuilder() {
            StringBuilder builder = new StringBuilder();
            builder.append(encoding);builder.append(" "); builder.append(isFinal? 1:0); builder.append("\n");
            for (Map.Entry<Integer,State> entry:transitions.entrySet()) {
                builder.append(inputToStringBuilder(entry.getKey()));
                builder.append("-> ");
                builder.append(entry.getValue().encoding);
                builder.append("\n");
            }
            return builder;
        }
    }

    /**
     * Stores all the states in the automaton.
     * Key is the encoding of the state.
     */
    private HashMap<Integer,State> states = new HashMap<>();

    /**
     * The size of the input.
     */
    int inputSize = 0;

    /**
     * The range of the input. The max the input can be + 1.
     * See class description for more detail.
     */
    private int[][] inputRange = {{1}};

    /**
     * The max of range of each input.
     * See class description for more detail.
     */
    private int[] maxRange = {1};

    /**
     * The index that the input range start repeating.
     * See class description for more detail.
     */
    private int repeatBeginIndex = 0;
    /**
     * Constructor of ostrowski.Automaton with size and range of inputs.
     * @param size size of the input.
     * @param range range of the input.
     */
    public Automaton(int size, int[][] range) {inputSize = size; inputRange = range; maxRange = OstrowskiCalculator.maxRange(range);}

    /**
     * Default constructor. Do nothing.
     */
    public Automaton() {}

    public void setInputRange(int[][] inputRange) {
        this.inputRange = inputRange;
        maxRange = OstrowskiCalculator.maxRange(inputRange);
    }

    /**
     *
     */
    /**
     * Add a state to the automaton
     * @param encoding the encoding of the state.
     * @param isFinal if is the state final.
     */
    public void addState(int encoding, boolean isFinal) {
        if (states.containsKey(encoding)) {
            states.get(encoding).setFinal(isFinal);
        } else {
            states.put(encoding, new State(encoding, isFinal));
        }
    }

    /**
     * Add a transition to the automaton.
     * @param state the encoding of the origin of the transition.
     * @param input the input of the transition.
     * @param dest the destination of the transition.
     * @return true if the transition is successfully added.
     */
    public boolean putTransition(int state, int[] input, int dest) {
        if (!states.containsKey(dest)) {addState(dest,false);}
        if (input.length != inputSize) {return false;}
        for (int i: input) {
            if (i >= inputRange[0][0]) {return false;}
        }
        states.get(state).putTransition(input, states.get(dest));
        return true;
    }

    public StringBuilder allStatesToString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < maxRange.length; i++) {
            s.append("{");
            for (int j = 0; j < maxRange[0]*2; i++) {
                s.append(j);
                s.append(",");
            }
            s.append(maxRange[0]*2);
            s.append("}");
            s.append(" ");
        }
        s.append("\n");

        for (State state : states.values()) {     //6 6 6 3 3 3 2
            s.append(state.toStringBuilder());
        }
        return s;
    }
}

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