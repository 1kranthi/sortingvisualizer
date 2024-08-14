using System;
using System.IO;
using System.Diagnostics;

static class Program
{
    static void Main()
    {
        // Console.Write("Enter the size of the array: ");
        int n = int.Parse(Console.ReadLine());

        // Define file paths relative to the "static" directory
        string currentPath = Directory.GetCurrentDirectory();
        string basePath = Path.Combine(currentPath, "../../../../static");
        string originalFile = Path.Combine(basePath, "original_array.csv");
        string sortedFile = Path.Combine(basePath, "sorted_result.csv");

        int[] arr;

        // Always create a new array based on user input
        arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++)
        {
            arr[i] = random.Next(1, 101);
        }
        WriteToFile(originalFile, arr);

        // Start timing
        Stopwatch stopwatch = Stopwatch.StartNew();

        // Sort the array using Bubble Sort
        BubbleSort(arr);

        // Stop timing
        stopwatch.Stop();
        double totalTime = stopwatch.Elapsed.TotalSeconds;

        // Write the sorted array to file
        WriteToFile(sortedFile, arr);

        // // Output results with HTML-style links
        // Console.WriteLine($"Original array file: <a href=\"/static/original_array.csv\">original_array.csv</a>");
        // Console.WriteLine($"Sorted array file: <a href=\"/static/sorted_result.csv\">sorted_result.csv</a>");
        Console.WriteLine($"Time taken: {totalTime} seconds.");
    }

    // Bubble sort function
    static void BubbleSort(int[] arr)
    {
        for (int i = 0; i < arr.Length - 1; i++)
        {
            for (int j = 0; j < arr.Length - i - 1; j++)
            {
                if (arr[j] > arr[j + 1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Function to write an array to a CSV file
    static void WriteToFile(string filename, int[] arr)
    {
        using (StreamWriter writer = new StreamWriter(filename))
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
    }

    // Function to read an array from a CSV file
    static int[] ReadFromFile(string filename)
    {
        string[] lines = File.ReadAllLines(filename);
        string[] items = lines[0].Split(',');

        int[] arr = new int[items.Length];
        for (int i = 0; i < items.Length; i++)
        {
            arr[i] = int.Parse(items[i]);
        }
        return arr;
    }
}
