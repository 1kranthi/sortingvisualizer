using System;
using System.IO;
using System.Diagnostics;

static class Program
{
    static void Main()
    {
        Console.Write("Enter the size of the array: ");
        int n = int.Parse(Console.ReadLine());

        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++)
        {
            arr[i] = random.Next(1, 101);
        }

        string originalFile = "original_array.csv";
        string sortedFile = "sorted_result.csv";

        // Write original array to file
        using (StreamWriter writer = new StreamWriter(originalFile))
        {
            for (int i = 0; i < arr.Length; i++)
            {
                writer.Write(arr[i]);
                if (i < arr.Length - 1)
                {
                    writer.Write(",");
                }
            }
            writer.WriteLine();
        }

        // Start timing
        Stopwatch stopwatch = Stopwatch.StartNew();

        // Perform QuickSort
        QuickSort(arr, 0, arr.Length - 1);

        // Stop timing
        stopwatch.Stop();
        double totalTime = stopwatch.Elapsed.TotalSeconds;

        // Write sorted array to file
        using (StreamWriter writer = new StreamWriter(sortedFile))
        {
            for (int i = 0; i < arr.Length; i++)
            {
                writer.Write(arr[i]);
                if (i < arr.Length - 1)
                {
                    writer.Write(",");
                }
            }
            writer.WriteLine();
        }

        Console.WriteLine("Original and sorted arrays have been saved to CSV files.");
        Console.WriteLine($"Original array file: {originalFile}");
        Console.WriteLine($"Sorted array file: {sortedFile}");
        Console.WriteLine($"Time taken: {totalTime} seconds.");
    }

    // QuickSort function
    static void QuickSort(int[] arr, int low, int high)
    {
        if (low < high)
        {
            int pivotIndex = Partition(arr, low, high);
            QuickSort(arr, low, pivotIndex - 1);
            QuickSort(arr, pivotIndex + 1, high);
        }
    }

    // Partition function for QuickSort
    static int Partition(int[] arr, int low, int high)
    {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++)
        {
            if (arr[j] >= pivot) // Ascending order
            {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i + 1] and arr[high]
        int temp2 = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp2;

        return i + 1;
    }
}
