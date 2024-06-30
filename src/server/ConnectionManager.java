package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class ConnectionManager {
    private static final int PORT = 12345;
    private static Selector selector;
    private static DatagramChannel channel;

    public ConnectionManager() throws IOException {
        selector = Selector.open();
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(PORT));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    public SelectionKey getNextSelectionKey() throws IOException {
        selector.select();
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        SelectionKey key = iterator.next();
        iterator.remove();
        return key;
    }
}
