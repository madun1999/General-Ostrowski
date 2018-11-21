package ostrowski;

import java.lang.StringBuilder;
import java.util.ArrayList;

class Alg2Automaton extends OstrowskiAutomaton{

    //interchange statenumber and entries
    public static int getStateNumber(int[] entries) {Alg2State temp = new Alg2State(entries);return temp.getStateNumber();}
    public static int[] getEntries(int stateNumber) {Alg2State temp = new Alg2State(stateNumber);return temp.getEntries();}

    public Alg2Automaton(ArrayList<Integer> r, int nRLength) {
        super(r.stream().map(i->new int[]{i,i}).toArray(int[][]::new), nRLength);
    }

    @Override
    void addAllTransitions() {
        for (int e = 0; e < totalLength; e++) {
            int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
            int e2 = e1+1 == totalLength ? nonRepeatLength : e1+1;
            int dMax = range[e1][1];
            int cMax = range[e][1];
            int bMax = range[e1][0];
            int aMax = range[e][0];
            for (int a = 0; a < aMax; a++) {
                for (int b = 0; b < bMax; b++) {
                    for (int c = 0; c < cMax; c++) {
                        for (int d = 0; d < dMax; d++) {
                            addTransitionsAtIndex(new int[]{a, b, c, d, e}, e2);
                        }
                    }
                }
            }
        }
    }

    private void addTransitionsAtIndex(int[] entries, int index){
        int iMax = range[index][0];
        int jMax = range[index][1];
        for (int i = 0; i < iMax; i++) {
            for (int j = 0; j < jMax; j++) {
                addTransition(entries,new int[]{i,j});
            }
        }
    }

    @Override
    void addAllInitialStates() {
        addInitialStateWithEntries(new int[]{0,0,0,0,0});
    }

    @Override
    int entriesToEncoding(int[] entries) {
        int result = 0;
        int e = entries[4];
        int e1 = e+1 == totalLength ? nonRepeatLength : e+1;
        result += entries[0];

        result *= range[e][0];
        result += entries[1];

        result *= range[e1][0];
        result += entries[2];

        result *= range[e][1];
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
    int[] findTransitionDestination(int[] entries, int[] transition) {
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
