package ostrowski;
import java.util.Arrays;

public class RecognitionAutomaton extends OstrowskiAutomaton{

    /**
     * Constructor
     * @param r the range
     * @param nRLength the nonRepeated part of the range.
     */
    public RecognitionAutomaton(int[] r, int nRLength) {
        super(Arrays.stream(r).boxed().map(i->new int[]{i}).toArray(int[][]::new), nRLength, new int[]{1});
        setDeterministic(true);
    }


    @Override
    void addAllTransitions() {
        for (int i = 0; i < totalLength; i++) {
            int i1 = i+1 == totalLength ? nonRepeatLength : i+1;
            int kMax = range[i][0];
            for (int k = 0; k <= kMax; k++){
                addTransition(new int[]{i,0},new int[]{k},i1);
            }
            for (int k = 0; k <= kMax-1; k++){
                addTransition(new int[]{i,1},new int[]{k},i1);
            }
        }
    }

    @Override
    void addAllInitialStates() {
        if (range[0][0] == 1) addInitialStateWithEntries(new int[]{1==totalLength ? 0:1, 0});
        else addInitialStateWithEntries(new int[]{0,1});
    }

    @Override
    int entriesToEncoding(int[] entries) {
        return entries[0]*2 + entries[1];
    }

    @Override
    boolean checkFinal(int[] entries) {
        if (DEBUG) {
            if (entriesToEncoding(entries) == 421) System.out.println(Arrays.toString(entries));
        }
        return true;
    }

    @Override
    int[] findTransitionDestination(int[] entries, int[] transition, int indexOfTransition) {
        if (indexOfTransition >= range.length) {
            System.out.println("findTransitionDestination error: indexOfTransition");
            System.exit(0);
        }
        if (DEBUG) {

        }
        if ((entries[0] + 1 == totalLength ? nonRepeatLength : entries[0] + 1) != indexOfTransition) {
            System.out.println("findTransitionDestination error: indexOfTransition");
            System.exit(0);
        }
        int i = transition[0];

        if (i == 0) return new int[]{indexOfTransition,0};
        return new int[]{indexOfTransition,1};

    }
}
