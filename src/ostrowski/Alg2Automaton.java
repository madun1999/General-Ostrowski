package ostrowski;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * This class is a generator for algorithm 2 of ostrowski addition.
 * See super class description.
 */
public class Alg2Automaton extends OstrowskiAutomaton{

    /**
     * Constructor
     * @param r the range
     * @param nRLength the nonRepeated part of the range.
     */
    public Alg2Automaton(int[] r, int nRLength) {
        super(Arrays.stream(r).boxed().map(i->new int[]{i,i}).toArray(int[][]::new), nRLength, new int[]{1,1});
        setDeterministic(true);
    }

    @Override
    void addAllTransitions() {
        for (int e = 0; e < totalLength; e++) {
            int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
            int e2 = e1+1 == totalLength ? nonRepeatLength : e1+1;
            int dMax = range[e][1];
            int cMax = range[e1][1];
            int bMax = range[e][0];
            int aMax = range[e1][0];
            for (int a = 0; a <= aMax; a++) {
                for (int b = 0; b <= bMax; b++) {
                    for (int c = 0; c <= cMax; c++) {
                        for (int d = 0; d <= dMax; d++) {
                            addTransitionsAtIndex(new int[]{a, b, c, d, e}, e2);
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
        if (range[0][0] == 1)
            addInitialStateWithEntries(new int[]{0,0,0,0,1 == totalLength ? 0 : 1});
        else
            addInitialStateWithEntries(new int[]{0,0,0,0,0});
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
        return (entries[0] == entries[2]) && (entries[1] == entries[3]);
    }

    @Override
    int[] findTransitionDestination(int[] entries, int[] transition,int index) {
        int a = entries[0], b = entries[1], c = entries[2];
        int d = entries[3], e = entries[4];
        int f = transition[0], g = transition[1];
        int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
        int e2 = e1+1 == totalLength ? nonRepeatLength : e1+1;

        int a3 = range[e2][1], a2 = range[e1][1];

        if (f<a3 && a==a2 && b>0) {f++;a=0;b--;} //rule for ostrowski.Alg2Automaton
        if (b==d) return new int[]{f,a,g,c,e1};
        return new int[0];
    }
}
