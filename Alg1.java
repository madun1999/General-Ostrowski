import java.lang.StringBuilder;
class Alg1 {

    public static String allStatesToString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0;i<216*27*2 ; i++) {     //6 6 6 3 3 3 2
            Alg1State temp = new Alg1State(i);
            for(int j=0;j<15;j++) {       //5 3
                temp.addTransition(j/3,j%3);
            }
            s.append(temp.toString());
        }
        return s.toString();
    }

    //interchange statenumber and entries
    public static int getStateNumber(int[] entries) {Alg1State temp = new Alg1State(entries);return temp.getStateNumber();}
    public static int[] getEntries(int stateNumber) {Alg1State temp = new Alg1State(stateNumber);return temp.getEntries();}

}
