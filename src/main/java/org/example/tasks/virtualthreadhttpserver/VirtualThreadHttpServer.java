package org.example.tasks.virtualthreadhttpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*1️⃣ High-Throughput HTTP Server using Virtual Threads

Build a multi-client HTTP server handling thousands of requests efficiently.

Use Java’s HttpServer + Virtual Threads to process requests asynchronously.

Ensure efficient CPU usage and minimal memory overhead.*/
public class VirtualThreadHttpServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        int backlog = 100;

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), backlog);

        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        httpServer.setExecutor(executorService);

        httpServer.createContext("/hello", new HelloHandler());

        System.out.println("Http Server started on port " + port);
        httpServer.start();
    }

    static class HelloHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello from Virtual Threads";
            exchange.sendResponseHeaders(200, response.length());

            try(OutputStream out = exchange.getResponseBody()) {
                out.write(response.getBytes());
            }
        }
    }
}

/*How to Run the Server
1️⃣ Compile & Run:
javac VirtualThreadHttpServer.java
java VirtualThreadHttpServer
2️⃣ Test it in your browser or via curl:

curl http://localhost:8080/hello*/
