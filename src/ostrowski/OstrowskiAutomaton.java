package ostrowski;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.*;

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
abstract class OstrowskiAutomaton extends Automaton {
    /**
     * The OstrowskiState base class.
     */
    class OstrowskiState extends State {

//        /**
//         * The encoding of the state.
//         */
//        int encoding;
//
//        /**
//         * If the state is a final state.
//         */
//        boolean isFinal;
//
//        /**
//         * Constructor given encoding and isFinal.
//         * @param a encoding
//         * @param f isFinal
//         */
//        public OstrowskiState(int a, boolean f) {encoding = a; isFinal = f;}
//
//        /**
//         * Encode the given input.
//         * @param in input.
//         * @return Encoding.
//         */
//        private int encodeInput(int[] in) {
//            int sum = 0;
//            int multiplier=1;
//            for(int i: in) {
//                sum+=multiplier*i; multiplier*=inputRange[0][0];
//            }
//            return sum;
//        }
//
//        /**
//         * Decode the given input from its encoding.
//         * @param in the encoded input.
//         * @return the decoded input.
//         */
//        private ArrayList<Integer> decodeInput(int in) {
//            int sum = in;
//            int multiplier=inputRange[0][0];
//            ArrayList<Integer> inputList = new ArrayList<>();
//            while(in > 0) {
//                inputList.add(sum%multiplier);
//                in -= sum%multiplier;
//                multiplier*=inputRange[0][0];
//
//            }
//            return inputList;
//        }
//
//        /**
//         * Add/Update a transition to the transition table.
//         * @param input The transition to be added.
//         * @param destination The destination of the transition.
//         */
//        void putTransition(int[] input, OstrowskiState destination) { transitions.put(encodeInput(input),destination); }
//
//        /**
//         * Setter for isFinal
//         * @param aFinal isFinal
//         */
//        public void setFinal(boolean aFinal) {isFinal = aFinal;}
//
//        /**
//         * Getter of isFinal
//         * @return isFinal
//         */
//        public boolean isFinal() {return isFinal;}

        /**
         * Outputs a StringBuilder fot the state corresponding Walnut's implementation.
         * @return the resulting StringBuilder.
         */
        public StringBuilder toStringBuilder() {
            StringBuilder builder = new StringBuilder();
            builder.append(getNumber());builder.append(" "); builder.append(isAccept()? 1:0); builder.append("\n");
            for (Transition transition:getTransitions()) {
                builder.append(((OstrowskiTransition) transition).toStringBuilder());
                builder.append("\n");
            }
            return builder;
        }

    }

    class OstrowskiTransition extends Transition{
        OstrowskiTransition(int i, OstrowskiState state) {super((char) i, state);}
        public StringBuilder toStringBuilder() {
            StringBuilder builder = new StringBuilder();
            int num = getMax();
            for (int i: maxRange) {
                int a = num % i;
                builder.append(a);
                builder.append(" ");
                num -= a;
                num /= i;
            }
            builder.append("-> ");
            builder.append(getDest().getNumber());
            return builder;
        }

        @Override
        public int hashCode() {
            return getMax();
        }
    }


    /**
     * Stores all the states in the automaton.
     * Key is the encoding of the state.
     */
    private HashMap<Integer, OstrowskiState> states = new HashMap<>();

    private Set<State> initialStates = new HashSet<>();

    /**
     * The range of the input = the max of the input + 1.
     * See class description for more detail.
     */
    int[][] nonRepeatingA;

    /**
     * The max of range of each input.
     * See class description for more detail.
     */
    int[][] repeatingA;

    int[] maxRange;

    private void setMaxRange() {
        int[] a = OstrowskiCalculator.maxRange(repeatingA);
        int[] b = OstrowskiCalculator.maxRange(nonRepeatingA);
        int count = a.length;
        for (int i = 0; i < count; i++) {
            maxRange[i] = a[i] > b[i] ? a[i] : b[i];
        }
    }

    /**
     * Constructor of ostrowski.OstrowskiAutomaton with size and range of inputs.
     * @param repeat size of the input.
     * @param nonRepeat range of the input.
     */
    public OstrowskiAutomaton(int[][] repeat, int[][] nonRepeat) {
        nonRepeatingA = nonRepeat; repeatingA = repeat;
        maxRange = new int[repeat[0].length];
        setMaxRange();
        fillAutomaton();
        restoreInvariant();
        states.clear();
        BasicOperations.determinize(this, initialStates);
        initialStates.clear();
        initialStates.add(getInitialState());
    }

    @Override
    public Set<State> getAcceptStates() {
        HashSet<State> accepts = new HashSet<>();
        HashSet<State> visited = new HashSet<>(initialStates);
        LinkedList<State> worklist = new LinkedList<>(initialStates);
        while (worklist.size() > 0) {
            State s = worklist.removeFirst();
            if (s.isAccept())
                accepts.add(s);
            for (Transition t : s.getTransitions())
                if (!visited.contains(t.getDest())) {
                    visited.add(t.getDest());
                    worklist.add(t.getDest());
                }
        }
        return accepts;
    }

    @Override
    public Set<State> getStates() {
        Set<State> visited;
        visited = new HashSet<>(initialStates);
        LinkedList<State> worklist = new LinkedList<>(initialStates);
        while (worklist.size() > 0) {
            State s = worklist.removeFirst();
            Collection<Transition> tr;
            tr = s.getTransitions();
            for (Transition t : tr)
                if (!visited.contains(t.getDest())) {
                    visited.add(t.getDest());
                    worklist.add(t.getDest());
                }
        }
        return visited;
    }

    /**
     * Default constructor. Do nothing.
     */
    public OstrowskiAutomaton() {}

    abstract void fillAutomaton();

    abstract int entriesToEncoding(int[] entries);

    abstract boolean checkFinal(int[] entries);

    abstract int[] findTransitionDestination(int[] stateEntries, int[] transition);
    
    private int inputToEncoding(int[] input) {
        int inputSize = input.length;
        int sum = 0;
        int coef = 1;
        for (int i = 0; i < inputSize; i++) {
            sum += input[i] * coef;
            coef *= maxRange[i];
        }
        return sum;
    }
    /**
     * Add a state to the automaton
     * @param entries the encoding of the state.
     */
    private OstrowskiState getStateWithEntries(int[] entries) {
        int encoding = entriesToEncoding(entries);
        if (!states.containsKey(encoding)) {
            OstrowskiState state = new OstrowskiState();
            state.setAccept(checkFinal(entries));
            states.put(encoding, state);
            return state;
        }
        return states.get(encoding);
    }

    public void addTransitions(int[] entries, LinkedList<int[]> inputs) {
        OstrowskiState state = getStateWithEntries(entries);
        for (int[] input : inputs) {
            int[] dest = findTransitionDestination(entries,input);
            if (dest.length != 0) {
                OstrowskiState destState = getStateWithEntries(dest);
                OstrowskiTransition transition = new OstrowskiTransition(inputToEncoding(input),destState);
                state.addTransition(transition);
            }
        }
    }

    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        for (int i:maxRange) {
            s.append("{");
            for (int j = 0; j < i; j++) {
                s.append(j);
                s.append(",");
            }
            s.append(i);
            s.append("}");
            s.append(" ");
        }
        s.append("\n");

        for (OstrowskiState state : states.values()) {
            s.append(state.toStringBuilder());
        }
        return s;
    }
}

