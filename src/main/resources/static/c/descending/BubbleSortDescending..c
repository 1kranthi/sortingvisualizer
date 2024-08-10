#include <stdio.h>
#include <stdlib.h>
#include <time.h>

void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n-1; i++) {
        for (int j = 0; j < n-i-1; j++) {
            if (arr[j] < arr[j+1]) {
                int temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
    }
}

void writeToFile(const char* filename, int arr[], int n) {
    FILE *file = fopen(filename, "w");
    if (file == NULL) {
        printf("Error opening file %s\n", filename);
        return;
    }
    for (int i = 0; i < n; i++) {
        fprintf(file, "%d", arr[i]);
        if (i < n-1) {
            fprintf(file, ",");
        }
    }
    fprintf(file, "\n");
    fclose(file);
}

int main() {
    int n;
    printf("Enter the size of the array: ");
    scanf("%d", &n);

    int *arr = (int*)malloc(n * sizeof(int));
    if (arr == NULL) {
        printf("Memory allocation failed\n");
        return 1;
    }

    srand(time(0));
    for (int i = 0; i < n; i++) {
        arr[i] = rand() % 100 + 1;
    }

    clock_t startTime = clock();

    const char* originalFile = "original_array.csv";
    const char* sortedFile = "sorted_result.csv";

    writeToFile(originalFile, arr, n);

    bubbleSort(arr, n);

    writeToFile(sortedFile, arr, n);

    clock_t endTime = clock();
    double totalTime = (double)(endTime - startTime) / CLOCKS_PER_SEC;

    printf("Original and sorted arrays have been saved to CSV files.\n");
    printf("Original array file: %s\n", originalFile);
    printf("Sorted array file: %s\n", sortedFile);
    printf("Time taken: %.6f seconds.\n", totalTime);

    free(arr);
    return 0;
}
