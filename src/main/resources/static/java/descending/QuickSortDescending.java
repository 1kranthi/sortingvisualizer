package java.descending;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class QuickSortDescending {

    int partition(int arr[], int low, int high) {
        int pivot = arr[high];
        int i = low - 1; 

        for (int j = low; j < high; j++) {
            
            if (arr[j] >= pivot) {
                i++;
            
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    void quicksort(int arr[], int low, int high) {
        if (low < high) {
            
            int pivot = partition(arr, low, high);

            quicksort(arr, low, pivot - 1);
            quicksort(arr, pivot + 1, high);
        }
    }

    public static void main(String[] args) {
        QuickDescendingJava obj = new QuickDescendingJava();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the size of the array: ");
        int k = sc.nextInt();

        int arr[] = new int[k];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100) + 1;
        }

        long startTime = System.currentTimeMillis();

        String directory = "Sorting_Problems/";
        String originalFile = directory + "original_array.csv";
        String sortedFile = directory + "sorted_result.csv";

        try (FileWriter writer = new FileWriter(originalFile)) {
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

        obj.quicksort(arr, 0, arr.length - 1);

        try (FileWriter writer = new FileWriter(sortedFile)) {
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

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double seconds = totalTime / 1000.0; 

        System.out.println("Original and sorted arrays have been saved to CSV files.");
        System.out.println("Original array: " + originalFile);
        System.out.println("Sorted array: " + sortedFile);
        System.out.println("Time taken: " + seconds + " seconds.");

        sc.close();
    }
}
