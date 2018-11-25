package ostrowski;

import java.util.Arrays;

/**
 * This class is a generator for algorithm 3 of ostrowski addition.
 * See super class description.
 */
public class Alg3Automaton extends OstrowskiAutomaton{

    /**
     * Constructor
     * @param r the range
     * @param nRLength the nonRepeated part of the range.
     */
    public Alg3Automaton(int[] r, int nRLength) {
        super(Arrays.stream(r).boxed().map(i->new int[]{i,i}).toArray(int[][]::new), nRLength, new int[]{1,1});
        setDeterministic(r.length == 1);
    }

    @Override
    void addAllTransitions() {
        for (int e = 0; e < totalLength; e++) {
            int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
            int dMax = range[e][1];
            int cMax = range[e1][1];
            int bMax = range[e][0];
            int aMax = range[e1][0];
            for (int a = 0; a <= aMax; a++) {
                for (int b = 0; b <= bMax; b++) {
                    for (int c = 0; c <= cMax; c++) {
                        for (int d = 0; d <= dMax; d++) {
                            if (e!=0) addTransitionsAtIndex(new int[]{a,b,c,d,e}, e-1);
                            if (nonRepeatLength == e) addTransitionsAtIndex(new int[]{a,b,c,d,e}, totalLength-1);
                        }
                    }
                }
            }
        }
    }
    /**
     * Helper method that adds all transitions from a source state.
     * @param entries the entries of the source state
     * @param index the index of the input
     */
    private void addTransitionsAtIndex(int[] entries, int index){

        int iMax = range[index][0];
        int jMax = range[index][1];
        if (index == 0 && nonRepeatLength != 0) {iMax--;jMax--;}
        for (int i = 0; i <= iMax; i++) {
            for (int j = 0; j <= jMax; j++) {
                addTransition(entries,new int[]{i,j},index);
            }
        }
    }

    @Override
    void addAllInitialStates() {
        for (int i = 0; i < range.length; i++) {
            addInitialStateWithEntries(new int[]{0,0,0,0,i});
        }
    }

    @Override
    int entriesToEncoding(int[] entries) {
        int result = 0;
        int e = entries[4];
        int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
        result += entries[0];

        result *= range[e][0]+1;
        result += entries[1];

        result *= range[e1][0]+1;
        result += entries[2];

        result *= range[e][1]+1;
        result += entries[3];

        result *= totalLength;
        result += e;

        return result;
    }

    @Override
    boolean checkFinal(int[] entries) {
        if (DEBUG) {
            if (entriesToEncoding(entries) == 14) {
                System.out.println(Arrays.toString(entries));
            }
        }
        int e1 = 0;
        if (range[0][0] == 1) {
            e1 = e1+1 == totalLength ? 0 : e1+1;
            if (entries[4] != e1) return false;
            entries = findTransitionDestination(entries,new int[]{0,0},0);
            if (entries.length == 0) return false;
        }
        if (entries[4] != 0) return false;
        return (entries[0] == entries[2]) && (entries[1] == entries[3]);
    }

    @Override
    int[] findTransitionDestination(int[] entries, int[] transition,int index) {
        if (DEBUG) {

        }
        int a = entries[0], b = entries[1], c = entries[2];
        int d = entries[3], e = entries[4];
        int f = transition[0], g = transition[1];
        int e1 = e+1 == totalLength ? nonRepeatLength : e+1;

        int a3 = range[e1][1], a2 = range[e][1];

        if (a<a3 && b==a2 && f>0) {a++;b=0;f--;} //rule for ostrowski.Alg3Automaton
        if (a==c) return new int[]{b,f,d,g,index};
        return new int[0];
    }
}
