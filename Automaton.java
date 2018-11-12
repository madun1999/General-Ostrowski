import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The automaton base class.
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
                sum+=multiplier*i; multiplier*=inputRange.get(0)[0];
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
            int multiplier=inputRange.get(0)[0];
            ArrayList<Integer> inputList = new ArrayList<>();
            while(in > 0) {
                inputList.add(sum%multiplier);
                in -= sum%multiplier;
                multiplier*=inputRange.get(0)[0];

            }
            return inputList;
        }

        /**
         * Add/Update a transition to the transition table.
         * @param input The transition to be added.
         * @param destination The destination of the transition.
         */
        public void putTransition(int[] input, State destination) { transitions.put(encodeInput(input),destination); }

        /**
         * Getter for encoding of the state.
         * @return encoding
         */
        public int getEncoding() {return encoding;}

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
        private String inputToString(int a){
            StringBuilder builder = new StringBuilder();
            for (int i:decodeInput(a)) {
                builder.append(i);
                builder.append(" ");
            }
            return builder.toString();
        }

        /**
         * Outputs a string fot the state corresponding Walnut's implementation.
         * @return the resulting string.
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(encoding);builder.append(" "); builder.append(isFinal? 1:0); builder.append("\n");
            for (Map.Entry<Integer,State> entry:transitions.entrySet()) {
                builder.append(inputToString(entry.getKey()));
                builder.append("-> ");
                builder.append(entry.getValue().encoding);
                builder.append("\n");
            }
            return builder.toString();
        }
    }

    /**
     * Stores all the states in the automaton.
     * Key is the encoding of the state.
     */
    private HashMap<Integer,State> states = new HashMap<>();

    /**
     * InputEncoder table.
     * If the encoder is the same for all inputs, then this table should contain only one key-value pair.
     * Key is an number associated with the encoders.
     * Values are the encoders for the input.
     */
    HashMap<Integer,ArrayList<Integer>> inputEncoders = new HashMap<>();

    /**
     * StateEncoder table.
     * If the encoder is the same for all states, then this table should contain only one key-value pair.
     * Key is an number associated with the encoders.
     * Values are the encoders for the state.
     */
    HashMap<Integer,ArrayList<Integer>> stateEncoders = new HashMap<>();

    /**
     * The size of the input.
     */
    private int inputSize;

    /**
     * The range of the input.
     */
    private ArrayList<int[]> inputRange;

    /**
     * Constructor of Automaton with size and range of inputs.
     * @param size size of the input.
     * @param range range of the input.
     */
    public Automaton(int size, ArrayList<int[]> range) {inputSize = size; inputRange = range;}

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
            if (i >= inputRange.get(0)[0]) {return false;}
        }
        states.get(state).putTransition(input, states.get(dest));
        return true;
    }

    /**
     * Outputs a string for the automaton corresponding Walnut's implementation.
     * @return the resulting string.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (State s:states.values()) {
            builder.append(s.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}
