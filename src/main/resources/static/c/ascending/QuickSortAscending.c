#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

// Function to swap two elements
void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// Partition function used in Quick Sort
int partition(int arr[], int low, int high) {
    int pivot = arr[high]; // Choosing the pivot element
    int i = (low - 1); // Index of the smaller element

    for (int j = low; j <= high - 1; j++) {
        // If current element is smaller than the pivot
        if (arr[j] < pivot) {
            i++; // Increment index of smaller element
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return (i + 1);
}

// Quick Sort function
void quickSort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);

        quickSort(arr, low, pi - 1); // Recursively sort elements before partition
        quickSort(arr, pi + 1, high); // Recursively sort elements after partition
    }
}

// Function to write an array to a CSV file
void writeToFile(const char* filename, int arr[], int n) {
    FILE *file = fopen(filename, "w");
    if (file == NULL) {
        printf("Error opening file %s\n", filename);
        return;
    }
    for (int i = 0; i < n; i++) {
        fprintf(file, "%d", arr[i]);
        if (i < n - 1) {
            fprintf(file, ",");
        }
    }
    fprintf(file, "\n");
    fclose(file);
}

// Function to read an array from a CSV file
int* readFromFile(const char* filename, int* n) {
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        printf("Error opening file %s\n", filename);
        return NULL;
    }

    int *arr = NULL;
    int count = 0, value;
    char buffer[4096];

    if (fgets(buffer, sizeof(buffer), file)) {
        char *token = strtok(buffer, ",");
        while (token != NULL) {
            count++;
            arr = realloc(arr, count * sizeof(int));
            if (arr == NULL) {
                printf("Memory allocation failed\n");
                fclose(file);
                return NULL;
            }
            sscanf(token, "%d", &value);
            arr[count - 1] = value;
            token = strtok(NULL, ",");
        }
    }

    *n = count;
    fclose(file);
    return arr;
}

int main() {
    int n;
    // printf("Enter the size of the array: ");
    scanf("%d", &n);

    // Define file paths
    const char* originalFile = "../../original_array.csv";
    const char* sortedFile = "../../sorted_result.csv";

    // Always create a new array based on user input
    int *arr = (int*)malloc(n * sizeof(int));
    if (arr == NULL) {
        printf("Memory allocation failed\n");
        return 1;
    }

    srand(time(0));
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 100 + 1;
    }
    writeToFile(originalFile, arr, n);

    clock_t startTime = clock();

    // Sort the array using Quick Sort
    quickSort(arr, 0, n - 1);

    // Write the sorted array to file
    writeToFile(sortedFile, arr, n);

    clock_t endTime = clock();
    double totalTime = (double)(endTime - startTime) / CLOCKS_PER_SEC;

    // Output results with file paths
    // printf("Original array file: %s\n", originalFile);
    // printf("Sorted array file: %s\n", sortedFile);
    printf("Time taken: %.6f seconds.\n", totalTime);

    free(arr);
    return 0;
}
