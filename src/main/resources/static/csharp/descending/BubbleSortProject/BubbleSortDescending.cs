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

        BubbleSort(arr);

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

    // Bubble sort function
    static void BubbleSort(int[] arr)
    {
        for (int i = 0; i < arr.Length - 1; i++)
        {
            for (int j = 0; j < arr.Length - i - 1; j++)
            {
                if (arr[j] < arr[j + 1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
