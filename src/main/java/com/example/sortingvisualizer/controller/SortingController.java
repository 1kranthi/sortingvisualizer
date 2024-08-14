package com.example.sortingvisualizer.controller;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sort")
public class SortingController {

    @PostMapping("/execute")
    public Map<String, SortingResult> executeSort(@RequestBody SortRequest request) {
        Map<String, SortingResult> results = new HashMap<>();
        results.put("C", runCSort(request));
        results.put("C++", runCppSort(request));
        results.put("Java", runJavaSort(request));
        results.put("Python", runPythonSort(request));
        results.put("JavaScript", runJavaScriptSort(request));
        results.put("CSharp", runCSharpSort(request));
        return results;
    }

    private SortingResult runCSort(SortRequest request) {
        String basePath = "src/main/resources/static/c/";
        return executeCommand(basePath, request, "c");
    }

    private SortingResult runCppSort(SortRequest request) {
        String basePath = "src/main/resources/static/cpp/";
        return executeCommand(basePath, request, "cpp");
    }

    private SortingResult runJavaSort(SortRequest request) {
        String basePath = "src/main/resources/static/java/";
        return executeCommand(basePath, request, "java");
    }

    private SortingResult runPythonSort(SortRequest request) {
        String basePath = "src/main/resources/static/python/";
        return executeCommand(basePath, request, "python");
    }

    private SortingResult runJavaScriptSort(SortRequest request) {
        String basePath = "src/main/resources/static/javascript/";
        return executeCommand(basePath, request, "javascript");
    }

    private SortingResult runCSharpSort(SortRequest request) {
        String basePath = "src/main/resources/static/csharp/";
        return executeCommand(basePath, request, "csharp");
    }

    private SortingResult executeCommand(String basePath, SortRequest request, String language) {
        SortingResult result = new SortingResult();
        String direction = request.getSortType().equals("ascending") ? "ascending" : "descending";
        String algorithm = request.getAlgorithmType().equals("quicksort") ? "QuickSort" : "BubbleSort";
        String extension = language.equals("python") ? ".py" : language.equals("javascript") ? ".js" : "";
        String compileCommand = "";
        String runCommand = "";
        String path = basePath + direction;
        String inputSize = String.valueOf(request.getArraySize()); // Get array size from request
        switch (language) {
            case "c":
                compileCommand = "gcc " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1) + ".c -o " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1);
                runCommand = "echo -e \"" + inputSize + "\" | ./" + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1);
                break;
            case "cpp":
                compileCommand = "g++ " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1) + ".cpp -o " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1);
                runCommand = "echo -e \"" + inputSize + "\" | ./" + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1);
                break;
            case "java":
                compileCommand = "javac " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1) + ".java";
                runCommand = "echo -e \"" + inputSize + "\" | java " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1);
                break;
            case "python":
                compileCommand = ""; // No compilation needed for Python
                runCommand = "echo -e \"" + inputSize + "\" | python3 " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1) + extension;
                break;
            case "javascript":
                compileCommand = ""; // No compilation needed for JavaScript
                runCommand = "node " + algorithm + direction.substring(0, 1).toUpperCase() + direction.substring(1) + extension + " " + inputSize;
                break;
            case "csharp":
                path = basePath + direction + "/" + algorithm + "Project";
                runCommand = "echo -e \"" + inputSize + "\" | dotnet run";
                break;
        }
        StringBuilder command = new StringBuilder("cd " + path);
        if (!compileCommand.isEmpty()) {
            command.append(" && ").append(compileCommand);
        }
        command.append(" && sleep 1 && ").append(runCommand);

        try {
            System.out.println("Executing command: " + command.toString());

            ProcessBuilder builder = new ProcessBuilder("bash", "-c", command.toString());
            builder.directory(new File(path));
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }

            int exitCode = process.waitFor();
            result.setOutput(output.toString());
            result.setErrorOutput(errorOutput.toString());
            result.setExitCode(exitCode);

            System.out.println("Full Output: " + output.toString()); // Add this line
            System.out.println("Error Output: " + errorOutput.toString());
        
            // Assuming the program prints the time taken and file names
            String[] outputLines = output.toString().split("\n");
            for (String outputLine : outputLines) {
                if (outputLine.startsWith("Original array file:")) {
                    result.setOriginalArrayFile(outputLine.split(":")[1].trim());
                } else if (outputLine.startsWith("Sorted array file:")) {
                    result.setSortedArrayFile(outputLine.split(":")[1].trim());
                } else if (outputLine.startsWith("Time taken:")) {
                    result.setTimeTaken(outputLine.split(":")[1].trim());
                }
            }

            System.out.println("Output: " + output.toString());
            System.out.println("Error Output: " + errorOutput.toString());

        } catch (Exception e) {
            result.setOutput("Error occurred: " + e.getMessage());
            result.setErrorOutput(e.toString());
            result.setExitCode(-1);
        }

        return result;
    }
}
