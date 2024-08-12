package com.example.sortingvisualizer.controller;

public class SortRequest {
    private String algorithmType;
    private String sortType;
    private int arraySize;

    // Getters and setters for algorithmType, sortType, and arraySize

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }
}
