import java.lang.StringBuilder;
import java.util.Hashtable;
class Alg2State {
    //Entries and transitions
    public int a=0, b=0, c=0, d=0;
    private Hashtable<Integer,Alg2Transition> transitions;
    //All states
    private static Hashtable<Integer,Alg2State> states;

    //Constructors
    public Alg2State(int num) {setWithStateNumber(num); if (valid()) states.put(num,this);}
    public Alg2State(int aa, int bb, int cc, int dd) {setWithEntries(aa,bb,cc,dd); if (valid()) states.put(this.getStateNumber(),this);}
    public Alg2State(int[] entries) {setWithEntries(entries); if (valid()) states.put(this.getStateNumber(),this);}
    //getters
    public int getStateNumber() {return 27*a+9*b+3*c+d;}
    public int[] getEntries() {return new int[] {a,b,c,d};}

    //setters
    public void setWithStateNumber(int num) {
        states.remove(getStateNumber());
        if (num < 0 || num >80) a=b=c=d=-1;
        else {
            a = num / 27;
            b = num % 27 /9;
            c = num % 9 / 3;
            d = num % 3;
        }
        if (valid()) states.put(getStateNumber(),this);
    }
    public void setWithEntries(int aa, int bb, int cc, int dd){
        states.remove(getStateNumber());
        a=b=c=d=-1;
        if (0<=aa && aa<=2) a=aa;
        if (0<=bb && bb<=2) b=bb;
        if (0<=cc && cc<=2) c=cc;
        if (0<=dd && dd<=2) d=dd;
        if (valid()) states.put(getStateNumber(),this);
    }
    public void setWithEntries(int[] entries){
        states.remove(getStateNumber());
        a=b=c=d=-1;
        if (entries.length >=1 && 0<=entries[0] && entries[0]<=2) a=entries[0];
        if (entries.length >=2 && 0<=entries[1] && entries[1]<=2) b=entries[1];
        if (entries.length >=3 && 0<=entries[2] && entries[2]<=2) c=entries[2];
        if (entries.length >=4 && 0<=entries[3] && entries[3]<=2) d=entries[3];
        if (valid()) states.put(getStateNumber(),this);
    }
    public boolean valid() {
        return 0<=a && a<=2 && 0<=b && b<=2 && 0<=c && c<=2 && 0<=d && d<=2;
    }
    //toStrings
    public String toEntriesString() {
        return ""+a+", " +b+", "+c+", "+d;
    }
    public String toStateNumberString() {
        return ""+getStateNumber();
    }

    //find and add State
    public static Alg2State findState(int a) {
        return states.get(a);
    }
    public static void addState(Alg2State s) {
        states.put(s.getStateNumber(),s);
    }
    //Verify and add Transition
    public void addTransition(int e, int f) {
        int e1 = e, f1 = f;
	if (a < 2 && b == 2 && e > 0) {a++; b = 0; e --;}
	if (a==c) {transitions.put(e1 * 3 + f1, new Alg2Transition(e1,f1,27*e+9*a+3*f+c))}
    }

    public boolean isFinal() {
        return (a == c) && (b == d);
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getStateNumber());
        s.append(" ");
        s.append(isFinal() ? 1 : 0);
        s.append("\n");
        for (Alg2Transition transition: transitions.values()) s.append(transition.toString());
        return s.toString();
    }

}
