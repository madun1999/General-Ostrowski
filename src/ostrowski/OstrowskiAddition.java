package ostrowski;

import java.io.IOException;
import java.lang.Exception;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

public class OstrowskiAddition {
    public static void main(String[] args) {

        String inputFileName = "input.txt";
        String name = "rt3_2";
        int[] a = {2,1};
        int nonRepeatLength = 0;
        int[] aRepeat = {2,1};
        int[] aNRepeat = {};

        try (Stream<String> stream = Files.lines(Paths.get(inputFileName))){
            String[] lines = stream.toArray(String[]::new);
            if (lines.length >= 3) {
                name = lines[0];
                if (lines[1].trim().length() == 0){
                   aNRepeat = new int[0];
                } else {
                    aNRepeat = Arrays.stream(lines[1].split("\\s+")).mapToInt(Integer::parseInt).toArray();
                }
                aRepeat = Arrays.stream(lines[2].split("\\s+")).mapToInt(Integer::parseInt).toArray();
                nonRepeatLength = aNRepeat.length;
                a = new int[aNRepeat.length + aRepeat.length];
                System.arraycopy(aNRepeat, 0, a, 0, aNRepeat.length);
                System.arraycopy(aRepeat, 0, a, aNRepeat.length, aRepeat.length);
                stream.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }



        Alg0Automaton alg0 = new Alg0Automaton(a);
        Alg1Automaton alg1 = new Alg1Automaton(a,nonRepeatLength);
        Alg2Automaton alg2 = new Alg2Automaton(a,nonRepeatLength);
        Alg3Automaton alg3 = new Alg3Automaton(a,nonRepeatLength);
        Recognition rec = new Recognition(aNRepeat,aRepeat);

        String directory = "./output/"+name;
        Path path = Paths.get(directory);
        Path alg0Path = Paths.get(directory+"/"+name+"Alg0.txt");
        Path alg1Path = Paths.get(directory+"/"+name+"Alg1.txt");
        Path alg2Path = Paths.get(directory+"/"+name+"Alg2.txt");
        Path alg3Path = Paths.get(directory+"/"+name+"Alg3.txt");
        Path recognitionPath = Paths.get(directory + "/" + name + ".txt");
        Path commandPath = Paths.get(directory +"/" + name + "AdditionCommand.txt");

        BufferedWriter writer;
        try{
            if (!Files.exists(path)) Files.createDirectory(path);
            if (Files.exists(alg0Path)) Files.delete(alg0Path);
            if (Files.exists(alg1Path)) Files.delete(alg1Path);
            if (Files.exists(alg2Path)) Files.delete(alg2Path);
            if (Files.exists(alg3Path)) Files.delete(alg3Path);
            if (Files.exists(recognitionPath)) Files.delete(recognitionPath);
            if (Files.exists(commandPath)) Files.delete(commandPath);

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

            writer = Files.newBufferedWriter(recognitionPath, StandardCharsets.UTF_16);
            writer.append(rec.generate());
            writer.close();

            writer = Files.newBufferedWriter(commandPath, StandardCharsets.UTF_16);
            writer.append(buildCommand(name));
            writer.close();

        }
        catch(Exception e){e.printStackTrace();}

    }

    private static String buildCommand(String name) {
        return "eval lsd_"
                + name
                + "_addition \"Ey (Ex (Ew $"
                + name
                + "(a) & $"
                + name
                + "(b) & $"
                + name
                + "Alg0(a,b,w) & `$"
                + name
                + "Alg1(w,x)) & $"
                + name
                + "Alg2(x,y)) & `$"
                + name
                +"Alg3(y,c)\":";
    }

}
