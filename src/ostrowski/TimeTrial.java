package ostrowski;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TimeTrial {
    public static void main(String[] unused){
        ArrayList<Integer> start = new ArrayList<>();
        ArrayList<Long> timelist = new ArrayList<>();
        start.add(2);start.add(1);start.add(1);
        long startTime;
        long endTime;
        long totalTime;
        for (int j = 0; j < 10; j++) {
            totalTime = 0;
            for (int i = 0; i < 100; i++) {
                startTime = System.nanoTime();
                run(start.stream().mapToInt(integer -> integer).toArray(), 0);
                endTime = System.nanoTime();
                totalTime += endTime-startTime;
                printScale((j*100+i)/1000.0);
            }
            timelist.add(totalTime/10/1000000);
            start.add(2);start.add(1);
        }
        System.out.println(timelist);
    }
    private static void run(int[] a, int npLength) {
        Alg0Automaton alg0 = new Alg0Automaton(a,npLength);
        Alg1Automaton alg1 = new Alg1Automaton(a,npLength);
        Alg2Automaton alg2 = new Alg2Automaton(a,npLength);
        Alg3Automaton alg3 = new Alg3Automaton(a,npLength);
        RecognitionAutomaton rec = new RecognitionAutomaton(a,npLength);
        alg0.calculateAutomaton();
        alg1.calculateAutomaton();
        alg2.calculateAutomaton();
        alg3.calculateAutomaton();
        rec.calculateAutomaton();
        alg0.toStringBuilder();
        alg1.toStringBuilder();
        alg2.toStringBuilder();
        alg3.toStringBuilder();
        rec.toStringBuilder();
    }
    private static void printScale(double a) {
        if (a == 1) {System.out.println("Finished!"); return;}
        double percentage = a;
        StringBuilder bar = new StringBuilder().append("\rTime Trial Progress: |");
        for (int i = 0; i < percentage*40; i++) {
            bar.append("=");
        }
        for (int i = 0; i < 40-percentage*40; i++) {
            bar.append(" ");
        }
        bar.append("|").append(new DecimalFormat(" #,#0.0 '%'").format(percentage*100));
        System.out.print(bar);
    }
}
