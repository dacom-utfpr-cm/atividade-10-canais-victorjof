import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Semaphore;

public class DiningPhilosopher implements  Resource {
    int n = 0;
    PipedInputStream[] in = null;
    PipedOutputStream[] out = null;
    PipedOutputStream temp;
    Semaphore fork_mutex;

    public DiningPhilosopher(int initN) {
        n = initN;
        //n -> number of forks, each fork with it's own semaphore to avoid being caught by two philosophers
        in = new PipedInputStream[n];
        out = new PipedOutputStream[n];
        for (int i = 0; i < n; i++) {
            temp  = new PipedOutputStream();
            out[i] = temp;
            try {
                in[i] =  new PipedInputStream(temp,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fork_mutex = new Semaphore(1);
    }

    public void pickup(int i) {
        try {

            fork_mutex.acquire();//Ensures that one philosopher pick up 2 forks at once, avoiding deadlock.
            out[i].write(1);
            out[(i + 1) % n].write(2);
            fork_mutex.release();

        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void drop(int i) {
        try {
            in[i].read();//waits corresponding(i) outputPipe()-> .write()
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in[(i + 1) % n].read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
