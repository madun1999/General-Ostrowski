import java.lang.StringBuilder;
class Alg0 {
    private static final int MAX = 2;
    public static StringBuilder allStatesToString() {
        StringBuilder s = new StringBuilder();
        s.append("{0,1,2} {0,1,2} {0,1,2,3,4}");
        s.append("\n");
        s.append("0 1");
        s.append("\n");
        for (int i = 0;i<=MAX ;i++ ) {
            for (int j=0;j<=MAX ;j++ ) {
                s.append("" + i + " " + j + " " + (i+j) + " -> " + "0");
                s.append("\n");
            }
        }
        return s;
    }

    //interchange statenumber and entries
    public static int getStateNumber(int[] entries) {Alg1State temp = new Alg1State(entries);return temp.getStateNumber();}
    public static int[] getEntries(int stateNumber) {Alg1State temp = new Alg1State(stateNumber);return temp.getEntries();}

}
