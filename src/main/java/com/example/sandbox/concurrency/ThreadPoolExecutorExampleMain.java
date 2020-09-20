package com.example.sandbox.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExampleMain {

    public static void main(String args[]) throws InterruptedException {
        // RejectedExecutionHandler implementation
        // RejectedExecutionHandlerImpl rejectionHandler = new
        // RejectedExecutionHandlerImpl();

        // Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(20), threadFactory);
        // start the monitoring thread
        ThreadPoolExecutorExample monitor = new ThreadPoolExecutorExample(executorPool, 3);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();
        // submit work to the thread pool
        // Example: queue=20, core=2 and max=4
        // With 22 tasks:
        // Two threads (core) are started immediately, but as we merely fill the queue
        // (as opposed to overflowing it), no new threads (from max) are created!
        // The result is that only 2 threads will be running at any one time!
        //
        // With 24 tasks:
        // 24 tasks will immediately fill up both the core (2) and queue (20) and now
        // we have 2 tasks overflowing, so we will start picking from the "max-threads"
        // (4).
        // This means that two new threads will be started in addition to the currently
        // running
        // two threads. It also means that a total of FOUR threads will be running tasks
        // simultaneously
        // until we're finished!
        for (int i = 0; i < 24; i++) {
            executorPool.execute(new WorkerThread("cmd" + i));
        }

        Thread.sleep(30000);
        // shut down the pool
        executorPool.shutdown();
        // shut down the monitor thread
        Thread.sleep(5000);
        monitor.shutdown();
    }
}
