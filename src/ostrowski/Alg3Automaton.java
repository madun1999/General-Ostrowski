package ostrowski;

import java.util.ArrayList;

class Alg3Automaton extends OstrowskiAutomaton{

    public Alg3Automaton(ArrayList<Integer> r, int nRLength) {
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
                            if (e!=0) addTransitionsAtIndex(new int[]{a,b,c,d,e}, e-1);
                            if (nonRepeatLength == e) addTransitionsAtIndex(new int[]{a,b,c,d,e}, totalLength-1);
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
    int[] findTransitionDestination(int[] entries, int[] transition,int index) {
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
