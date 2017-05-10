import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Factorial {

    public static int getFactorial(int num) {
        if (num == 0) {
            return 1;
        } else {
            return num * getFactorial(num - 1);
        }
    }

    public static void main(String[] args) {

        String inputFilePath = "factorial-in.txt";

        try {

            FileReader inputFile = new FileReader(inputFilePath);
            BufferedReader inputBr = new BufferedReader(inputFile);
            int n = Integer.parseInt(inputBr.readLine());
            inputBr.close();

            //int factorial = getFactorial(n);

            int factorial = 1;

            for (int i = n; i > 1; i--) {
                factorial *= i;
            }

            PrintWriter writer = new PrintWriter("factorial-out-java.txt", "UTF-8");
            writer.println(factorial);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
