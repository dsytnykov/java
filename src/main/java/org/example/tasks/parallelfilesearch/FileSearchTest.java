package org.example.tasks.parallelfilesearch;

import java.io.File;

public class FileSearchTest {
    public static void main(String[] args) {
        String directoryPath = "C:/logs";  // Change to your target directory
        String searchTerm = "ERROR";       // Change to the text you want to search for
        String filePattern = "*.log";      // Wildcard pattern for file filtering

        ParallelFileSearch searcher = new ParallelFileSearch(4, searchTerm, filePattern);
        searcher.searchInDirectory(new File(directoryPath));
        searcher.shutdown();
    }
}
