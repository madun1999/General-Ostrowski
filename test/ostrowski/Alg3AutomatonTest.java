package ostrowski;

import org.junit.Test;

import static org.junit.Assert.*;

public class Alg3AutomatonTest {

    @Test
    public void entriesToEncoding() {
        Alg3Automaton test = new Alg3Automaton(new int[]{2,1},0);
        int[] stateEntries = {0,1,
                              0,1,
                              0};
        int expectedEncoding = 14;

        int actualEncoding = test.entriesToEncoding(stateEntries);

        assertEquals(expectedEncoding,actualEncoding);
    }

    @Test
    public void checkFinal() {
        Alg3Automaton test = new Alg3Automaton(new int[]{2,1},0);


    }

    @Test
    public void findTransitionDestination() {
        Alg3Automaton test = new Alg3Automaton(new int[]{2,1},0);
        int[] begin = {0,0,
                       1,0,
                       1};
        int[] transition = {2,
                            0};
        int index = 0;
        int[] expected = {0,0,
                          0,1,
                          0};
        expected = new int[0];

        int[] actualDest = test.findTransitionDestination(begin,transition,index);
//        int actualEncoding = test.entriesToEncoding(actualDest);
//        int expectedEncoding = test.entriesToEncoding(expected);
        assertArrayEquals(expected,actualDest);
//        assertEquals(expectedEncoding,actualEncoding);


    }
}