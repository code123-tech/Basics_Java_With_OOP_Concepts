package Java;

import java.util.ArrayList;
import java.util.List;

class SharedQueue<T> {
    private final int MAX_BUFFER_SIZE;
    private final List<T> buffer;  // we are not using queue now.
    public SharedQueue(){
        this(10);
    }

    public SharedQueue(int size){
        MAX_BUFFER_SIZE = size;
        buffer = new ArrayList<>(MAX_BUFFER_SIZE);
    }

    /**
     * blocks if the list size is MAX_BUFFER_SIZE meaning there is NO extra slot to put the element.
     *
     * @param t
     * @throws InterruptedException
     */
    public synchronized void produce(T t) throws InterruptedException {
        if(buffer.size() == MAX_BUFFER_SIZE){
            System.out.println(Thread.currentThread().getName() + " BUFFER IS FULL. Going into Wait State!");
            wait();  // tell current thread to wait until another thread invokes notify or notifyAll.
        }

        buffer.add(t);
        System.out.println(Thread.currentThread().getName() + " Produced Element " + t);
        notify();
    }

    /**
     * blocks if the list size is 0 meaning there is no element to consume.
     *
     * @return T
     * @throws InterruptedException
     */
    public synchronized T consume() throws InterruptedException{
        if(buffer.size() == 0){
            System.out.println(Thread.currentThread().getName() + " BUFFER IS EMPTY. Going into Wait State!");
            wait();  // tell current thread to wait until another thread invokes notify or notifyAll.
        }
        T t = buffer.remove(0);
        System.out.println(Thread.currentThread().getName() + " Consumed Element " + t);
        notify();
        return t;
    }
}

public class ProducerConsumerProblem {
    private static final SharedQueue<Integer> sharedQueue = new SharedQueue<>(10);

    public static void main(String[] args) throws InterruptedException {
        Runnable producerTask = () -> {
            int i = 0;
            while(true){
                try {
                    sharedQueue.produce(i++);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable consumerTask = () -> {
            while(true){
                try {
                    sharedQueue.consume();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread pThread = new Thread(producerTask, "Producer");
        Thread cThread = new Thread(consumerTask, "Consumer");

        pThread.start();
        cThread.start();

        pThread.join();
        cThread.join();
    }
}
