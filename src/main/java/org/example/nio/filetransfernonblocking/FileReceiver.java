package org.example.nio.filetransfernonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class FileReceiver {
    public static final int PORT = 12345;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(HOST, PORT));
        serverChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if(key.isAcceptable()) {
                    SocketChannel socket = serverChannel.accept();

                    System.out.println("Client joined");
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);
                    continue;
                }

                if(key.isReadable()) {
                    SocketChannel socket = (SocketChannel) key.channel();
                    receiveFile(socket);
                    socket.close();
                    return;
                }
            }
        }
    }

    private static void receiveFile(SocketChannel socket) throws IOException {
        String outputFile = "/path/to/new/file";
        int bufSize = 10240;
        Path path = Paths.get(outputFile);
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(bufSize);
        int res = 0;
        int counter = 0;
        do {
            buffer.clear();
            res = socket.read(buffer);
            System.out.println(res);
            buffer.flip();
            if(res > 0) {
                fileChannel.write(buffer);
                counter += res;
            }
        } while (res >= 0);
        socket.close();
        fileChannel.close();
        System.out.println("Receiver: " + counter);
    }
}
