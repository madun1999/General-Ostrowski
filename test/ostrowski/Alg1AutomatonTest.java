package ostrowski;

import org.junit.Test;

import static org.junit.Assert.*;

public class Alg1AutomatonTest {

    @Test
    public void entriesToEncoding() {
        Alg1Automaton fib = new Alg1Automaton(new int[]{1},0);
        //Alg1Automaton rt2 = new Alg1Automaton(new int[]{2},0);
        int[] stateEntries = {0,0,0,
                              0,0,0,
                              0,0};
        int expectedEncoding = 18;

        int actualEncoding = fib.entriesToEncoding(stateEntries);

        //assertEquals(expectedEncoding,actualEncoding);
    }

    @Test
    public void checkFinal() {
        Alg1Automaton fib = new Alg1Automaton(new int[]{1},0);
        Alg1Automaton test = new Alg1Automaton(new int[]{2,1},0);
        int[] state = {0,0,2,
                       0,1,0,
                       0,0};

        boolean expectedFinal = false;
        boolean actualFinal = test.checkFinal(state);

        assertEquals(expectedFinal,actualFinal);
    }

    @Test
    public void findTransitionDestination() {

        Alg1Automaton rt2 = new Alg1Automaton(new int[]{2},0);
        Alg1Automaton test = new Alg1Automaton(new int[]{2,1},0);
        int[] begin = {0,0,0,
                       0,0,1,
                       0,0};
        int[] transition = {2,
                            0};
        int index = 1;
        int[] expected = {0,0,2,
                          0,1,0,
                          0,1};

        int[] actualDest = test.findTransitionDestination(begin,transition,index);
        int actualEncoding = test.entriesToEncoding(actualDest);
        int expectedEncoding = test.entriesToEncoding(expected);
        assertArrayEquals(expected,actualDest);
        assertEquals(expectedEncoding,actualEncoding);

        int[] odd = {0,1,3,
                     0,0,2,
                     0,0};
        assertNotEquals(rt2.entriesToEncoding(odd),actualEncoding);

    }
}