import java.lang.StringBuilder;
class Alg3 {

    public static String allStatesToString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0;i<81 ; i++) {
            Alg3State temp = new Alg3State(i);
            for(int j=0;j<9;j++) {
                temp.addTransition(j/3,j%3);
            }
            s.append(temp.toString());
        }
        return s.toString();
    }

    //interchange statenumber and entries
    public static int getStateNumber(int[] entries) {Alg3State temp = new Alg3State(entries);return temp.getStateNumber();}
    public static int[] getEntries(int stateNumber) {Alg3State temp = new Alg3State(stateNumber);return temp.getEntries();}

}
