package org.example.concurrent.tasks.advanced;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*  Parallel File Processing
    Task: Recursively scan a directory tree and process each file in parallel.
    Goal: Use parallelism efficiently without overwhelming the system.
*/
public class ParallelFileProcessing {

    private record Task(String file) implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);//imitate long file processing
                List<String> strings = Files.readAllLines(Path.of(file), StandardCharsets.UTF_8);
                strings.forEach(System.out::println);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<String> files = List.of("file1.txt", "file2.txt", "file3.txt");

        files.forEach(file -> executor.submit(new Task(file)));

        executor.shutdown();
    }


}
