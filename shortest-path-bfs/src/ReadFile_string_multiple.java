import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile_string_multiple {
    public static void main(String[] args) {
        File myObj = new File("src/input2.txt");
        try {
            Scanner myReader = new Scanner(myObj);

            String[] firstMultipleInput = myReader.nextLine().split(" ");

            int caseNum = Integer.parseInt(firstMultipleInput[0]);
            int rowNum = Integer.parseInt(firstMultipleInput[1]);

            List<List<String>> input = new ArrayList<>();

            for (int i = 0; i < caseNum; i++) {
                List<String> curr = new ArrayList<>();

                for (int j = 0; j < rowNum  ; j++) {
                    curr.add(myReader.nextLine());
                }

                input.add(curr);
            }

            System.out.println(input.get(0).get(0));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
