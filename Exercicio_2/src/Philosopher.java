import java.util.Random;

class Philosopher implements Runnable {
    int id = 0;
    Resource fork = null;

    public Philosopher(int initId, Resource initr) {
        id = initId;
        fork = initr;
    }

    @Override
    public void run() {
        while (true) {

            System.out.printf("Philosopher[%d] thinking%n",this.id);
            System.out.printf("Philosopher[%d] hungry%n",this.id);
            fork.pickup(this.id);
            System.out.printf("Philosopher[%d] eating%n",this.id);
            fork.drop(this.id);
        }
    }
}