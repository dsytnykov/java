package org.example.tasks.multithreadedfilemerger;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class MultiThreadedFileMerger {
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Future<File> mergeSortedFiles(List<File> inputFiles, File outputFile) {
        return executor.submit(() -> {
            PriorityQueue<FileEntry> minHeap = new PriorityQueue<>(Comparator.comparing(FileEntry::line));
            Map<FileEntry, BufferedReader> readers = new HashMap<>();

            try {
                // Open all input files and initialize heap
                for (File file : inputFiles) {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    if (line != null) {
                        FileEntry entry = new FileEntry(line, file);
                        minHeap.add(entry);
                        readers.put(entry, reader);
                    }
                }

                // Open output file writer
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                    while (!minHeap.isEmpty()) {
                        FileEntry smallestEntry = minHeap.poll();
                        writer.write(smallestEntry.line());
                        writer.newLine();

                        BufferedReader reader = readers.get(smallestEntry);
                        String nextLine = reader.readLine();
                        if (nextLine != null) {
                            minHeap.add(new FileEntry(nextLine, smallestEntry.file()));
                        }
                    }
                }

            } finally {
                // Close all file readers
                for (BufferedReader reader : readers.values()) {
                    reader.close();
                }
            }
            return outputFile;
        });
    }

    private record FileEntry(String line, File file) {
    }

    public void shutdown() {
        executor.shutdown();
    }

    // Main method for testing
    public static void main(String[] args) throws Exception {
        MultiThreadedFileMerger merger = new MultiThreadedFileMerger();

        // Example sorted files (replace with real files)
        List<File> inputFiles = List.of(new File("file1.txt"), new File("file2.txt"), new File("file3.txt"));
        File outputFile = new File("merged_output.txt");

        Future<File> future = merger.mergeSortedFiles(inputFiles, outputFile);
        System.out.println("Merging in progress...");

        // Wait for merge completion
        File result = future.get();
        System.out.println("Merge completed: " + result.getAbsolutePath());

        merger.shutdown();
    }
}
