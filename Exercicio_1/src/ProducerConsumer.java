import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.Semaphore;

import static java.nio.ByteBuffer.allocate;

public class ProducerConsumer {
    private Pipe pipe;
    private WritableByteChannel source;
    private ReadableByteChannel destiny;
    private ByteBuffer buffer;
    private Semaphore produce_mutex;
    private Semaphore consume_mutex;

    public ProducerConsumer() throws IOException {
        buffer = allocate(Integer.BYTES);//size 1
        pipe = Pipe.open();
        source = pipe.sink();
        destiny = pipe.source();
        produce_mutex = new Semaphore(1);
        consume_mutex = new Semaphore(0);


    }

     public void produce(int value) throws InterruptedException {
        produce_mutex.acquire();
        //without a mutex a second thread could  clear the buffer before the first one puts the buffer on the channel
        buffer.clear();
        buffer.put((byte) value);//writes content into buffer
        buffer.flip(); //switch buffer from writing to reading mode
        try {
            source.write(buffer);//buffer into channel
        } catch (IOException e) {
            e.printStackTrace();

        }

         consume_mutex.release();

     }

     public int consume() throws InterruptedException {
        consume_mutex.acquire();
        buffer.clear();

        try{
            destiny.read(buffer);//channel into buffer
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip();
        int value =  buffer.get();//reads content from buffer
        produce_mutex.release();
        return value;

    }

}
