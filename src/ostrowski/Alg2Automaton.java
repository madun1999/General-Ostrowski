package ostrowski;

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
        for (int e = -2; e < totalLength; e++) {
            int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
            int e2 = e1+1 == totalLength ? nonRepeatLength : e1+1;
            int dMax = e < 0 ? 0 : range[e][1];
            int cMax = e1 < 0 ? 0 : range[e1][1];
            int bMax = e < 0 ? 0 : range[e][0];
            int aMax = e1 < 0 ? 0 : range[e1][0];
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
        if (DEBUG){
            if (entriesToEncoding(entries) == 1) {
                System.out.println(Arrays.toString(entries)+ index);
            }
        }
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
        addInitialStateWithEntries(new int[]{0,0,0,0,range[0][0] == 1 ? -1: -2});
    }

    @Override
    int entriesToEncoding(int[] entries) {
        int result = 0;
        int e = entries[4];
        int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
        result += entries[0];

        result *= e < 0 ? 1 : range[e][0]+1;
        result += entries[1];

        result *= e1 < 0 ? 1 :range[e1][0]+1;
        result += entries[2];

        result *= e < 0 ? 1 : range[e][1]+1;
        result += entries[3];

        result *= totalLength+2;
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
        if (e < 0) {
            if (b != 0 || d != 0){
                System.out.println("Alg2 findTransitionDestinationError: negative index with non-zero entries b,d");
                System.exit(0);
            }
            if (e1 < 0 && (a != 0 || c != 0)){
                System.out.println("Alg2 findTransitionDestinationError: negative index with non-zero entries a,c");
                System.exit(0);
            }
            if (e2 < 0) {
                System.out.println("Alg2 findTransitionDestinationError: negative index e2");
                System.exit(0);
            }
            return new int[] {f,a,g,c,e1};
        }
        int a3 = range[e2][1], a2 = range[e1][1];

        if (f<a3 && a==a2 && b>0) {f++;a=0;b--;} //rule for ostrowski.Alg2Automaton
        if (b==d) return new int[]{f,a,g,c,e1};
        return new int[0];
    }
}
