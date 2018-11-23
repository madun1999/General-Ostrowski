package ostrowski;

import org.junit.Test;

import static org.junit.Assert.*;

public class Alg1AutomatonTest {

    @Test
    public void entriesToEncoding() {
        Alg1Automaton rt2 = new Alg1Automaton(new int[]{2},0);

        int[] stateEntries = {0,1,4,
                              0,0,2,
                              0,0};
        int expectedEncoding = 544;

        int actualEncoding = rt2.entriesToEncoding(stateEntries);

        assertEquals(expectedEncoding,actualEncoding);
    }

    @Test
    public void checkFinal() {
    }

    @Test
    public void findTransitionDestination() {

        Alg1Automaton rt2 = new Alg1Automaton(new int[]{2},0);

        int[] begin = {0,0,1,
                       0,0,0,
                       0,0};
        int[] transition = {4,
                            2};
        int index = 0;
        int[] expected = {0,1,4,
                          0,0,2,
                          0,0};

        int[] actualDest = rt2.findTransitionDestination(begin,transition,index);
        int actualEncoding = rt2.entriesToEncoding(actualDest);
        int expectedEncoding = rt2.entriesToEncoding(expected);
        assertArrayEquals(expected,actualDest);
        assertEquals(expectedEncoding,actualEncoding);

        int[] odd = {0,1,3,
                     0,0,2,
                     0,0};
        assertNotEquals(rt2.entriesToEncoding(odd),actualEncoding);

    }
}