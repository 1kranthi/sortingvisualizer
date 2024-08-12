import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BubbleSortAscending {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size of the array: ");
        int n = sc.nextInt();

        long startTime = System.currentTimeMillis();

        List<Integer> arr = new ArrayList<>(n);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr.add(random.nextInt(100) + 1);
        }

        // File paths in the current directory
        String original = "original_array.csv";
        String sort = "sorted_result.csv";

        // Write original array to file
        writeToFile(original, arr);

        // Sort the array
        bubbleSort(arr);

        // Write sorted array to file
        writeToFile(sort, arr);

        long endTime = System.currentTimeMillis();
        double totalSeconds = (endTime - startTime) / 1000.0;

        System.out.println("Original and sorted arrays have been saved to CSV files.");
        System.out.println("Original array file: " + original);
        System.out.println("Sorted array file: " + sort);
        System.out.println("Time taken: " + totalSeconds + " seconds.");

        sc.close();
    }

    public static void bubbleSort(List<Integer> arr) {
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = 0; j < arr.size() - i - 1; j++) {
                if (arr.get(j + 1) < arr.get(j)) {
                    int temp = arr.get(j + 1);
                    arr.set(j + 1, arr.get(j));
                    arr.set(j, temp);
                }
            }
        }
    }

    public static void writeToFile(String filename, List<Integer> arr) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < arr.size(); i++) {
                writer.append(String.valueOf(arr.get(i)));
                if (i < arr.size() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
