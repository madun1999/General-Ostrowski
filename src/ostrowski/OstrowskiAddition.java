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

/**
 * Driver class, calculates automaton.
 *
 * Reads input from "input.txt".
 *
 * "input.txt" should contain three lines:
 * First line is the name of the numeration system.
 * Second line is the nonRepeated part of the continued fraction, separated by space.
 * Third line is the Repeated part of the continued fraction, separated by space, must not be empty.
 *
 * outputs can be found in "output/[name of numeration system]/".
 * This folder contains six files.
 * "[name].txt" is the recognition automaton.
 * "[name]Alg0.txt" - "[name]Alg3.txt" is the three parts of addition automaton.
 * "[name]AdditionCommand contains the command to be used in Walnut to produce the addition automaton."
 */
public class OstrowskiAddition {
    /**
     * main method
     * @param args First argument is the name of the input file. Default "input.txt".
     */
    public static void main(String[] args) {
        String inputFileName = "input.txt";
        boolean verifyMode = false;
        if (args.length == 0) {
            System.out.println("No input file name given.");
        }
        else if (args[0].startsWith("-")) {
            if (args[0].equals("-verify")) {
                verifyMode = true;
                if (args.length >= 2) {
                    inputFileName = args[0].trim();
                } else {
                    System.out.println("No input file name given.");
                }
            } else {
                System.out.println("No such option: " + args[0]);
                System.exit(0);
            }
        } else {
            inputFileName = args[0].trim();
        }

        if (verifyMode) System.out.println("Verify mode.");
        System.out.println();

        String name = "rt3";
        int[] a = {1,2};
        int nonRepeatLength = 0;
        int[] aRepeat = {1,2};
        int[] aNRepeat = {};

        System.out.println("Try reading input from " + inputFileName + " ...\n");
        try (Stream<String> stream = Files.lines(Paths.get(inputFileName))){
            String[] lines = stream.toArray(String[]::new);
            if (lines.length >= 3) {
                name = lines[0];
                if (lines[1].trim().length() == 0){
                   aNRepeat = new int[0];
                } else {
                    aNRepeat = Arrays.stream(lines[1].split("\\s+")).mapToInt(Integer::parseInt).toArray();
                }
                if (lines[2].trim().length() == 0) {
                    System.out.println("Input Error: Repeated part of the continued fraction must not be empty.");
                    System.exit(0);
                }
                aRepeat = Arrays.stream(lines[2].split("\\s+")).mapToInt(Integer::parseInt).toArray();
                nonRepeatLength = aNRepeat.length;
                a = new int[aNRepeat.length + aRepeat.length];
                System.arraycopy(aNRepeat, 0, a, 0, aNRepeat.length);
                System.arraycopy(aRepeat, 0, a, aNRepeat.length, aRepeat.length);
                stream.close();
            }
        } catch (IOException e) {
            System.out.println("File " + inputFileName + " is not found. Shutting down...");
            System.exit(1);
        }



        Alg0Automaton alg0 = new Alg0Automaton(a,nonRepeatLength);
        Alg1Automaton alg1 = new Alg1Automaton(a,nonRepeatLength);
        Alg2Automaton alg2 = new Alg2Automaton(a,nonRepeatLength);
        Alg3Automaton alg3 = new Alg3Automaton(a,nonRepeatLength);
        RecognitionAutomaton rec = new RecognitionAutomaton(a,nonRepeatLength);

        String directory = System.getProperty("user.dir") + "/output/" + name;
        Path path = Paths.get(directory);
        Path alg0Path = Paths.get(directory+"/"+name+"Alg0.txt");
        Path alg1Path = Paths.get(directory+"/"+name+"Alg1.txt");
        Path alg2Path = Paths.get(directory+"/"+name+"Alg2.txt");
        Path alg3Path = Paths.get(directory+"/"+name+"Alg3.txt");
        Path recognitionPath = Paths.get(directory + "/lsd_" + name + ".txt");
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
            System.out.println("Alg0 written to "+alg0Path.toString());
            System.out.println();

            writer = Files.newBufferedWriter(alg1Path, StandardCharsets.UTF_16);
            alg1.calculateAutomaton();
            writer.append(alg1.toStringBuilder());
            writer.close();
            System.out.println("Alg1 written to "+alg1Path.toString());
            System.out.println();

            writer = Files.newBufferedWriter(alg2Path, StandardCharsets.UTF_16);
            alg2.calculateAutomaton();
            writer.append(alg2.toStringBuilder());
            writer.close();
            System.out.println("Alg2 written to "+alg2Path.toString());
            System.out.println();

            writer = Files.newBufferedWriter(alg3Path, StandardCharsets.UTF_16);
            alg3.calculateAutomaton();
            writer.append(alg3.toStringBuilder());
            writer.close();
            System.out.println("Alg3 written to "+alg3Path.toString());
            System.out.println();

            writer = Files.newBufferedWriter(recognitionPath, StandardCharsets.UTF_16);
            rec.calculateAutomaton();
            writer.append(rec.toStringBuilder());
            writer.close();
            System.out.println("RecognitionAutomaton written to "+recognitionPath.toString());
            System.out.println();

            writer = Files.newBufferedWriter(commandPath, StandardCharsets.UTF_16);
            writer.append(buildCommand(name, verifyMode));
            writer.close();
            System.out.println("Walnut command written to "+commandPath.toString());
            System.out.println();

        }
        catch(Exception e){e.printStackTrace();}

    }

    /**
     * Helper function to calculate the command for Walnut.
     * @param name the name of the numeration system.
     * @return the command for Walnut.
     */
    private static String buildCommand(String name, boolean verifyMode) {
        String verify = "";
        if (verifyMode) {
            verify += "eval verify_testAdd0 \"Ax Ay Az (($lsd_test(x) & $lsd_test(y) & $lsd_test_addition(x,y,z)) => $lsd_test(z))\":\n" +
                    "eval verify_testAdd1 \"Ax,y($endIn3Zeros(x) & $endIn3Zeros(y) & $lsd_test(x) & $lsd_test(y) => (Ez $lsd_test_addition(x,y,z)))\":\n" +
                    "eval verify_testAdd2 \"Ax,y,z,w $lsd_test_addition(x,y,z) & $lsd_test_addition(x,y,w) => $same(z,w)\":\n" +
                    "eval verify_testAdd3 \"Ax,y,z,r,s,t $lsd_test_addition(x,y,r)&$lsd_test_addition(r,z,t)&$lsd_test_addition(y,z,s) => $lsd_test_addition(x,s,t)\":\n" +
                    "eval verify_testAdd4 \"Ax,y,z $zero(y) => ($lsd_test_addition(x,y,z) <=> ($same(x,z) & $lsd_test(x)))\":\n" +
                    "eval verify_testAdd5 \"Ax,y,o $one(o) & $endIn3Zeros(x) & $lsd_test_addition(x,o,y) => ($less(x,y) & ~(Ez $lsd_test(z) & $less(x,z) & $less(z,y)))\":\n" +
                    "\n" +
                    "exit;";
            verify = verify.replaceAll("test",name);
        }
        return "eval lsd_"
                + name
                + "_addition \"Ey (Ex (Ew $lsd_"
                + name
                + "(a) & $lsd_"
                + name
                + "(b) & $"
                + name
                + "Alg0(a,b,w) & `$"
                + name
                + "Alg1(w,x)) & $"
                + name
                + "Alg2(x,y)) & `$"
                + name
                + "Alg3(y,c)\":"
                + verify;
    }

}
