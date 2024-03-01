package Java;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * If we take approach of creating thread whenever a request arrives, it has some major disadvantages
 * <ol>
 * <li>Server consumes time to create thread when it arrives, and when task is done, destroying that thread.</li>
 * <li>Resource Thrashing: Suppose at a time, 200 requests comes, so 200 new thread creation will happen which means a lot of
 * memory usage, can cause system to run out of memory.</li>
 * </ol>
 * <p>Executor helps to create a pool of threads which are re-usable for different tasks, and it can remove the disadvantages
 * received in the above approach.</p>
 * <p>Executor internally uses a thread pool, which already creates some X number of threads, and re-uses those X threads only
 * for handling any request.</p>
 * <br/>
 * There is a class ThreadPoolExecutor exists in Executor framework.
 * Now, we only have to create Runnable Objects and send them to executor to execute, and Steps are as follows:
 * <ol>
 * <li> Create a task (Runnable Object) to execute</li>
 * <li> Need to create Object of ThreadPoolExecutor class which points to ExecutorService Interface.</li>
 * <li> pass Runnable task to the above created object for execution.</li>
 * <li> Shut down the executor.</li>
 *</ol>
 * <br/>
 * Executor Thread Pool Creation Methods.
 * <ul>
 * <li>newFixedThreadPool(int) - Fixed size thread pool.</li>
 * <li>newCachedThreadPool()  - Create new thread as needed, otherwise re-uses previously created threads.</li>
 * <li>newSingleThreadExecutor() - Creates Single thread.</li>
 *</ul>
 * <p>ThreadPoolExecutor internally manages a Blocking Queue in which it publishes tasks, and consumes tasks from it
 * whenever thread is available to pick up tasks.</p>
 *<br/>
 * <u>Disadvantages (As per GFG article):</u>
 * <ul>
 * <li> <b><u><i>Deadlock:</i></u></b> Suppose a thread executing a task, waiting for the result from a thread which is waiting in the queue.
 * So, both are waiting for each other to complete their task, it is deadlock.</li>
 * <li> <b><u><i>Thread Leakage:</i></u></b> During a task execution, an exception occurs, and it is not caught by thread pool, in that case that thread
 * simply exist, and thread pool size reduces by one without our knowledge.</li>
 * <li> <b><u><i>Resource Thrashing:</i></u></b> Creating Too many threads in thread pool, but using only few leads to wastage of resources, and also
 * leads to time-wasting in context switching.</li>
 *</ul>
 * Question is: what should be the optimal size of ThreadPoolExecutor?
 * <p>As per GFG answer, The optimum size of the thread pool depends on the number of processors available and the nature of
 * the tasks. On an N processor system for a queue of only computation type processes, a maximum thread pool size of N or
 * N+1 will achieve the maximum efficiency.But tasks may wait for I/O and in such a case we take into account the ratio of
 * waiting time(W) and service time(S) for a request; resulting in a maximum pool size of N*(1+ W/S) for maximum efficiency.
 *</p><br/>
 * Read complete Article: <a href="https://www.geeksforgeeks.org/thread-pools-java/">Thread Pools</a>
 * <p>For a multi threaded logger implementation using Executors, you can check: <a href="https://github.com/code123-tech/Low-Level-Design-Questions/tree/master/Questions/MultiThreadedLogger">MultiThreaded Logger</a></p>
 */
public class ExecutorsHansOn {
    static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    static {
        System.out.println("======= Executor Hands On Started ==========");
        System.out.println("Available number of threads in thread pool are: " + MAX_THREADS);
    }
    public static void main(String[] args) {
        Runnable[] tasks = new Runnable[MAX_THREADS+2];
        for(int i = 0; i < tasks.length; i++){
            tasks[i] = new Task("Task " + (i+1));   // Step 1
        }

        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);  // Step 2
        for(int i = 0; i < tasks.length; i++){
            pool.execute(tasks[i]);  // Step 3
        }
        pool.shutdown(); // Step 4
    }
}

class Task implements Runnable{
    private String name;
    public Task(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try{
            for(int i = 0; i <= 5; i++){
                if(i == 0){
                    Date d = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
                    System.out.println("Initialization Time for"
                            + " task name - "+ name +" = " +ft.format(d));
                }else{
                    Date d = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
                    System.out.println("Executing Time for task name - "+
                            name +" = " +ft.format(d));
                }
                Thread.sleep(1000);
            }
            System.out.println(name+" complete");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
