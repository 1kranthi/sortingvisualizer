#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

// Function to perform Bubble Sort
void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] < arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
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
    printf("Enter the size of the array: ");
    scanf("%d", &n);

    // Define file paths
    const char* originalFile = "../../../static/original_array.csv";
    const char* sortedFile = "../../../static/sorted_result.csv";

    int *arr = NULL;

    // Check if the original array file exists
    FILE *file = fopen(originalFile, "r");
    if (file) {
        // printf("Original array file found. Reading from file...\n");
        fclose(file);
        arr = readFromFile(originalFile, &n);
    } else {
        // printf("Original array file not found. Creating new array...\n");
        arr = (int*)malloc(n * sizeof(int));
        if (arr == NULL) {
            printf("Memory allocation failed\n");
            return 1;
        }

        srand(time(0));
        for (int i = 0; i < n; i++) {
            arr[i] = rand() % 100 + 1;
        }
        writeToFile(originalFile, arr, n);
    }

    clock_t startTime = clock();

    // Sort the array using Bubble Sort
    bubbleSort(arr, n);

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
