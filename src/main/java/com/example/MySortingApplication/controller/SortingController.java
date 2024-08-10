package com.example.MySortingApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/sort")
public class SortingController {

    @PostMapping("/execute")
    public ResponseEntity<SortingResult> executeSorting(@RequestBody SortRequest request) {
        String language = request.getLanguage();
        String algorithm = request.getAlgorithm();
        String order = request.getOrder();

        // Determine the file path based on language, algorithm, and order
        String filePath = determineFilePath(language, algorithm, order);

        // Build the command to execute
        String[] command = buildCommand(language, filePath);

        // Execute the program and get results
        SortingResult result = executeProgram(command);

        return ResponseEntity.ok(result);
    }

    private String determineFilePath(String language, String algorithm, String order) {
        return "src/main/resources/static/" + language + "/" + order + "/" + algorithm + "Sort" + order;
    }

    private String[] buildCommand(String language, String filePath) {
        switch (language.toLowerCase()) {
            case "java":
                return new String[]{"javac", filePath + ".java", "&&", "java", filePath};
            case "c":
                return new String[]{"gcc", filePath + ".c", "-o", "output", "&&", "./output"};
            case "cpp":
                return new String[]{"g++", filePath + ".cpp", "-o", "output", "&&", "./output"};
            case "python":
                return new String[]{"python3", filePath + ".py"};
            case "javascript":
                return new String[]{"node", filePath + ".js"};
            case "csharp":
                // Assuming that the .csproj file is in the same directory as .cs files
                return new String[]{"dotnet", "build", filePath + ".csproj", "&&", "dotnet", "run", "--project", filePath + ".csproj"};
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    private SortingResult executeProgram(String[] command) {
        SortingResult result = new SortingResult();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new File("src/main/resources/static/"));

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            result.setOutput(output.toString());
            result.setExitCode(exitCode);

        } catch (Exception e) {
            e.printStackTrace();
            result.setOutput("An error occurred: " + e.getMessage());
            result.setExitCode(1);  // Set a non-zero exit code to indicate error
        }

        return result;
    }
}
