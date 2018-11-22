package ostrowski;

import java.lang.Exception;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class OstrowskiAddition {
    public static void main(String[] args) {
        if (args.length>=1){
            return;
        }
        int[] a = {2};
        int nonRepeatLength = 0;

        Alg0Automaton alg0 = new Alg0Automaton(a);
        Alg1Automaton alg1 = new Alg1Automaton(a,nonRepeatLength);
        Alg2Automaton alg2 = new Alg2Automaton(a,nonRepeatLength);
        Alg3Automaton alg3 = new Alg3Automaton(a,nonRepeatLength);

        BufferedWriter writer = null;
        try{
            if (Files.exists(Paths.get("./output/alg0.txt"))) Files.delete(Paths.get("./output/alg0.txt"));
            if (Files.exists(Paths.get("./output/alg1.txt"))) Files.delete(Paths.get("./output/alg1.txt"));
            if (Files.exists(Paths.get("./output/alg2.txt"))) Files.delete(Paths.get("./output/alg2.txt"));
            if (Files.exists(Paths.get("./output/alg3.txt"))) Files.delete(Paths.get("./output/alg3.txt"));

            writer = Files.newBufferedWriter(Paths.get("./output/alg0.txt"), StandardCharsets.UTF_16);
            writer.append(alg0.toStringBuilder());
            writer.close();

            writer = Files.newBufferedWriter(Paths.get("./output/alg1.txt"), StandardCharsets.UTF_16);
            alg1.calculateAutomaton();
            writer.append(alg1.toStringBuilder());
            writer.close();

            writer = Files.newBufferedWriter(Paths.get("./output/alg2.txt"), StandardCharsets.UTF_16);
            alg2.calculateAutomaton();
            writer.append(alg2.toStringBuilder());
            writer.close();

            writer = Files.newBufferedWriter(Paths.get("./output/alg3.txt"), StandardCharsets.UTF_16);
            alg3.calculateAutomaton();
            writer.append(alg3.toStringBuilder());
            writer.close();

        }
        catch(Exception e){e.printStackTrace();}

    }
}
