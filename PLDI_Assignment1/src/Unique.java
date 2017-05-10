import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Unique {
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

            ArrayList<Integer> uniqueNumbers = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (!uniqueNumbers.contains(numbers[i])) {
                    uniqueNumbers.add(numbers[i]);
                }
            }

            PrintWriter writer = new PrintWriter("unique-out-java.txt", "UTF-8");
            int numberOfUniqueNumbers = uniqueNumbers.size();
            for (int i = 0; i < numberOfUniqueNumbers; i++) {
                writer.println(uniqueNumbers.get(i));
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
