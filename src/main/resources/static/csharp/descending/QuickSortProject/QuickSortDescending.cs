using System;
using System.IO;
using System.Diagnostics;

static class Program
{
    static void Main()
    {
        Console.Write("Enter the size of the array: ");
        int n = int.Parse(Console.ReadLine());

        // Define file paths relative to the "static" directory
        string currentPath = Directory.GetCurrentDirectory();
        string basePath = Path.Combine(currentPath, "../../../../static");
        string originalFile = Path.Combine(basePath, "original_array.csv");
        string sortedFile = Path.Combine(basePath, "sorted_result.csv");

        int[] arr;

        // Check if the original array file exists
        if (File.Exists(originalFile))
        {
            // Console.WriteLine("Original array file found. Reading from file...");
            arr = ReadFromFile(originalFile);
        }
        else
        {
            // Console.WriteLine("Original array file not found. Creating new array...");
            arr = new int[n];
            Random random = new Random();
            for (int i = 0; i < n; i++)
            {
                arr[i] = random.Next(1, 101);
            }
            WriteToFile(originalFile, arr);
        }

        // Start timing
        Stopwatch stopwatch = Stopwatch.StartNew();

        // Perform QuickSort
        QuickSort(arr, 0, arr.Length - 1);

        // Stop timing
        stopwatch.Stop();
        double totalTime = stopwatch.Elapsed.TotalSeconds;

        // Write sorted array to file
        WriteToFile(sortedFile, arr);

        // // Output results with HTML-style links
        // Console.WriteLine($"Original array file: <a href=\"{originalFile}\">original_array.csv</a>");
        // Console.WriteLine($"Sorted array file: <a href=\"{sortedFile}\">sorted_result.csv</a>");
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
