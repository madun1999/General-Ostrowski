import java.lang.StringBuilder;

public class Recognition {
    public Recognition(int[] bases, int maxBase) {
        BASES = bases;
        MAX_BASE = maxBase;
    }

    //BASES holds the q values for one period of an Ostrowski expansion
    private int[] BASES;
    //MAX_BASE holds the largest q value, for generating alphabet
    private int MAX_BASE;
    //todo add arrays to hold repeating portion of bases and non repeated separately

    //generates automaton text
    public StringBuilder generate() {
        StringBuilder s = new StringBuilder();
        //see notes for overview, 2n+1 states total, n=BASES.length
        int n = BASES.length;
        s.append("{0");
        for (int i = 1; i <= MAX_BASE; i++) {
            s.append(",").append(i);
        }
        s.append("}\n");
        //every number system starts with this behavior
        s.append("0 1\n").append("0 -> 1\n1 -> ").append(n + 1).append("\n");
        //i is index of the a value in BASES + 1
        //loop goes through all bases except the last, since those point to the first pair of states
        for (int i = 1; i < n; i++) {
            s.append(i).append(" 1\n");
            s.append("0 -> ").append(i+1).append("\n");
            for (int k = 1; k <= BASES[i-1]; k++) {
                s.append(k).append(" -> ").append(i + n + 1).append("\n");
            }
            s.append(i + n).append(" 1\n");
            s.append("0 -> ").append(i + 1).append("\n");
            for (int k = 1; k < BASES[i-1]; k++) {
                s.append(k).append(" -> ").append(i + n + 1).append("\n");
            }
        }

        s.append(n).append(" 1\n");
        s.append("0 -> 1\n");
        for (int k = 1; k <= BASES[n-1]; k++) {
            s.append(k).append(" -> ").append(n + 1).append("\n");
        }
        s.append(2*n).append(" 1\n");
        s.append("0 -> 1\n");
        for (int k = 1; k < BASES[n-1]; k++) {
            s.append(k).append(" -> ").append(n + 1).append("\n");
        }

        return s;
    }
}
