#### Purpose of wait and notify
- wait and notify are used to put some communication between threads. 
- `wait` and `notify` should be used in conjunction with `synchronized` to prevent race condition.
- `wait`, `notify` and `notifyAll` are present in `Object` class which is default parent class of every class in Java.
- wait/notify does not tell us that what event occurred, for that we need `shared resource/shared object.`

#### Producer Consumer Problem.
- Producer ============>   [][][][][] (Shared Resource)  ==============> Consumer
- This shared resource could be Queue or LinkedList.
- So, we have three entities: Producer, Consumer, Queue etc.

#### Explanation of code
- Each Java Object has two associated things: `montior` and `watiset` maintained by JVM for every Java object.
- Whenever a thread calls `wait()` method, two things happens
  1. First the thread, who is calling wait(), releases the monitor of object. 
  2. Second the thread, who is calling wait(), is added to `waitset`, and thread goes into `WAITING` satate.
- When thread calls `notify()`, JVM takes waitset associated with the object, and picks one thread from it, and put it into
    Runnable state.
- When thread calls `notifyAll()', JVM takes all threads from waitset and pushes all threads into Runnable state.