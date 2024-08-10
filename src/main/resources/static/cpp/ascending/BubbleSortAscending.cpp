#include <iostream>
#include <fstream>
#include <vector>
#include <ctime>
#include <cstdlib>

void bubbleSort(std::vector<int>& arr) {
    for (size_t i = 0; i < arr.size() - 1; i++) {
        for (size_t j = 0; j < arr.size() - i - 1; j++) {
            if (arr[j + 1] < arr[j]) {
                std::swap(arr[j], arr[j + 1]);
            }
        }
    }
}

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

int main() {
    int n;
    std::cout << "Enter the size of the array: ";
    std::cin >> n;

    std::vector<int> arr(n);
    std::srand(std::time(nullptr));
    for (int i = 0; i < n; i++) {
        arr[i] = std::rand() % 100 + 1;
    }

    clock_t startTime = clock();

    std::string original = "original_array.csv";
    std::string sort = "sorted_result.csv";

    writeToFile(original, arr);

    bubbleSort(arr);

    writeToFile(sort, arr);

    clock_t endTime = clock();
    double totalTime = static_cast<double>(endTime - startTime) / CLOCKS_PER_SEC;

    std::cout << "Original and sorted arrays have been saved to CSV files." << std::endl;
    std::cout << "Original array file: " << original << std::endl;
    std::cout << "Sorted array file: " << sort << std::endl;
    std::cout << "Time taken: " << totalTime << " seconds." << std::endl;

    return 0;
}
