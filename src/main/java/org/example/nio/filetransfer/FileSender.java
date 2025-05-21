package org.example.nio.filetransfer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSender {
    public static void main(String[] args) throws IOException {
        FileSender client = new FileSender();
        SocketChannel socketChannel = client.createChannel();
        client.sendFile(socketChannel);
    }

    private SocketChannel createChannel() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        SocketAddress address = new InetSocketAddress("localhost", 12345);
        socketChannel.connect(address);
        return socketChannel;
    }

    private void sendFile(SocketChannel socketChannel) throws IOException {
        Path path = Paths.get("/path/to/file");
        FileChannel fileChannel = FileChannel.open(path);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(fileChannel.read(buffer) > 0) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
        fileChannel.close();
    }
}
