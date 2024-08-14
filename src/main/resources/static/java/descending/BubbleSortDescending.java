import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BubbleSortDescending {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // System.out.print("Enter the size of the array: ");
        int n = sc.nextInt();

        long startTime = System.currentTimeMillis();

        // Relative path to the static directory
        String basePath = "../../"; // Go up two levels to reach the static directory
        String originalFilePath = basePath + "original_array.csv";
        String sortedFilePath = basePath + "sorted_result.csv";

        List<Integer> arr;

        // Always create a new array based on user input
        arr = new ArrayList<>(n);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr.add(random.nextInt(100) + 1);
        }
        // Write the original array to file
        writeToFile(originalFilePath, arr);

        // Sort the array
        bubbleSort(arr);

        // Write the sorted array to file
        writeToFile(sortedFilePath, arr);

        long endTime = System.currentTimeMillis();
        double totalSeconds = (endTime - startTime) / 1000.0;

        // // Output results with HTML-style links
        // System.out.println("Original array file: <a href=\"" + originalFilePath + "\">original_array.csv</a>");
        // System.out.println("Sorted array file: <a href=\"" + sortedFilePath + "\">sorted_result.csv</a>");
        System.out.println("Time taken: " + totalSeconds + " seconds.");

        sc.close();
    }

    public static void bubbleSort(List<Integer> arr) {
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = 0; j < arr.size() - i - 1; j++) {
                if (arr.get(j + 1) > arr.get(j)) {
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

    public static List<Integer> readFromFile(String filename) {
        List<Integer> arr = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            String[] values = content.split(",");
            for (String value : values) {
                arr.add(Integer.parseInt(value.trim()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
