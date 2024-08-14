#include <iostream>
#include <fstream>
#include <vector>
#include <ctime>
#include <cstdlib>
#include <string>
#include <filesystem>

// Function to perform Quick Sort
void quickSort(std::vector<int>& arr, int low, int high) {
    if (low < high) {
        int pivotIndex = low + (high - low) / 2;
        int pivot = arr[pivotIndex];
        int i = low, j = high;

        while (i <= j) {
            while (arr[i] > pivot) {
                i++;
            }
            while (arr[j] < pivot) {
                j--;
            }
            if (i <= j) {
                std::swap(arr[i], arr[j]);
                i++;
                j--;
            }
        }

        // Recursively sort the partitions
        if (low < j) {
            quickSort(arr, low, j);
        }
        if (i < high) {
            quickSort(arr, i, high);
        }
    }
}

// Function to write an array to a CSV file
void writeToFile(const std::string& filename, const std::vector<int>& arr) {
    std::ofstream file(filename);
    for (size_t i = 0; i < arr.size(); i++) {
        file << arr[i];
        if (i < arr.size() - 1) {
            file << ",";
        }
    }
    file << std::endl;
    file.close();
}

// Function to read an array from a CSV file
std::vector<int> readFromFile(const std::string& filename) {
    std::vector<int> arr;
    std::ifstream file(filename);
    std::string line;
    if (std::getline(file, line)) {
        size_t start = 0;
        size_t end = line.find(',');
        while (end != std::string::npos) {
            arr.push_back(std::stoi(line.substr(start, end - start)));
            start = end + 1;
            end = line.find(',', start);
        }
        arr.push_back(std::stoi(line.substr(start, end)));
    }
    return arr;
}

int main() {
    int n;
    // std::cout << "Enter the size of the array: ";
    std::cin >> n;

    // Get the current path and navigate to the static directory
    std::filesystem::path currentPath = std::filesystem::current_path();
    std::filesystem::path basePath = currentPath.parent_path().parent_path().parent_path() / "static";

    std::string originalFile = (basePath / "original_array.csv").string();
    std::string sortedFile = (basePath / "sorted_result.csv").string();

    std::vector<int> arr;

    // Always generate a new array based on user input
    arr.resize(n);
    std::srand(std::time(nullptr));
    for (int i = 0; i < n; i++) {
        arr[i] = std::rand() % 100 + 1;
    }
    writeToFile(originalFile, arr);

    clock_t startTime = clock();

    // Sort the array using Quick Sort
    quickSort(arr, 0, arr.size() - 1);

    // Write the sorted array to file
    writeToFile(sortedFile, arr);

    clock_t endTime = clock();
    double totalTime = static_cast<double>(endTime - startTime) / CLOCKS_PER_SEC;

    // // Output results
    // std::cout << "Original array file: " << originalFile << std::endl;
    // std::cout << "Sorted array file: " << sortedFile << std::endl;
    std::cout << "Time taken: " << totalTime << " seconds." << std::endl;

    return 0;
}
