package org.example.nio.filetransfer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileReceiver {
    public static void main(String[] args) throws IOException {
        FileReceiver server = new FileReceiver();

        SocketChannel socketChannel = server.createServerSocketChannel();
        server.readFileFromSocketChannel(socketChannel);
        socketChannel.close();
    }

    private static SocketChannel createServerSocketChannel() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(12345));
        SocketChannel client = server.accept();
        System.out.println("connection established ..." + client.getRemoteAddress());
        return client;
    }

    private static void readFileFromSocketChannel(SocketChannel socketChannel) throws IOException {
        Path path = Paths.get("/path/to/file");
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(socketChannel.read(buffer) > 0) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }
        fileChannel.close();
        System.out.println("Receiving file is successful");
    }
}
