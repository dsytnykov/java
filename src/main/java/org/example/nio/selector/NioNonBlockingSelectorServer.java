package org.example.nio.selector;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.SequencedMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NioNonBlockingSelectorServer {
    private static final Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverSocket = ServerSocketChannel.open();

        serverSocket.bind(new InetSocketAddress(12345));
        serverSocket.configureBlocking(false);

        final Selector selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            selector.select();
            final Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for(Iterator<SelectionKey> it = selectionKeys.iterator(); it.hasNext();) {
                final SelectionKey key = it.next();
                it.remove();

                try {
                    if(key.isValid()) {
                        if(key.isAcceptable()) {
                            accept(key);
                        } else if(key.isWritable()) {
                            write(key);
                        } else if(key.isReadable()) {
                            read(key);
                        }
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
            sockets.keySet().removeIf(socketChannel -> !socketChannel.isOpen());
        }
    }

    private static void accept(final SelectionKey key) throws IOException {
        ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socket = socketChannel.accept();
        socket.configureBlocking(false);
        socket.register(key.selector(), SelectionKey.OP_READ);
        sockets.put(socket, ByteBuffer.allocateDirect(80));
    }

    private static void read(final SelectionKey key) throws IOException {
        final SocketChannel socket = (SocketChannel) key.channel();
        socket.configureBlocking(false);
        final ByteBuffer byteBuffer = sockets.get(socket);
        int data = socket.read(byteBuffer);

        byteBuffer.flip();
        invertCase(byteBuffer);

        key.interestOps(SelectionKey.OP_WRITE);

        if (data == -1) {
            closeSocket(socket);
            sockets.remove(socket);
        }
    }

    private static void write(final SelectionKey key) throws IOException {
        final SocketChannel socket = (SocketChannel) key.channel();
        final ByteBuffer byteBuffer = sockets.get(socket);
        socket.write(byteBuffer);
        while(!byteBuffer.hasRemaining()) {
            byteBuffer.compact();
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    private static void closeSocket(final SocketChannel socket) {
        try {
            socket.close();
        } catch(IOException e) {
            //ignore
        }
    }

    private static void invertCase(final ByteBuffer byteBuffer) {
        for(int x = 0; x < byteBuffer.limit(); x++) {
            byteBuffer.put(x, (byte) invertCase((byteBuffer.get(x))));
        }
    }

    private static int invertCase(final int data) {
        return Character.isLetter(data)
                ? Character.isUpperCase(data) ? Character.toLowerCase(data) : Character.toUpperCase(data)
                : data;
    }
}
