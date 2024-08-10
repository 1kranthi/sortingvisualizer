#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <cstdlib>
#include <ctime>

// Function to read CSV file into a vector
std::vector<int> readCSV(const std::string& filename) {
    std::vector<int> arr;
    std::ifstream file(filename);
    std::string line;

    if (file.is_open()) {
        std::getline(file, line);
        std::stringstream ss(line);
        std::string item;
        while (std::getline(ss, item, ',')) {
            arr.push_back(std::stoi(item));
        }
        file.close();
    } else {
        std::cerr << "Error: Unable to open file " << filename << std::endl;
    }
    return arr;
}

// Function to write vector to CSV file
void writeCSV(const std::string& filename, const std::vector<int>& arr) {
    std::ofstream file(filename);
    if (file.is_open()) {
        for (size_t i = 0; i < arr.size(); ++i) {
            file << arr[i];
            if (i < arr.size() - 1) {
                file << ",";
            }
        }
        file << "\n";
        file.close();
    } else {
        std::cerr << "Error: Unable to open file " << filename << std::endl;
    }
}

class QuickSort {
public:
    void quicksort(std::vector<int>& arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quicksort(arr, low, pivot - 1);
            quicksort(arr, pivot + 1, high);
        }
    }

private:
    int partition(std::vector<int>& arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] >= pivot) {
                i++;
                std::swap(arr[i], arr[j]);
            }
        }
        std::swap(arr[i + 1], arr[high]);
        return i + 1;
    }
};

int main() {
    std::string originalFile = "original_array.csv";
    std::string sortedFile = "sorted_result.csv";

    // Read the original array from the existing CSV file
    std::vector<int> arr = readCSV(originalFile);
    if (arr.empty()) {
        std::cerr << "Error: No data read from the original CSV file." << std::endl;
        return 1;
    }

    // Record start time
    std::clock_t startTime = std::clock();

    // Create QuickSort object and sort the array
    QuickSort sorter;
    sorter.quicksort(arr, 0, arr.size() - 1);

    // Record end time
    std::clock_t endTime = std::clock();
    double duration = (endTime - startTime) / (double)CLOCKS_PER_SEC;

    // Write the sorted array to the existing or a new CSV file
    writeCSV(sortedFile, arr);

    std::cout << "Original and sorted arrays have been saved to CSV files.\n";
    std::cout << "Original array: " << originalFile << "\n";
    std::cout << "Sorted array: " << sortedFile << "\n";
    std::cout << "Time taken: " << duration << " seconds.\n";

    return 0;
}
