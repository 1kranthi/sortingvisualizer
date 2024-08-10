const fs = require('fs');

function bubbleSort(arr) {
    let n = arr.length;
    for (let i = 0; i < n - 1; i++) {
        for (let j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
            }
        }
    }
}

function writeToFile(filename, arr) {
    const data = arr.join(',') + '\n';
    fs.writeFileSync(filename, data);
}

function main() {
    const prompt = require('prompt-sync')();
    const n = parseInt(prompt("Enter the size of the array: "), 10);
    const arr = Array.from({ length: n }, () => Math.floor(Math.random() * 100) + 1);

    const startTime = Date.now();

    const originalFile = 'original_array.csv';
    const sortedFile = 'sorted_result.csv';

    writeToFile(originalFile, arr);

    bubbleSort(arr);

    writeToFile(sortedFile, arr);

    const endTime = Date.now();
    const totalTime = (endTime - startTime) / 1000;

    console.log("Original and sorted arrays have been saved to CSV files.");
    console.log(`Original array file: ${originalFile}`);
    console.log(`Sorted array file: ${sortedFile}`);
    console.log(`Time taken: ${totalTime} seconds.`);
}

main();
