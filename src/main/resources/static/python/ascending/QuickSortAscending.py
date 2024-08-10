import csv
import random
import time

def quick_sort(arr, low, high):
    if low < high:
        pivot_index = partition(arr, low, high)
        quick_sort(arr, low, pivot_index - 1)
        quick_sort(arr, pivot_index + 1, high)

def partition(arr, low, high):
    pivot = arr[high]
    i = low - 1
    for j in range(low, high):
        if arr[j] <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

def write_to_file(filename, arr):
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(arr)

def main():
    n = int(input("Enter the size of the array: "))
    
    arr = [random.randint(1, 100) for _ in range(n)]

    start_time = time.time()

    # Define file names
    original_file = 'original_array.csv'
    sorted_file = 'sorted_result.csv'

    # Write the original array to file
    write_to_file(original_file, arr)

    # Perform QuickSort
    quick_sort(arr, 0, len(arr) - 1)

    # Write the sorted array to file
    write_to_file(sorted_file, arr)

    end_time = time.time()
    total_time = end_time - start_time

    print(f"Original and sorted arrays have been saved to CSV files.")
    print(f"Original array file: {original_file}")
    print(f"Sorted array file: {sorted_file}")
    print(f"Time taken: {total_time:.2f} seconds.")

if __name__ == "__main__":
    main()
