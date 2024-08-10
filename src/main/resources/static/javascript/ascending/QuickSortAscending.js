const fs = require('fs');
const readline = require('readline');

// QuickSort function
function quickSort(arr, low, high) {
    if (low < high) {
        const pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }
}

// Partition function for QuickSort
function partition(arr, low, high) {
    const pivot = arr[high];
    let i = low - 1;

    for (let j = low; j < high; j++) {
        if (arr[j] <= pivot) { // Ascending order
            i++;
            [arr[i], arr[j]] = [arr[j], arr[i]];
        }
    }
    [arr[i + 1], arr[high]] = [arr[high], arr[i + 1]];

    return i + 1;
}

// Function to write an array to a CSV file
function writeToFile(filename, arr) {
    const data = arr.join(',') + '\n';
    fs.writeFileSync(filename, data);
}

// Function to get user input
function getUserInput(question) {
    return new Promise((resolve) => {
        const rl = readline.createInterface({
            input: process.stdin,
            output: process.stdout
        });
        rl.question(question, (answer) => {
            rl.close();
            resolve(answer);
        });
    });
}

async function main() {
    const n = parseInt(await getUserInput("Enter the size of the array: "), 10);
    const arr = Array.from({ length: n }, () => Math.floor(Math.random() * 100) + 1);

    const startTime = Date.now();

    const originalFile = 'original_array.csv';
    const sortedFile = 'sorted_result.csv';

    // Write the original array to file
    writeToFile(originalFile, arr);

    // Perform QuickSort
    quickSort(arr, 0, arr.length - 1);

    // Write the sorted array to file
    writeToFile(sortedFile, arr);

    const endTime = Date.now();
    const totalTime = (endTime - startTime) / 1000;

    console.log("Original and sorted arrays have been saved to CSV files.");
    console.log(`Original array file: ${originalFile}`);
    console.log(`Sorted array file: ${sortedFile}`);
    console.log(`Time taken: ${totalTime} seconds.`);
}

main();
