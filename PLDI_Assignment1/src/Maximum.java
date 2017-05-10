import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Maximum {

    public static void main(String[] args) {

        String inputFilePath = "int-list-in.txt";

        try {

            FileReader inputFile = new FileReader(inputFilePath);
            BufferedReader inputBr = new BufferedReader(inputFile);
            int n = Integer.parseInt(inputBr.readLine());

            int[] numbers = new int[n];

            for (int i = 0; i < n; i++) {
                numbers[i] = Integer.parseInt(inputBr.readLine());
            }
            inputBr.close();

            int max = numbers[0];
            for (int i = 1; i < n; i++) {
                if (numbers[i] > max) {
                    max = numbers[i];
                }
            }

            PrintWriter writer = new PrintWriter("maximum-out-java.txt", "UTF-8");
            writer.println(max);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
