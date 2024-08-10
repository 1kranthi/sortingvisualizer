import random
import time
import csv

def bubble_sort(arr):
    n = len(arr)
    for i in range(n - 1):
        for j in range(n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]

def write_to_file(filename, arr):
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(arr)

def main():
    n = int(input("Enter the size of the array: "))
    arr = [random.randint(1, 100) for _ in range(n)]

    start_time = time.time()

    original_file = "original_array.csv"
    sorted_file = "sorted_result.csv"

    write_to_file(original_file, arr)

    bubble_sort(arr)

    write_to_file(sorted_file, arr)

    end_time = time.time()
    total_time = end_time - start_time

    print("Original and sorted arrays have been saved to CSV files.")
    print(f"Original array file: {original_file}")
    print(f"Sorted array file: {sorted_file}")
    print(f"Time taken: {total_time} seconds.")

if __name__ == "__main__":
    main()
