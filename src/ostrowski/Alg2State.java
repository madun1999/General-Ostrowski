package ostrowski;

import java.lang.StringBuilder;
import java.util.Hashtable;
class Alg2State {
    //Entries and transitions
    public int a=-1, b=-1, c=-1, d=-1;
    private Hashtable<Integer,Alg2Transition> transitions= new Hashtable<Integer,Alg2Transition>();
    //All states
    private static Hashtable<Integer,Alg2State> states= new Hashtable<Integer,Alg2State>();

    //Constructors
    public Alg2State(int num) {setWithStateNumber(num);}
    // public ostrowski.Alg2State(int aa, int bb, int cc, int dd) {setWithEntries(aa,bb,cc,dd);}
    public Alg2State(int[] entries) {setWithEntries(entries);}

    //getters
    public int getStateNumber() {return 27*a+9*b+3*c+d;}
    public int[] getEntries() {return new int[] {a,b,c,d};}

    //setters
    public void setWithStateNumber(int num) {
        // System.out.println("before1: " + a + " " + b + " " + c + " " + d);
        states.remove(getStateNumber());
        if (num < 0 || num >80) a=b=c=d=-1;
        else {
            a = num / 27;
            b = num % 27 /9;
            c = num % 9 / 3;
            d = num % 3;
        }
        if (valid()) states.put(getStateNumber(),this);
       // System.out.println("after: " + a + " " + b + " " + c + " " + d);
       // System.out.println("number: " + getStateNumber());
    }
    public void setWithEntries(int aa, int bb, int cc, int dd){
        // System.out.println("before2: " + a + " " + b + " " + c + " " + d);
        states.remove(getStateNumber());
        a=b=c=d=-1;
        if (0<=aa && aa<=2) a=aa;
        if (0<=bb && bb<=2) b=bb;
        if (0<=cc && cc<=2) c=cc;
        if (0<=dd && dd<=2) d=dd;
        if (valid()) states.put(getStateNumber(),this);
    }
    public void setWithEntries(int[] entries){
        // System.out.println("before3: " + a + " " + b + " " + c + " " + d);
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
        int e1 = e, f1 = f, a1 = a, b1 = b, c1 = c, d1 = d;
        if (e1<2 && a1==2 && b1>0) {e1++;a1=0;b1--;} //rule for ostrowski.Alg2
        if (b1==d1) {transitions.put(e*3+f,new Alg2Transition(e,f,27*e1+9*a1+3*f1+c1));}
    }

    public boolean isFinal() {
        return (a == c) && (b == d);
    }

    public StringBuilder toStringBuilder() {
        // System.out.println(getStateNumber());
        StringBuilder s = new StringBuilder();
        s.append(getStateNumber());
        s.append(" ");
        s.append(isFinal() ? 1 : 0);
        s.append("\n");
        for (Alg2Transition transition: transitions.values()) s.append(transition.toString());
        return s;
    }

}
