package java.ascending;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class BubbleSortAscending {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size of the array: ");
        int n = sc.nextInt();

        long startTime = System.currentTimeMillis();

        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100) + 1;
        }

        String directory = "Sorting_Problems/";
        String original = directory + "original_array.csv";
        String sort = directory + "sorted_result.csv";

        try (FileWriter writer = new FileWriter(original)) {
            for (int i = 0; i < arr.length; i++) {
                writer.append(String.valueOf(arr[i]));
                if (i < arr.length - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        bubbleSort(arr);

        try (FileWriter writer = new FileWriter(sort)) {
            for (int i = 0; i < arr.length; i++) {
                writer.append(String.valueOf(arr[i]));
                if (i < arr.length - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double totalSeconds=totalTime/1000;

        System.out.println("Original and sorted arrays have been saved to CSV files.");
        System.out.println("Original array file: " + original);
        System.out.println("Sorted array file: " + sort);
        System.out.println("Time taken: " + totalSeconds + " seconds.");
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
