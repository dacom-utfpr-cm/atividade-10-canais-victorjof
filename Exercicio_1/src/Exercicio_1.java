/* 
Author: Victor Figueira
Date:  09/12/2019
Task: 1. Implementar o problema do produtor-consumidor usando Java
NIO: Pipe, Pipe.SinkChannel e Pipe.SourceChannel.
*/
import java.io.IOException;


public class Exercicio_1  {
    static int iterations = 5;
    
    public static void main(String[] args) throws IOException{
        ProducerConsumer controller = new ProducerConsumer();

        new Thread(() -> producing(controller)).start();
        new Thread(() -> producing(controller)).start();
        new Thread(() -> consuming(controller)).start();
        new Thread(() -> consuming(controller)).start();
        new Thread(() -> producing(controller)).start();
        new Thread(() -> producing(controller)).start();
        new Thread(() -> consuming(controller)).start();
        new Thread(() -> consuming(controller)).start();

    }

    public static void producing(ProducerConsumer controller){
        for (int i = 0; i < iterations; i++) {
            try {
                controller.produce(i);
                System.out.printf("Producing %d %n",i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void consuming(ProducerConsumer controller){
        for (int i = 0; i < iterations ; i++) {
            try {
                System.out.printf("Consuming %d %n",controller.consume());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
