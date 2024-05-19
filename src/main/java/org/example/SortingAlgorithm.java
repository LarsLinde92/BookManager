package org.example;

import java.util.List;

// Class containing sorting algorithms
public class SortingAlgorithm {

    // Method to perform QuickSort on a list of books
    public void quickSort(List<org.example.Book> books, int low, int high) {
        // Check if low index is less than high index
        if (low < high) {
            // Partition the array and get the pivot index
            int pi = partition(books, low, high);

            // Recursively sort the elements before and after partition
            quickSort(books, low, pi - 1);
            quickSort(books, pi + 1, high);
        }
    }

    // Method to partition the list around the pivot
    private int partition(List<org.example.Book> books, int low, int high) {
        // Select the pivot element (last element)
        org.example.Book pivot = books.get(high);

        // Initialize the smaller element index
        int i = (low - 1);

        // Iterate through the list and rearrange elements
        for (int j = low; j < high; j++) {
            // If the current element is smaller than the pivot
            if (books.get(j).compareTo(pivot) < 0) {
                // Increment the index for the smaller element
                i++;

                // Swap the elements at i and j
                org.example.Book temp = books.get(i);
                books.set(i, books.get(j));
                books.set(j, temp);
            }
        }

        // Swap the pivot element with the element at i+1
        org.example.Book temp = books.get(i + 1);
        books.set(i + 1, books.get(high));
        books.set(high, temp);

        // Return the partitioning index
        return i + 1;
    }
}