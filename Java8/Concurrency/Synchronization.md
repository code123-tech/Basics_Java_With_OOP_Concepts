- When there are two or more threads accessing the shared data, it may cause inconsistent result in your program due to race condition.
- To overcome this behaviour two approaches are there in Java
  1. Using `synchronized` and `ReentrantLock`. 
  2. Using `Atomic Classes` which uses `Compare-and-sweep` Constructs.

#### Synchronization internally
- In Java, when `synchronized` gets executed, a `lock` is acquired on resource, and the resource is `Java Object`.
- In Object in Java is associated with a `lock` or `Monitor` (Monitor also known as `Mutual Exclusion lock`) and `watiset`.
- Now question is on which Java Object, it acquires lock? 
  - First, synchronized is used on method level, so first on the object `this` who is calling the method.
  - Second, synchronized is used on block level, we need to specify what resource to be locked.
  - **Note**: synchronized --> Acquiring Lock on Current Resource ---> Thread takes ownership of Monitor object associated with
            current resource (object).

#### Three cases can happen while acquiring lock
1. Another thread already has ownership on Monitor: So current thread gets `BLOCKED` until it gets monitor ownership.
2. Current Thread again asks for ownership on Monitor:  it increments a counter in the monitor indicating the number of 
   times this thread has entered the monitor. This is known as `Reentrancy` or `Reentrant lock`.
3. No thread owns the monitor: current thread acquires monitor, setting entry count of this monitor to 1.

#### Summarize
1. Every object in Java is a lock associated with it. This lock is also called an `Intrinsic Lock` or `Monitor`.
2. Acquiring the lock means gaining ownership of the monitor.
3. When thread enters into synchronized method or block, tries to get ownership of monitor of the object referred by `objectref`.
4. The Monitor or Lock or Intrinsic Lock is reentrant in nature, which means, a thread can acquire the lock that it has 
    already acquired and it maintains the count of how many times the lock has been acquired by a specific thread.
5. The section in which code executed between monitorenter and monitorexit is known as `critical section.`
6. The monitorenter and monitorexit instructions are NOT used in the implementation of synchronized methods.
7. In case of `static` methods, lock is acquired on `.class` object. 
   - In case of `non-static method`, the monitor is acquired on `this` object.
   - In case of synchronized block whether it is static or non-static method, it depends on us that which resource 
     we have shared.
