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
                // int[] a = Alg2.getEntries(Integer.valueOf(args[1]));
                System.out.println(Arrays.toString(Alg2.getEntries(Integer.valueOf(args[1]))));
                return;
            }
            if (args[0].equals("-GetStateNumber2")) {
                if (args.length != 5) {System.out.println("must be 4 arguments."); return;}
                System.out.println(Alg2.getStateNumber(new int[] {Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4])}));
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
            writer.append(Alg0.allStatesToString());
            writer.close();
            writer = Files.newBufferedWriter(Paths.get("../output/alg1.txt"), StandardCharsets.UTF_16);
            writer.append(Alg1.allStatesToString());
            writer.close();
            writer = Files.newBufferedWriter(Paths.get("../output/alg2.txt"), StandardCharsets.UTF_16);
            writer.append(Alg2.allStatesToString());
            writer.close();
            writer = Files.newBufferedWriter(Paths.get("../output/alg3.txt"), StandardCharsets.UTF_16);
            writer.append(Alg3.allStatesToString());
            writer.close();

            //
            // if (Files.exists(Paths.get("../output/Alg2.txt"))) {
            //     Files.delete(Paths.get("../output/Alg2.txt"));
            // }
            // Files.createFile(Paths.get("../output/Alg2.txt"));
            // Files.write(Alg2.allStatesToString(),Paths.get("../output/Alg2.txt"), StandardCharsets.UTF_16);
            //
            // if (Files.exists(Paths.get("../output/Alg3.txt"))) {
            //     Files.delete(Paths.get("../output/Alg3.txt"));
            // }
            // Files.createFile(Paths.get("../output/Alg3.txt"));
            // Files.write(Alg3.allStatesToString(), Paths.get("../output/Alg3.txt"), StandardCharsets.UTF_16);
            //
            // if (Files.exists(Paths.get("../output/Alg1.txt"))) {
            //     Files.delete(Paths.get("../output/Alg1.txt"));
            // }
            // Files.createFile(Paths.get("../output/Alg1.txt"));
            // Files.write(Alg1.allStatesToString(), Paths.get("../output/Alg1.txt"), StandardCharsets.UTF_16);
            //
            // if (Files.exists(Paths.get("../output/Alg0.txt"))) {
            //     Files.delete(Paths.get("../output/Alg0.txt"));
            // }
            // Files.createFile(Paths.get("../output/Alg0.txt"));
            // Files.write(Alg0.allStatesToString(), Paths.get("../output/Alg0.txt"), StandardCharsets.UTF_16);
        }
        catch(Exception e){e.printStackTrace();}

    }
}
