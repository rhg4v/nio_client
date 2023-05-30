import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open()) {

            setSocket(socketChannel);
            runClientThread(socketChannel);

            while (true) {

                ByteBuffer byteBuffer = setByteBuffer();
                ByteBuffer readBuffer = readSocket(socketChannel, byteBuffer);

                if (byteBuffer.position() == 0) {
                    continue;
                }

                viewSocketResponse(readBuffer);
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    private static void viewSocketResponse(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        String responseValue = new String(byteBuffer.array(), StandardCharsets.UTF_8);
        System.out.println(responseValue);
        byteBuffer.clear();
    }

    private static ByteBuffer readSocket(SocketChannel socketChannel, ByteBuffer byteBuffer) throws IOException {
        socketChannel.read(byteBuffer);

        return byteBuffer;
    }

    private static ByteBuffer setByteBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(500);

        return byteBuffer;
    }

    private static void runClientThread(SocketChannel socketChannel) {
        Thread thread = new Thread(new ClientThread(socketChannel));
        thread.start();
    }

    private static void setSocket(SocketChannel socketChannel) throws IOException {
        socketChannel.bind(new InetSocketAddress(8081));
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
    }
}