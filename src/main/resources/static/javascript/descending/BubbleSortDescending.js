const fs = require('fs');
const path = require('path');

// Bubble Sort Algorithm
function bubbleSort(arr) {
    let n = arr.length;
    for (let i = 0; i < n - 1; i++) {
        for (let j = 0; j < n - i - 1; j++) {
            if (arr[j] < arr[j + 1]) {
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
            }
        }
    }
}

// Write array to a file
function writeToFile(filename, arr) {
    const data = arr.join(',') + '\n';
    fs.writeFileSync(filename, data);
}

// Read array from a file
function readFromFile(filename) {
    if (!fs.existsSync(filename)) {
        console.error('File not found:', filename);
        return [];
    }
    const content = fs.readFileSync(filename, 'utf-8');
    return content.split(',').map(num => parseInt(num.trim(), 10));
}

// Main function
function main() {
    if (process.argv.length < 3) {
        console.error('Usage: node script.js <array-size>');
        process.exit(1);
    }

    const n = parseInt(process.argv[2], 10); // Get array size from command-line argument
    if (isNaN(n) || n <= 0) {
        console.error('Array size must be a positive integer.');
        process.exit(1);
    }

    const basePath = path.join(__dirname, '../../'); // Go up two levels to reach the static directory
    const originalFile = path.join(basePath, 'original_array.csv');
    const sortedFile = path.join(basePath, 'sorted_result.csv');

    // Always create a new array based on user input
    const arr = Array.from({ length: n }, () => Math.floor(Math.random() * 100) + 1);
    writeToFile(originalFile, arr);

    const startTime = Date.now();

    bubbleSort(arr);

    writeToFile(sortedFile, arr);

    const endTime = Date.now();
    const totalTime = (endTime - startTime) / 1000;

    // // Output results with HTML-style links
    // console.log(`Original array file: <a href="/static/original_array.csv">original_array.csv</a>`);
    // console.log(`Sorted array file: <a href="/static/sorted_result.csv">sorted_result.csv</a>`);
    console.log(`Time taken: ${totalTime} seconds.`);
}

main();
