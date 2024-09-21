package Extras_Interview.Some_Methods;

/**
 * In simple words, yield() -> Stop the current executing thread and give a chance to other threads of same or higher priority.'
 * Till, Java5, it was being used inside Thread.sleep() method, but now it is not used.
 * Currently, as per the official documentation, It is way to give hint to the scheduler that the current thread is willing to yield its current use of a processor,
 * and it would be willing to schedule back as soon as possible. The scheduler is free to ignore this hint.
 * So, we can say behaviour of yield() method is not guaranteed.
 * And finally, as per official doc itself, It is rarely to use yield() method.
 */
public class Thread_Yield {
    public static void main(String[] args) {
        System.out.println("----TwoThreadsOfSamePriority----");
        TwoThreadsOfSamePriority();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----TwoThreadsOfDifferentPriority----");
        TwoThreadsOfDifferentPriority();
    }

    private static void TwoThreadsOfSamePriority() {

        Runnable r = () -> {
            int counter = 0;
            while(counter < 2){
                System.out.println(Thread.currentThread().getName() + " is running");
                counter++;
                Thread.yield();
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();
    }

    private static void TwoThreadsOfDifferentPriority() {

        Runnable r = () -> {
            int counter = 0;
            while(counter < 2){
                System.out.println(Thread.currentThread().getName() + " is running");
                counter++;
                Thread.yield();
            }
        };

        Thread t1 = new Thread(r, "Thread - priority 1");
        t1.setPriority(1);
        Thread t2 = new Thread(r, "Thread - priority 2");
        t2.setPriority(2);

        t1.start();
        t2.start();
    }
}
