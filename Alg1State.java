import java.lang.StringBuilder;
import java.util.Hashtable;
class Alg2State {
    private static final int TOTAL = 216*27*2;
    private static final int COA = 36*27*2;
    private static final int COB = 6*27*2;
    private static final int COC = 54;
    private static final int COD = 18;
    private static final int COE = 6;
    private static final int COF = 2;
    //Entries and transitions
    public int a=-1, b=-1, c=-1, d=-1, e=-1, f=-1, g=-1;
    private Hashtable<Integer,Alg2Transition> transitions= new Hashtable<Integer,Alg2Transition>();
    //All states
    private static Hashtable<Integer,Alg2State> states= new Hashtable<Integer,Alg2State>();

    //Constructors
    public Alg2State(int num) {setWithStateNumber(num);}
    // public Alg2State(int aa, int bb, int cc, int dd) {setWithEntries(aa,bb,cc,dd);}
    public Alg2State(int[] entries) {setWithEntries(entries);}

    //getters
    public int getStateNumber() {return COA*a+COB*b+COC*c+COD*d+COE*e+COF*f+g;}
    public int[] getEntries() {return new int[] {a,b,c,d};}

    //setters
    public void setWithStateNumber(int num) {
        // System.out.println("before1: " + a + " " + b + " " + c + " " + d);
        states.remove(getStateNumber());
        if (num < 0 || num >= TOTAL) a=b=c=d=-1;
        else {
            a = num / COA;
            b = num % COA / COB;
            c = num % COB / COC;
            d = num % COC / COD;
            e = num % COD / COE;
            f = num % COE / COF;
            f = num % COF;
        }
        if (valid()) states.put(getStateNumber(),this);
       // System.out.println("after: " + a + " " + b + " " + c + " " + d);
       // System.out.println("number: " + getStateNumber());
    }
    public void setWithEntries(int aa, int bb, int cc, int dd, int ee, int ff, int gg){
        // System.out.println("before2: " + a + " " + b + " " + c + " " + d);
        states.remove(getStateNumber());
        a=b=c=d=-1;
        if (0<=aa && aa<=5) a=aa;
        if (0<=bb && bb<=5) b=bb;
        if (0<=cc && cc<=5) c=cc;
        if (0<=dd && dd<=2) d=dd;
        if (0<=ee && ee<=2) e=ee;
        if (0<=ff && ff<=2) f=ff;
        if (0<=gg && gg<=1) g=gg;
        if (valid()) states.put(getStateNumber(),this);
    }
    public void setWithEntries(int[] entries){
        // System.out.println("before3: " + a + " " + b + " " + c + " " + d);
        states.remove(getStateNumber());
        a=b=c=d=-1;
        if (entries.length >=1 && 0<=entries[0] && entries[0]<=5) a=entries[0];
        if (entries.length >=2 && 0<=entries[1] && entries[1]<=5) b=entries[1];
        if (entries.length >=3 && 0<=entries[2] && entries[2]<=5) c=entries[2];
        if (entries.length >=4 && 0<=entries[3] && entries[3]<=2) d=entries[3];
        if (entries.length >=5 && 0<=entries[4] && entries[4]<=2) d=entries[4];
        if (entries.length >=6 && 0<=entries[5] && entries[5]<=2) d=entries[5];
        if (entries.length >=7 && 0<=entries[6] && entries[6]<=1) d=entries[6];
        if (valid()) states.put(getStateNumber(),this);
    }
    public boolean valid() {
        return 0<=a && a<=5 && 0<=b && b<=5 && 0<=c && c<=5 && 0<=d && d<=2 && 0<=e && e<=2 && 0<=f && f<=2 && 0<=g && g<=1;
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
    public void addTransition(int h, int i) {
        int a1 = a, b1 = b, c1 = c, d1 = d, e1 = e, f1 = f, g1 = g, h1 = h, i1 = i;
        if (b1<2 && c1>2 && (d1+g1) == 0) {b1++;c1-=3;d1=1;g1=1;}  //A1
        else if (b1<2 && c1>=2 && c1<=4 && (d1+g1)>0) {b1++;c1-=2;d1-=1;g1=0} //A2
        if (a1==d1) {transitions.put(h*5+i,new Alg2Transition(h,i,COA*b1+COB*c1+COC*h1+COD*e1+COE*f1+COF*i1+g1));}
    }

    public boolean isFinal() {
        return (a == c) && (b == d);
    }
    public String toString() {
        // System.out.println(getStateNumber());
        StringBuilder s = new StringBuilder();
        s.append(getStateNumber());
        s.append(" ");
        s.append(isFinal() ? 1 : 0);
        s.append("\n");
        for (Alg2Transition transition: transitions.values()) s.append(transition.toString());
        return s.toString();
    }

}
