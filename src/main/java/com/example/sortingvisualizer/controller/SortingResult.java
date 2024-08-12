package com.example.sortingvisualizer.controller;

public class SortingResult {
    private String output;
    private String errorOutput;
    private int exitCode;
    private String originalArrayFile;
    private String sortedArrayFile;
    private String timeTaken;

    // Getters and setters
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getErrorOutput() {
        return errorOutput;
    }

    public void setErrorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public String getOriginalArrayFile() {
        return originalArrayFile;
    }

    public void setOriginalArrayFile(String originalArrayFile) {
        this.originalArrayFile = originalArrayFile;
    }

    public String getSortedArrayFile() {
        return sortedArrayFile;
    }

    public void setSortedArrayFile(String sortedArrayFile) {
        this.sortedArrayFile = sortedArrayFile;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }
}
