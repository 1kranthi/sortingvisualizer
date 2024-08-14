import random
import time
import csv
import os

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
        n = int(input(""))  # Get array size from user input
        if n <= 0:
            raise ValueError
    except ValueError:
        print("Array size must be a positive integer.")
        os.sys.exit(1)

    # Navigate two levels up to reach the static directory
    base_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../'))
    original_file = os.path.join(base_path, 'original_array.csv')
    sorted_file = os.path.join(base_path, 'sorted_result.csv')

    # Always create a new array based on user input
    arr = [random.randint(1, 100) for _ in range(n)]
    write_to_file(original_file, arr)

    start_time = time.time()

    bubble_sort(arr)

    write_to_file(sorted_file, arr)

    end_time = time.time()
    total_time = end_time - start_time
    
    # # Output results with HTML-style links
    # print(f'Original array file: <a href="/static/original_array.csv">original_array.csv</a>')
    # print(f'Sorted array file: <a href="/static/sorted_result.csv">sorted_result.csv</a>')
    print(f'Time taken: {total_time:.3f} seconds.')

if __name__ == "__main__":
    main()
