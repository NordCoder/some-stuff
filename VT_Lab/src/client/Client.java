package client;

import common.commands.Command;
import common.commands.Response;
import common.input.ConsoleInputGetter;
import common.input.InputParser;
import common.util.BadInputException;
import common.util.CustomPair;
import common.util.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;

import static common.util.Util.getClientCommands;
import static common.util.Util.handleCommandsWithAdditionalInfo;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static DatagramChannel channel;
    private static InetSocketAddress address;
    private static InputParser inputParser;
    private static ByteBuffer buffer;
    private static Serializer<CustomPair<Command, String[]>> commandSerializer = new Serializer<>();
    private static Serializer<Response> responseSerializer = new Serializer<>();

    public static void createConnection() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        address = new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT);
        buffer = ByteBuffer.allocate(8192);
    }

    public static void runMainLoop() {
        inputParser = new InputParser(new ConsoleInputGetter(), getClientCommands());
        long timer;
        while (true) {
            System.out.print(">>>");
            try {
               sendNextCommand();
               timer = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - timer > 10000) {
                        System.out.println("Server's unreachable. Try again later.");
                        break;
                    }
                    InetSocketAddress responseAddress = (InetSocketAddress) channel.receive(buffer);
                    if (responseAddress != null) {
                        buffer.flip();
                        Response response = responseSerializer.deserialize(buffer.array());
                        buffer.clear();
                        System.out.println(response.getText());
                        break;
                    }
                }
            } catch (BadInputException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        createConnection();
        runMainLoop();
    }

    private static void sendNextCommand() throws Exception {
        CustomPair<Command, String[]> command = inputParser.nextCommand();
        handleCommandsWithAdditionalInfo(command.getFirst(), new InputParser(new ConsoleInputGetter(), getClientCommands()));
        buffer.put(commandSerializer.serialize(command));
        buffer.flip();
        channel.send(buffer, address);
        buffer.clear();
    }
}
