class Alg2 {


    


    Alg2State[] states = new Alg2State[81];

    //interchange statenumber and entries
    public static int getStateNumber(int[] entries) {Alg2State temp = new Alg2State(entries);return temp.getStateNumber();}
    public static int[] getEntries(int stateNumber) {Alg2State temp = new Alg2State(stateNumber);return temp.getEntries();}

}
