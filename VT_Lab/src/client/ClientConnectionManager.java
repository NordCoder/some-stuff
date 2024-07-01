package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientConnectionManager {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private DatagramChannel channel;
    private InetSocketAddress address;
    private ByteBuffer buffer;

    public ClientConnectionManager(Client client) {
        createConnection();
    }

    public void createConnection() {
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            address = new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT);
            buffer = ByteBuffer.allocate(8192);
        } catch (IOException e) {
            System.out.println("failed to create connection");
        }
    }

    public DatagramChannel getChannel() {
        return channel;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}
