package org.example.tasks.parallelfilesearch;

import java.io.*;

public class FileSearchTask implements Runnable {
    private final File file;
    private final String searchTerm;

    public FileSearchTask(File file, String searchTerm) {
        this.file = file;
        this.searchTerm = searchTerm;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNummer = 0;
            while((line = reader.readLine()) != null) {
                lineNummer++;
                if(line.contains(searchTerm)) {
                    System.out.printf("Found in %s (Line %d) : %s%n", file.getAbsolutePath(), lineNummer, line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + file.getAbsolutePath());
        }
    }
}
