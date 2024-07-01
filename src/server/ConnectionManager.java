package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class ConnectionManager {
    private static final int PORT = 12345;
    private Selector selector;
    private DatagramChannel channel;
    private ByteBuffer buffer;


    public ConnectionManager() {
        try {
            selector = Selector.open();
            channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(PORT));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            buffer = ByteBuffer.allocate(8192);
        } catch (IOException e) {
            System.out.println("failed to start server connection: " + e.getMessage());
        }
    }

    public SelectionKey getNextSelectionKey() {
        try {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            SelectionKey key = iterator.next();
            iterator.remove();
            return key;
        } catch (IOException e) {
            System.out.println("failed to select key: " + e.getMessage());
            return null;
        }
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public DatagramChannel getChannel() {
        return channel;
    }
}
