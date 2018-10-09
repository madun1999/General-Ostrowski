static class Alg2State {
    //Entries
    public int a=0, b=0, c=0, d=0;
    private  ;
    //Constructors
    public Alg2State(int num) {setWithStateNumber(num);}
    public Alg2State(int aa, int bb, int cc, int dd) {setWithEntries(aa,bb,cc,dd);}
    public Alg2State(int[] entries) {setWithEntries(entries);}
    //getters
    public int getStateNumber() {return 27*a+9*b+3*c+d;}
    public int[] getEntries() {return new int[] {a,b,c,d};}

    //setters
    public void setWithStateNumber(int num) {
        if (num < 0 || num >80) a=b=c=d=-1;
        else {
            a = num / 27;
            b = num % 27 /9;
            c = num % 9 / 3;
            d = num % 3;
        }
    }
    public void setWithEntries(int aa, int bb, int cc, int dd){
        a=b=c=d=-1;
        if (0<=aa && aa<=2) a=aa;
        if (0<=bb && bb<=2) b=bb;
        if (0<=cc && cc<=2) c=cc;
        if (0<=dd && dd<=2) d=dd;
    }
    public void setWithEntries(int[] entries){
        a=b=c=d=-1;
        if (entries.length >=1 && 0<=entries[0] && entries[0]<=2) a=entries[0];
        if (entries.length >=2 && 0<=entries[1] && entries[1]<=2) b=entries[1];
        if (entries.length >=3 && 0<=entries[2] && entries[2]<=2) c=entries[2];
        if (entries.length >=4 && 0<=entries[3] && entries[3]<=2) d=entries[3];
    }

    //toStrings
    public String toEntriesString() {
        return ""+a+", " +b+", "+c+", "+d;
    }
    public String toStateNumberString() {
        return ""+getStateNumber();
    }

}
