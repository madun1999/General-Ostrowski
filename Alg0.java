import java.lang.StringBuilder;

/**
 * This class is a generator for algorithm 0.
 *
 * Requirement:
 * allStatesToString(int max) should return a StringBuilder representing algorithm 0.
 * For example, for rt2, since the max=2, the returned string should look like:
 *
 * {0,1,2} {0,1,2} {0,1,2,3,4}
 * 0 1
 * 0 0 -> 0
 * 0 1 -> 0
 * 0 2 -> 0
 * 1 0 -> 0
 * 1 1 -> 0
 * 1 2 -> 0
 * 2 0 -> 0
 * 2 1 -> 0
 * 2 2 -> 0
 *
 */
class Alg0 {
    /**
     * Return a StringBuilder representing algorithm 0.
     * Read the class description.
     * @return StringBuilder for the state.
     */
    public static StringBuilder allStatesToString(int max) {
        StringBuilder s = new StringBuilder();
        s.append("{0,1,2} {0,1,2} {0,1,2,3,4}");
        s.append("\n");
        s.append("0 1");
        s.append("\n");
        for (int i = 0;i<=max ;i++ ) {
            for (int j=0;j<=max ;j++ ) {
                s.append(i);
                s.append(" ");
                s.append(j);
                s.append(" ");
                s.append(i+j);
                s.append(" -> 0");
                s.append("\n");
            }
        }
        return s;
    }

}
