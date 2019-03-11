package ostrowski;

import java.lang.StringBuilder;
import java.util.Arrays;

/**
 * This class is a generator for algorithm 0.
 *
 * Requirement:
 * toStringBuilder(int max) should return a StringBuilder representing algorithm 0.
 * For example, for rt2, since the max=2, the returned string should look like:
 *
 * {0,1,2} {0,1,2} {0,1,2,3,4}
 * 0 1
 * 0 0 -> 0
 * 0 1 -> 0
 * 0 2 -> 0
 * 1 0 -> 0
 * 1 1 -> 0
 * 1 2 -> 0
 * 2 0 -> 0
 * 2 1 -> 0
 * 2 2 -> 0
 *
 */
public class Alg0Automaton extends OstrowskiAutomaton {

    /**
     * The max of the input.
     */
    private final int max;

    /**
     * Constructor.
     * @param r the range of the input.
     */
    public Alg0Automaton(int[] r, int nRLength) {
        super(Arrays.stream(r).boxed().map(i->new int[]{i}).toArray(int[][]::new), nRLength, new int[]{1});
        max = Arrays.stream(maxInputRange).max().orElse(0);
        setDeterministic(true);
    }

    /**
     * Return a StringBuilder representing algorithm 0.
     * Read the class description.
     * @return StringBuilder for the state.
     */
    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < 2; j++) {
            s.append("{");
            for (int i = 0; i < max; i++) {
                s.append(i);
                s.append(",");
            }
            s.append(max);
            s.append("}");
            s.append(" ");
        }

        s.append("{");
        for (int i = 0; i < max*2; i++) {
            s.append(i);
            s.append(",");
        }
        s.append(max*2);
        s.append("}");
        s.append("\n");
        s.append("0 1");
        s.append("\n");
        for (int i = 0;i<=max;i++ ) {
            for (int j=0;j<=max;j++ ) {
                s.append(i);
                s.append(" ");
                s.append(j);
                s.append(" ");
                s.append(i+j);
                s.append(" -> 0");
                s.append("\n");
            }
        }
        return s;
    }

    @Override
    void addAllTransitions() {

    }

    @Override
    void addAllInitialStates() {

    }

    @Override
    int entriesToEncoding(int[] entries) {
        return 0;
    }

    @Override
    boolean checkFinal(int[] entries) {
        return false;
    }

    @Override
    int[] findTransitionDestination(int[] stateEntries, int[] transition, int transitionPositionIndex) {
        return new int[0];
    }
}
