/* 
Author: Victor Figueira
Date:  09/12/2019
Task: 2. Implementar o problema do jantar dos filósofos usando Java
IO: PipedInputStream e PipedOutputStream.
*/
public class Exercicio_2 {
    public static void main(String[] args) {
        DiningPhilosopher philosopher = new DiningPhilosopher(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Philosopher(i, philosopher)).start();
        }
    }
}
