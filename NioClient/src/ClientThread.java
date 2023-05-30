import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ClientThread implements Runnable {
    private static final String DEFAULT_MESSAGE = " to testserver from c ";
    private SocketChannel socketChannel;

    public ClientThread() {
    }

    public ClientThread(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        int number = 0;

        try {

            while (true) {

                ByteBuffer byteBuffer = setByteBufferSetting();
                String message = createMessage(number++, DEFAULT_MESSAGE);
                setByteBuffer(byteBuffer, message);
                writeSocketChannel(byteBuffer);
            }
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }

    private void writeSocketChannel(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.flip();
        System.out.println("receive: "+new String(byteBuffer.array(), StandardCharsets.UTF_8));
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
    }

    private static void setByteBuffer(ByteBuffer byteBuffer, String message) {
        byteBuffer.put(message.getBytes());
    }

    private static ByteBuffer setByteBufferSetting() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(500);

        return byteBuffer;
    }

    private static String createMessage(int sequence, String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sequence).append(DEFAULT_MESSAGE);

        for (int i = stringBuilder.toString().getBytes().length; i < 500; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }
}