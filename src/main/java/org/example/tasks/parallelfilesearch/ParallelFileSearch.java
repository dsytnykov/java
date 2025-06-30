package org.example.tasks.parallelfilesearch;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelFileSearch {

    private final ExecutorService executor;
    private final String searchTerm;
    private final String filePattern;

    public ParallelFileSearch(int numThreads, String searchTerm, String filePattern) {
        this.executor = Executors.newFixedThreadPool(numThreads);
        this.searchTerm = searchTerm;
        this.filePattern = filePattern;
    }

    public void searchInDirectory(File directory) {
        if(!directory.isDirectory()) {
            System.out.println("The provided path is not a directory : " + directory.getAbsolutePath());
            return;
        }

        File[] files = directory.listFiles((dir, name) -> name.matches(filePattern.replace("*", ".*")));
        if(files == null) return;

        for(File file : files) {
            if(file.isFile()) {
                executor.execute(new FileSearchTask(file, searchTerm));
            } else if(file.isDirectory()) {
                searchInDirectory(file);
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if(!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
