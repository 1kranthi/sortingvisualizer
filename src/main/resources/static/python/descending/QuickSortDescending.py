import random
import time
import csv
import os

def quick_sort(arr):
    """Sorts an array in-place using the Quick Sort algorithm."""
    def partition(low, high):
        pivot = arr[high]
        i = low - 1
        for j in range(low, high):
            if arr[j] > pivot:
                i += 1
                arr[i], arr[j] = arr[j], arr[i]
        arr[i + 1], arr[high] = arr[high], arr[i + 1]
        return i + 1

    def quick_sort_recursive(low, high):
        if low < high:
            pi = partition(low, high)
            quick_sort_recursive(low, pi - 1)
            quick_sort_recursive(pi + 1, high)

    quick_sort_recursive(0, len(arr) - 1)

def write_to_file(filename, arr):
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(arr)

def read_from_file(filename):
    if not os.path.exists(filename):
        print(f'File not found: {filename}')
        return []
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        return list(map(int, next(reader)))

def main():
    # Get the array size dynamically from user input
    try:
        n = int(input("Enter the size of the array: "))  # Get array size from user input
        if n <= 0:
            raise ValueError
    except ValueError:
        print("Array size must be a positive integer.")
        os.sys.exit(1)

    # Navigate two levels up to reach the static directory
    base_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../'))
    original_file = os.path.join(base_path, 'original_array.csv')
    sorted_file = os.path.join(base_path, 'sorted_result.csv')

    if os.path.exists(original_file):
        # print('Original array file found. Reading from file...')
        arr = read_from_file(original_file)
    else:
        # print('Original array file not found. Creating new array...')
        arr = [random.randint(1, 100) for _ in range(n)]
        write_to_file(original_file, arr)

    start_time = time.time()

    quick_sort(arr)

    write_to_file(sorted_file, arr)

    end_time = time.time()
    total_time = end_time - start_time
    
    # # Output results with HTML-style links
    # print(f'Original array file: <a href="/static/original_array.csv">original_array.csv</a>')
    # print(f'Sorted array file: <a href="/static/sorted_result.csv">sorted_result.csv</a>')
    print(f'Time taken: {total_time:.3f} seconds.')

if __name__ == "__main__":
    main()
