package org.example.nio.filetransfernonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class FileSender {
    public static void main(String[] args) throws IOException {
        SocketChannel socket = SocketChannel.open();
        socket.configureBlocking(false);
        Selector selector = Selector.open();
        socket.connect(new InetSocketAddress("localhost", 12345));
        socket.register(selector, SelectionKey.OP_CONNECT);

        while(true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                SocketChannel client = (SocketChannel) key.channel();
                if(key.isConnectable()) {
                    if(client.isConnectionPending()) {
                        System.out.println("Trying to finish connection");
                        client.finishConnect();
                    }
                    client.register(selector, SelectionKey.OP_WRITE);
                    continue;
                }

                if(key.isWritable()) {
                    sendFile(client);
                    client.close();
                    return;
                }
            }
        }
    }

    private static void sendFile(SocketChannel client) throws IOException {
        String fName = "path/to/file";
        int bufferSize = 10240;
        Path path = Paths.get(fName);
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        int noOfBytesRead = 0;
        int counter = 0;
        do {
            noOfBytesRead = fileChannel.read(buffer);
            if(noOfBytesRead <= 0) {
                break;
            }
            counter += noOfBytesRead;
            buffer.flip();
            do {
                noOfBytesRead -= client.write(buffer);
            } while(noOfBytesRead > 0);
            buffer.clear();
        } while(true);
        fileChannel.close();
        System.out.println("Receiver: " + counter );
    }

}
