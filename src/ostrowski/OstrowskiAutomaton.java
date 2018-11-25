package ostrowski;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

import java.util.*;

/**
 * The ostrowski automaton base class.
 *
 * Explanation for Input range:
 * The range of the input might vary based on the position of the input.
 * For example, in recognition automaton for rt3 Ostrowski numeration system, the first input can range from 0 to 1, the second can range from 0 to 2, the third can range from 0 to 1, etc..
 * In this case, the inputRange would be {{1},{2}}, the maxInputRange would be {2}, and the repeatBeginIndex would be 0.
 * For another example, in algorithm 1 of addition automaton for an Ostrowski numeration system with continuous fraction [5,1,2;3,4] with [3,4] being the repeating part,
 * Then <code> range={{10,5},{2,1},{4,2},{6,3},{8,4}}</code>, <code>nonRepeatLength = 3</code>, <code>totalLength=5</code>, <code>maxInputRange = {10,5}</code>.
 * <code>Alg1Automaton({{10,5},{2,1},{4,2},{6,3},{8,4}},3)</code> will construct an instance of said alg1.
 */
abstract class OstrowskiAutomaton extends Automaton {
    /**
     * The max of each input in different positions.
     * Least significant digit first.
     * See class description for more detail.
     */
    final int[][] range;

    static boolean DEBUG = false;
    /**
     * The length of the non-repeated part of the range.
     * See class description for more detail.
     */
    int nonRepeatLength;

    /**
     * TotalLength of <code>range</code>
     * Must be equal to <code>range.length</code>
     */
    int totalLength;

    /**
     * The maximum range of each input.
     * See class description for more detail.
     */
    int[] maxInputRange;

    /**
     * A temporary dictionary that maps state encoding to states.
     * Only used during the construction of the automaton.
     * After invoking <code>restoreInvariant()</code>, this becomes irrelevant.
     * Key is the encoding of the state.
     * Value is the corresponding state.
     */
    private HashMap<Integer, State> states = new HashMap<>();

    /**
     * The set of initialStates.
     * Only used during the construction of the automaton.
     * After invoking <code>configureInitialStates()</code>, this becomes irrelevant.
     */
    private Set<State> initialStates = new HashSet<>();

    /**
     * Constructor of ostrowski.OstrowskiAutomaton with range of the input and nonRepeated length of the range.
     * See class description for detail.
     * @param r range of the input.
     * @param nRLength nonRepeated length of the range.
     */
    public OstrowskiAutomaton(int[][] r, int nRLength, int[] multiplier) {
        range = r; nonRepeatLength = nRLength;
        totalLength = r.length;
        maxInputRange = OstrowskiCalculator.maxRange(range,nRLength,multiplier);
    }

    /**
     * Calculate the automaton, ready for output.
     */
    void calculateAutomaton() {

        addAllTransitions();

        addAllInitialStates();
        configureInitialStates();

        if (DEBUG) {
            setNumbersToEncoding();
            System.out.println(stateToStringBuilder(getInitialState()));
        } else {
            restoreInvariant();
            states.clear();
            if (isDeterministic()) {
                determinize();
            }
            minimize();
            restoreInvariant();
            setInitialStateNumber();
        }
    }

    /**
     * Set the number of the initial state to 0.
     */
    private void setInitialStateNumber() {
        State initialState = getInitialState();
        int initialNumber = initialState.getNumber();
        State exchangeState = getStates().stream()
                .filter(x -> x.getNumber() == 0)
                .findFirst()
                .orElse(null);
        if (exchangeState == null) {
            System.out.println("Set InitialState number to 0 Error: No state with number 0");
            System.exit(0);
        }
        initialState.setNumber(0);
        exchangeState.setNumber(initialNumber);
    }

    /**
     * Set the number of the states to encoding of the state.
     * Method for debugging.
     */
    private void setNumbersToEncoding() {
        for (Map.Entry<Integer,State> s: states.entrySet()) {
            s.getValue().setNumber(s.getKey());
        }
    }

    /**
     * Prepare for determinization.
     */
    private void configureInitialStates() {
        State initState = new State();
        for(State state:initialStates) {
            initState.addEpsilon(state);
        }
        setInitialState(initState);
        initialStates.clear();
    }

    /**
     * Default constructor. Do nothing.
     */
    public OstrowskiAutomaton() {range = new int[][]{};}

    /**
     * That adds all transitions to the automaton.
     * Calls <code>addTransition(int[],int[],int)</code>
     */
    abstract void addAllTransitions();

    /**
     * Adds all initial states to <code>initialStates</code>.
     * Calls <code>addInitialStateWithEntries(int[])</code>.
     */
    abstract void addAllInitialStates();

    /**
     * Maps entries of a state to corresponding encoding.
     * @param entries entries of the state
     * @return encoding of the state.
     */
    abstract int entriesToEncoding(int[] entries);

    /**
     * Checks if a state is a final state.
     * @param entries the entries of the state.
     * @return true if the state is a final state.
     */
    abstract boolean checkFinal(int[] entries);

    /**
     * Decides the destination of the transition given the source state, input, and the index of the input.
     * @param stateEntries entries of the source state
     * @param transition the input of the transition
     * @param transitionPositionIndex the index of the input
     * @return the entries of the destination state.
     */
    abstract int[] findTransitionDestination(int[] stateEntries, int[] transition, int transitionPositionIndex);

    /**
     * Maps input to its corresponding encoding.
     * @param input the input.
     * @return the encoding of the input.
     */
    private int inputToEncoding(int[] input) {
        int inputSize = input.length;
        int sum = 0;
        int coef = 1;
        for (int i = inputSize-1; i >=0; i--) {
            sum += input[i] * coef;
            coef *= maxInputRange[i]+1;
        }
        return sum;
    }

    /**
     * Get the state of the corresponding state. If the state doesn't exits, create it and add it to <code>states</code>
     * @param entries the entries of the state.
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

    /**
     * Add a transition to the automaton.
     * @param entries the entries of the source state.
     * @param input the input.
     * @param transitionPositionIndex the index of the input.
     */
    public void addTransition(int[] entries, int[] input, int transitionPositionIndex) {
        if (DEBUG && this instanceof Alg3Automaton && entriesToEncoding(entries)==3 && inputToEncoding(input) == 2 && transitionPositionIndex == 0){
            System.out.println(Arrays.toString(entries));
            System.out.println(Arrays.toString(input));
        }
        State state = getStateWithEntries(entries);
        int[] dest = findTransitionDestination(entries,input,transitionPositionIndex);
        if (dest.length != 0) {
            State destState = getStateWithEntries(dest);
            Transition transition = new Transition((char) inputToEncoding(input), destState);
            state.addTransition(transition);
        }
    }

    /**
     * Set a state to be an initial state.
     * @param entries entries of the initial state
     */
    void addInitialStateWithEntries(int[] entries) {
        initialStates.add(getStateWithEntries(entries));
    }


    /**
     * Outputs a StringBuilder for the automaton corresponding Walnut's implementation.
     * @return the resulting StringBuilder for the automaton.
     */
    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        for (int i: maxInputRange) {
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

        Collection<State> states1;
        if (DEBUG) {
            states1 = states.values();
        } else {
            states1 = getStates();
        }
        ArrayList<State> statesList = new ArrayList<>(states1);
        statesList.sort((state,t1) -> t1.getNumber() > state.getNumber() ? -1 : 1);
        for (State state : statesList) {
            s.append(stateToStringBuilder(state));
        }
        return s;
    }

    /**
     * Outputs a StringBuilder for the state corresponding Walnut's implementation.
     * @param s the state
     * @return the resulting StringBuilder.
     */
    private StringBuilder stateToStringBuilder(State s) {
        StringBuilder builder = new StringBuilder();
        builder.append(s.getNumber());builder.append(" "); builder.append(s.isAccept()? 1:0); builder.append("\n");
        ArrayList<Transition> transitionList = new ArrayList<>(s.getTransitions());
        transitionList.sort((transition,t1) -> t1.getMin() < transition.getMin() ? -1 : 1);
        for (Transition transition:transitionList) {
            builder.append(transitionToStringBuilder(transition));
        }
        return builder;
    }

    /**
     * Outputs a StringBuilder for the transition corresponding Walnut's implementation.
     * @param t the transition
     * @return the resulting StringBuilder for the transition.
     */
    private StringBuilder transitionToStringBuilder(Transition t) {
        StringBuilder builder = new StringBuilder();
        for(int num1 = t.getMin(); num1 <= t.getMax(); num1++) {
            int num = num1;
            ArrayList<Integer> input = new ArrayList<>();
            for (int i = maxInputRange.length - 1; i >= 0; i--) {
                int a = num % (maxInputRange[i] + 1);
                input.add(0, a);
                num -= a;
                num /= (maxInputRange[i] + 1);
            }
            for (Integer a : input) {
                builder.append(a);
                builder.append(" ");
            }
            builder.append("-> ");
            builder.append(t.getDest().getNumber());
            builder.append("\n");
        }
        return builder;
    }

}

