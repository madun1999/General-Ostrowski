package ostrowski;

import java.util.Arrays;
import java.lang.Exception;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

class OstrowskiAddition {
    public static void main(String[] args) {
        if (args.length>=1){
            if (args[0].equals("-GetEntries2")) {
                if (args.length != 2) {System.out.println("Need more arguments."); return;}
                // int[] a = ostrowski.Alg2Automaton.getEntries(Integer.valueOf(args[1]));
                System.out.println(Arrays.toString(Alg2Automaton.getEntries(Integer.valueOf(args[1]))));
                return;
            }
            if (args[0].equals("-GetStateNumber2")) {
                if (args.length != 5) {System.out.println("must be 4 arguments."); return;}
                System.out.println(Alg2Automaton.getStateNumber(new int[] {Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4])}));
                return;
            }
            if (args[0].equals("-GetEntries3")) {
                if (args.length != 2) {System.out.println("Need more arguments."); return;}
                // int[] a = ostrowski.Alg2Automaton.getEntries(Integer.valueOf(args[1]));
                System.out.println(Arrays.toString(Alg3.getEntries(Integer.valueOf(args[1]))));
                return;
            }
        }

        BufferedWriter writer = null;
        try{
            if (Files.exists(Paths.get("../output/alg0.txt"))) Files.delete(Paths.get("../output/alg0.txt"));
            if (Files.exists(Paths.get("../output/alg1.txt"))) Files.delete(Paths.get("../output/alg1.txt"));
            if (Files.exists(Paths.get("../output/alg2.txt"))) Files.delete(Paths.get("../output/alg2.txt"));
            if (Files.exists(Paths.get("../output/alg3.txt"))) Files.delete(Paths.get("../output/alg3.txt"));

            writer = Files.newBufferedWriter(Paths.get("../output/alg0.txt"), StandardCharsets.UTF_16);
            writer.append(Alg0Automaton.allStatesToString(2));
            writer.close();
//            writer = Files.newBufferedWriter(Paths.get("../output/alg1.txt"), StandardCharsets.UTF_16);
//            writer.append(new Alg1Automaton(new ArrayList<>().add(2),0));
//            writer.close();
            writer = Files.newBufferedWriter(Paths.get("../output/alg2.txt"), StandardCharsets.UTF_16);
            writer.append(Alg2Automaton.toStringBuilder());
            writer.close();
            writer = Files.newBufferedWriter(Paths.get("../output/alg3.txt"), StandardCharsets.UTF_16);
            writer.append(Alg3.allStatesToString());
            writer.close();

            //
            // if (Files.exists(Paths.get("../output/ostrowski.Alg2Automaton.txt"))) {
            //     Files.delete(Paths.get("../output/ostrowski.Alg2Automaton.txt"));
            // }
            // Files.createFile(Paths.get("../output/ostrowski.Alg2Automaton.txt"));
            // Files.write(ostrowski.Alg2Automaton.toStringBuilder(),Paths.get("../output/ostrowski.Alg2Automaton.txt"), StandardCharsets.UTF_16);
            //
            // if (Files.exists(Paths.get("../output/ostrowski.Alg3.txt"))) {
            //     Files.delete(Paths.get("../output/ostrowski.Alg3.txt"));
            // }
            // Files.createFile(Paths.get("../output/ostrowski.Alg3.txt"));
            // Files.write(ostrowski.Alg3.toStringBuilder(), Paths.get("../output/ostrowski.Alg3.txt"), StandardCharsets.UTF_16);
            //
            // if (Files.exists(Paths.get("../output/ostrowski.Alg1Automaton.txt"))) {
            //     Files.delete(Paths.get("../output/ostrowski.Alg1Automaton.txt"));
            // }
            // Files.createFile(Paths.get("../output/ostrowski.Alg1Automaton.txt"));
            // Files.write(ostrowski.Alg1Automaton.toStringBuilder(), Paths.get("../output/ostrowski.Alg1Automaton.txt"), StandardCharsets.UTF_16);
            //
            // if (Files.exists(Paths.get("../output/ostrowski.Alg0Automaton.txt"))) {
            //     Files.delete(Paths.get("../output/ostrowski.Alg0Automaton.txt"));
            // }
            // Files.createFile(Paths.get("../output/ostrowski.Alg0Automaton.txt"));
            // Files.write(ostrowski.Alg0Automaton.toStringBuilder(), Paths.get("../output/ostrowski.Alg0Automaton.txt"), StandardCharsets.UTF_16);
        }
        catch(Exception e){e.printStackTrace();}

    }
}
