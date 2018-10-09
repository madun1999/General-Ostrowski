import java.lang.StringBuilder;
class Alg2 {





    public static String allStatesToString() {
        StringBuilder s = new SringBuilder();
    }

    //interchange statenumber and entries
    public static int getStateNumber(int[] entries) {Alg2State temp = new Alg2State(entries);return temp.getStateNumber();}
    public static int[] getEntries(int stateNumber) {Alg2State temp = new Alg2State(stateNumber);return temp.getEntries();}

}
