package ostrowski;

import java.lang.Exception;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class OstrowskiAddition {
    public static void main(String[] args) {
        if (args.length>=1){
            return;
        }
        String name = "rt2";
        int[] a = {2};
        int nonRepeatLength = 0;

        Alg0Automaton alg0 = new Alg0Automaton(a);
        Alg1Automaton alg1 = new Alg1Automaton(a,nonRepeatLength);
        Alg2Automaton alg2 = new Alg2Automaton(a,nonRepeatLength);
        Alg3Automaton alg3 = new Alg3Automaton(a,nonRepeatLength);

        String directory = "./output/"+name;
        Path path = Paths.get(directory);
        Path alg0Path = Paths.get(directory+"/alg0.txt");
        Path alg1Path = Paths.get(directory+"/alg1.txt");
        Path alg2Path = Paths.get(directory+"/alg2.txt");
        Path alg3Path = Paths.get(directory+"/alg3.txt");
        BufferedWriter writer;
        try{
            if (!Files.exists(path)) Files.createDirectory(path);
            if (Files.exists(alg0Path)) Files.delete(alg0Path);
            if (Files.exists(alg1Path)) Files.delete(alg1Path);
            if (Files.exists(alg2Path)) Files.delete(alg2Path);
            if (Files.exists(alg3Path)) Files.delete(alg3Path);

            writer = Files.newBufferedWriter(alg0Path, StandardCharsets.UTF_16);
            writer.append(alg0.toStringBuilder());
            writer.close();

            writer = Files.newBufferedWriter(alg1Path, StandardCharsets.UTF_16);
            alg1.calculateAutomaton();
            writer.append(alg1.toStringBuilder());
            writer.close();

            writer = Files.newBufferedWriter(alg2Path, StandardCharsets.UTF_16);
            alg2.calculateAutomaton();
            writer.append(alg2.toStringBuilder());
            writer.close();

            writer = Files.newBufferedWriter(alg3Path, StandardCharsets.UTF_16);
            alg3.calculateAutomaton();
            writer.append(alg3.toStringBuilder());
            writer.close();

        }
        catch(Exception e){e.printStackTrace();}

    }
}
