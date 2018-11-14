package ostrowski;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is a generator for algorithm 1.
 *
 * Requirement:
 * allStatesToStringBuilder(int max) should return a StringBuilder representing algorithm 1 according to Walnut's implementation.
 * For example, a final state, with encoding 1, with one transition to 2 where 0 1 is the input, would be:
 * 1 1
 * 0 1 -> 2
 * This function is written in the base class.
 *
 * To make it working, we need to properly set up the following variables and/or override the following functions in the base ostrowski.Automaton class:
 * 1. maxRange
 * 2. states
 * And the following variables and functions for the base State class:
 * 1. encoding
 * 2. isFinal
 * 3. transitions
 * 4. decodeInput() / inputRange (choose one)
 *
 * The other functions in the base class are supplementary, you may use them or not as long as allStatesToStringBuilder(int max) works, i.e. the listed variables and functions are properly set.
 *
 * The functions in the ostrowski.Alg1State file is the old code that I used. It is just a reference and I will not use that Class anymore.
 */
class Alg1 extends Automaton {

    public Alg1() {
        super();
        super.inputSize = 2;
    }

    /**
     * InputEncoder table.
     * If the encoder is the same for all inputs, then this table should contain only one key-value pair.
     * Key is an number associated with the encoders.
     * Values are the encoders for the input.
     */
    HashMap<Integer,ArrayList<Integer>> inputEncoders = new HashMap<>();

    /**
     * StateEncoder table.
     * If the encoder is the same for all states, then this table should contain only one key-value pair.
     * Key is an number associated with the encoders.
     * Values are the encoders for the state.
     */
    HashMap<Integer,ArrayList<Integer>> stateEncoders = new HashMap<>();

    //interchange statenumber and entries, for debugging

    /**
     * Get encoding of a state given entries.
     * For debugging.
     * @param entries the entries of the state
     * @return the encoding of the state
     */
    public static int getStateNumber(int[] entries) {Alg1State temp = new Alg1State(entries);return temp.getStateNumber();}

    /**
     * Get entries of a state given encoding.
     * For debugging.
     * @param stateNumber the encoding of the state
     * @return the entries of the state.
     */
    public static int[] getEntries(int stateNumber) {Alg1State temp = new Alg1State(stateNumber);return temp.getEntries();}

}
