package org.example.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleNetworkSerialization {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(SimpleNetworkSerialization::serverMethod);
        executor.submit(SimpleNetworkSerialization::clientMethod);

        executor.shutdown();
    }

    private static void serverMethod() {
        try (ServerSocket serverSocket = new ServerSocket(12345);
             Socket socket = serverSocket.accept();
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            User user = (User) ois.readObject();

            System.out.println("User received from the client: " + user);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void clientMethod() {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

            User user = new User("John Doe", 30);
            oos.writeObject(user);

            System.out.println("User was sent to the server");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private record User(String name, int age) implements Serializable { }
}
