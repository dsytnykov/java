package org.example.concurrent.tasks.intermediate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.*;

/* Thread Pool Execution
    Task: Use ExecutorService to process a list of tasks (e.g., downloading URLs).
    Goal: Learn how to manage a thread pool and handle task submission.
*/
public class ThreadPoolExecution {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final HttpClient client = HttpClient.newHttpClient();

    private record DownloadTask(String url, HttpClient client) implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("Downloading " + url + " by " + Thread.currentThread().getName());
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder().uri(URI.create(url)).GET().build(),
                    HttpResponse.BodyHandlers.ofString());
            return response.body().length() + " bytes";
        }
    }


    public static void main(String[] args) throws InterruptedException {
        List.of("https://google.com", "https://apple.com", "https://amazon.com").forEach(url ->
        {
            try {
                System.out.println(executor.submit(new DownloadTask(url, client)).get(20, TimeUnit.SECONDS));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        executor.shutdown();

    }
}
