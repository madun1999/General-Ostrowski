package ostrowski;

import dk.brics.automaton.Automaton;
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
 * The input Range would be {{10,5},{2,1},{4,2},{6,3},{8,4}}
 *
 */
abstract class OstrowskiAutomaton extends Automaton {
    /**
     * The range of the input = the max of the input + 1.
     * See class description for more detail.
     */
    int[][] range;

    /**
     * The max of range of each input.
     * See class description for more detail.
     */
    int nonRepeatLength;

    int repeatPeriod;

    int totalLength;

    int[] maxRange;

    /**
     * Stores all the states in the automaton.
     * Key is the encoding of the state.
     */
    private HashMap<Integer, State> states = new HashMap<>();

    private Set<State> initialStates = new HashSet<>();

    /**
     * Constructor of ostrowski.OstrowskiAutomaton with size and range of inputs.
     * @param r size of the input.
     * @param nRLength range of the input.
     */
    public OstrowskiAutomaton(int[][] r, int nRLength) {
        range = r; nonRepeatLength = nRLength;
        totalLength = r.length; repeatPeriod = totalLength-nRLength;
        maxRange = OstrowskiCalculator.maxRange(range);
        addAllTransitions();
        addAllInitialStates();
        configureInitialStates();
        restoreInvariant();
        states.clear();
        determinize();
    }



    private void configureInitialStates() {
        State initState = new State();
        for(State state:initialStates)
        initState.addEpsilon(state);
        setInitialState(initState);
        initialStates.clear();
    }
    /**
     * Default constructor. Do nothing.
     */
    public OstrowskiAutomaton() {}

    abstract void addAllTransitions();

    abstract void addAllInitialStates();

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
    private State getStateWithEntries(int[] entries) {
        int encoding = entriesToEncoding(entries);
        if (!states.containsKey(encoding)) {
            State state = new State();
            state.setAccept(checkFinal(entries));
            states.put(encoding, state);
            return state;
        }
        return states.get(encoding);
    }

    public void addTransition(int[] entries, int[] input) {
        State state = getStateWithEntries(entries);
        int[] dest = findTransitionDestination(entries,input);
        if (dest.length != 0) {
            State destState = getStateWithEntries(dest);
            Transition transition = new Transition((char) inputToEncoding(input), destState);
            state.addTransition(transition);
        }
    }

    void addInitialStateWithEntries(int[] entries) {
        initialStates.add(getStateWithEntries(entries));
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

        for (State state : states.values()) {
            s.append(stateToStringBuilder(state));
        }
        return s;
    }

    /**
     * Outputs a StringBuilder fot the state corresponding Walnut's implementation.
     * @return the resulting StringBuilder.
     */
    private StringBuilder stateToStringBuilder(State s) {
        StringBuilder builder = new StringBuilder();
        builder.append(s.getNumber());builder.append(" "); builder.append(s.isAccept()? 1:0); builder.append("\n");
        for (Transition transition:s.getTransitions()) {
            builder.append(transitionToStringBuilder(transition));
        }
        return builder;
    }

    private StringBuilder transitionToStringBuilder(Transition t) {
        StringBuilder builder = new StringBuilder();
        int num = t.getMax();
        for (int i: maxRange) {
            int a = num % i;
            builder.append(a);
            builder.append(" ");
            num -= a;
            num /= i;
        }
        builder.append("-> ");
        builder.append(t.getDest().getNumber());
        builder.append("\n");
        return builder;
    }

}

