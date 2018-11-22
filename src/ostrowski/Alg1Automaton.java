package ostrowski;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is a generator for algorithm 1 of ostrowski addition.
 * See super class description.
 */
public class Alg1Automaton extends OstrowskiAutomaton {

    /**
     * Constructor
     * @param r the range
     * @param nRLength the nonRepeated part of the range.
     */
    public Alg1Automaton(int[] r, int nRLength) {
        super(Arrays.stream(r).boxed().map(i->new int[]{i*2,i}).toArray(int[][]::new), nRLength);
    }


    @Override
    void addAllTransitions() {
        for (int h = 0; h < totalLength; h++) {
            int h1 = h+1 == totalLength ? nonRepeatLength : h+1;
            int h2 = h1+1 == totalLength ? nonRepeatLength : h1+1;
            int fMax = range[h][1];
            int eMax = range[h1][1];
            int dMax = range[h2][1];
            int cMax = range[h][0];
            int bMax = range[h1][0];
            int aMax = range[h2][0];
            for (int g = 0; g < 2; g++) {
                for (int a = 0; a <= aMax; a++) {
                    for (int b = 0; b <= bMax; b++) {
                        for (int c = 0; c <= cMax; c++) {
                            for (int d = 0; d <= dMax; d++) {
                                for (int e = 0; e <= eMax; e++) {
                                    for (int f = 0; f <= fMax; f++) {
                                        if (h!=0) addTransitionsAtIndex(new int[]{a,b,c,d,e,f,g,h}, h-1);
                                        if (nonRepeatLength == h) addTransitionsAtIndex(new int[]{a,b,c,d,e,f,g,h}, totalLength-1);
                                    }
                                }
                            }
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
        for (int i = 0; i <= iMax; i++) {
            for (int j = 0; j <= jMax; j++) {
                addTransition(entries, new int[]{i,j}, index);

            }
        }
    }

    @Override
    void addAllInitialStates() {
        for (int i = 0; i < range.length; i++) {
            addInitialStateWithEntries(new int[]{0,0,0,0,0,0,0,i});
        }
    }

    @Override
    int entriesToEncoding(int[] entries) {
        int result = 0;
        int h = entries[7];
        int h1 = h+1 == totalLength ? nonRepeatLength : h+1;
        int h2 = h1+1 == totalLength ? nonRepeatLength : h1+1;
        result += entries[0];

        result *= range[h1][0]+1;
        result += entries[1];

        result *= range[h][0]+1;
        result += entries[2];

        result *= range[h2][1]+1;
        result += entries[3];

        result *= range[h1][1]+1;
        result += entries[4];

        result *= range[h][1]+1;
        result += entries[5];

        result *= 2;
        result += entries[6];

        result *= totalLength;
        result += h;
        return result;
    }

    @Override
    boolean checkFinal(int[] entries) {
        if (entries[7] != 0) return false;
        int a = entries[0], b = entries[1], c = entries[2];
        int d = entries[3], e = entries[4], f = entries[5];
        int g = entries[6];

        int h1 = 0;
        int h2 = h1+1 == totalLength ? nonRepeatLength : h1+1;
        int h3 = h2+1 == totalLength ? nonRepeatLength : h2+1;

        int a3 = range[h3][1], a2 = range[h2][1], a1 = range[h1][1];

        if (g == 1) {a--;b+=a1+1;c=0;}
        if (a<a3&&b>a2&&c==0) {return (a+1 == d) && (b-a2-1 == e) && (f == 1);}

        if (a<a3 && b>=a2 && c>0 && c<=a1) {return (a+1 == d) && (b-a2 == e) && (f == c-1);}
        if (a<a3 && b>=a2 && c>a1)         {return (a+1 == d) && (b-a2+1 == e) && (f == c-a1-1);}
        if (        b<a2  && c>=a1)        {return (a == d) && (b+1 == e) && (f == c-a1);}
        return (a == d) && (b == e) && (f == c);
    }

    @Override
    int[] findTransitionDestination(int[] entries, int[] transition, int indexOfTransition) {
        if (indexOfTransition >= range.length) {
            System.out.println("findTransitionDestination error: indexOfTransition");
            System.exit(0);
        }
        int a = entries[0], b = entries[1], c = entries[2];
        int d = entries[3], e = entries[4], f = entries[5];
        int g = entries[6], h = entries[7];
        int i = transition[0]+g, j = transition[1];
        g = 0;

        int h2 = h+1 == totalLength ? nonRepeatLength : h+1;
        int h3 = h2+1 == totalLength ? nonRepeatLength : h2+1;

        int a3 = range[h3][1], a2 = range[h2][1], a1 = range[h][1];

        if (b<a3 && c>a2 && i == 0) {b++;c-=a2+1;i=a1-1;g=1;}  //A1
        else if (b<a3 && c>=a2 && c<=2*a2 && i>0) {b++;c-=a2;i=i-1;g=0;} //A2

        if (a==d) return new int[]{b,c,i,e,f,j,g,indexOfTransition};
        return new int[0];
    }
}
