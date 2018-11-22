package ostrowski;
import java.lang.StringBuilder;

public class Recognition {

    //NON_REP_BASES holds the a values for non-repeating part of continued fraction expansion
    private int[] NON_REP_BASES;
    //REP_BASES holds the a values for one period of continued fraction expansion
    private int[] REP_BASES;
    //MAX_BASE holds the largest a value, for generating alphabet
    private int MAX_BASE;

    public Recognition(int[] nonRepBases, int[] repBases) {
        NON_REP_BASES = nonRepBases;
        REP_BASES = repBases;
        int max = 0;
        for (int a : nonRepBases)
            if (a > max)
                max = a;
        for (int a : repBases)
            if (a > max)
                max = a;
        MAX_BASE = max;
    }

    //generates automaton text
    public StringBuilder generate() {
        StringBuilder s = new StringBuilder();
        //see notes for overview, 2n+1 states total, n = total number of base values
        int start = NON_REP_BASES.length + 1;
        int n = REP_BASES.length + start - 1;
        s.append("{0");
        for (int i = 1; i <= MAX_BASE; i++) {
            s.append(",").append(i);
        }
        s.append("}\n");
        //every number system starts with this behavior
        s.append("0 1\n").append("0 -> 1\n1 -> ").append(n + 1).append("\n");
        //i is index of the a value in REP_BASES + 1

        //non-repeating part of automaton
        for (int i = 1; i < start; i++) {
            s.append(i).append(" 1\n");
            s.append("0 -> ").append(i+1).append("\n");
            for (int k = 1; k < NON_REP_BASES[i-1]; k++) {
                s.append(k).append(" -> ").append(i+n+1).append("\n");
            }
            s.append(i+n).append(" 1\n");
            s.append("0 -> ").append(i+1).append("\n");
            for (int k = 1; k < NON_REP_BASES[i-1]; k++) {
                s.append(k).append(" -> ").append(i+n+1).append("\n");
            }
        }

        //repeating part of automaton
        //loop goes through all bases except the last, since those point to the first pair of states
        for (int i = start; i < n; i++) {
            s.append(i).append(" 1\n");
            s.append("0 -> ").append(i+1).append("\n");
            for (int k = 1; k <= REP_BASES[i-start]; k++) {
                s.append(k).append(" -> ").append(i + n + 1).append("\n");
            }
            s.append(i + n).append(" 1\n");
            s.append("0 -> ").append(i + 1).append("\n");
            for (int k = 1; k < REP_BASES[i-start]; k++) {
                s.append(k).append(" -> ").append(i + n + 1).append("\n");
            }
        }

        s.append(n).append(" 1\n");
        s.append("0 -> ").append(start).append("\n");
        for (int k = 1; k <= REP_BASES[REP_BASES.length-1]; k++) {
            s.append(k).append(" -> ").append(n + start).append("\n");
        }
        s.append(2*n).append(" 1\n");
        s.append("0 -> ").append(start).append("\n");
        for (int k = 1; k < REP_BASES[REP_BASES.length-1]; k++) {
            s.append(k).append(" -> ").append(n + start).append("\n");
        }

        return s;
    }
}
