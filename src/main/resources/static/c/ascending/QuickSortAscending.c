#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void quickSort(int arr[], int low, int high);
int partition(int arr[], int low, int high);
void writeToFile(const char* filename, int arr[], int size);

int main() {
    int n;
    printf("Enter the size of the array: ");
    scanf("%d", &n);

    int* arr = (int*)malloc(n * sizeof(int));
    if (arr == NULL) {
        perror("Unable to allocate memory");
        return EXIT_FAILURE;
    }

    srand((unsigned int)time(NULL));
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 100 + 1;
    }

    clock_t startTime = clock();

    // Define file names
    const char* originalFile = "original_array.csv";
    const char* sortedFile = "sorted_result.csv";

    // Write the original array to file
    writeToFile(originalFile, arr, n);

    // Perform QuickSort
    quickSort(arr, 0, n - 1);

    // Write the sorted array to file
    writeToFile(sortedFile, arr, n);

    clock_t endTime = clock();
    double totalTime = (double)(endTime - startTime) / CLOCKS_PER_SEC;

    printf("Original and sorted arrays have been saved to CSV files.\n");
    printf("Original array file: %s\n", originalFile);
    printf("Sorted array file: %s\n", sortedFile);
    printf("Time taken: %.2f seconds.\n", totalTime);

    free(arr);
    return EXIT_SUCCESS;
}

// QuickSort function
void quickSort(int arr[], int low, int high) {
    if (low < high) {
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }
}

// Partition function for QuickSort
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

// Function to write an array to a CSV file
void writeToFile(const char* filename, int arr[], int size) {
    FILE* file = fopen(filename, "w");
    if (file == NULL) {
        perror("Unable to open file");
        return;
    }

    for (int i = 0; i < size; i++) {
        fprintf(file, "%d", arr[i]);
        if (i < size - 1) {
            fprintf(file, ",");
        }
    }
    fprintf(file, "\n");

    fclose(file);
}
