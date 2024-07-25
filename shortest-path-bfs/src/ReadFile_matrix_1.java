import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile_matrix_1 {
    public static void main(String[] args) {
        File myObj = new File("src/input.txt");
        try {
            Scanner myReader = new Scanner(myObj);

            String[] firstMultipleInput = myReader.nextLine().split(" ");

            int m = Integer.parseInt(firstMultipleInput[0]);
            int n = Integer.parseInt(firstMultipleInput[1]);
            int r = Integer.parseInt(firstMultipleInput[2]);

            List<List<Integer>> matrix = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                String[] matrixRowTempItems = myReader.nextLine().split(" ");

                List<Integer> matrixRowItems = new ArrayList<>();

                for (int j = 0; j < n; j++) {
                    int matrixItem = Integer.parseInt(matrixRowTempItems[j]);
                    matrixRowItems.add(matrixItem);
                }

                matrix.add(matrixRowItems);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
