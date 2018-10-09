import java.util.Arrays;
import java.lang.Exception;
import java.nio.file.Files;
import java.nio.file.Paths;
class OstrowskiAddition {
    public static void main(String[] args) {
        if (args.length>=1){
            if (args[0].equals("-GetEntries")) {
                if (args.length != 2) {System.out.println("Need more arguments."); return;}
                // int[] a = Alg2.getEntries(Integer.valueOf(args[1]));
                System.out.println(Arrays.toString(Alg2.getEntries(Integer.valueOf(args[1]))));
                return;
            }
            if (args[0].equals("-GetStateNumber")) {
                if (args.length != 5) {System.out.println("must be 4 arguments."); return;}
                System.out.println(Alg2.getStateNumber(new int[] {Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4])}));
                return;
            }

        }

        try{

            if (Files.exists(Paths.get("../output/Alg2.txt"))) {
                Files.delete(Paths.get("../output/Alg2.txt"));
            }
            Files.createFile(Paths.get("../output/Alg2.txt"));
            Files.write(Paths.get("../output/Alg2.txt"), Alg2.allStatesToString().getBytes());
        }
        catch(Exception e){e.printStackTrace();}
    }
}
