import java.lang.StringBuilder;
class Alg2 {

    public static String allStatesToString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0;i<81 ; i++) {
            Alg2State temp = new Alg2State(i);
            for(int j=0;j<9;j++) {
                temp.addTransition(j/3,j%3);
            }
            s.append(temp.toString());
        }
        return s.toString();
    }

    //interchange statenumber and entries
    public static int getStateNumber(int[] entries) {Alg2State temp = new Alg2State(entries);return temp.getStateNumber();}
    public static int[] getEntries(int stateNumber) {Alg2State temp = new Alg2State(stateNumber);return temp.getEntries();}

}
