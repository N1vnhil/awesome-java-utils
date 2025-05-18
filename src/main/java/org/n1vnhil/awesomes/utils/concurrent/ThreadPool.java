package org.n1vnhil.awesomes.utils.concurrent;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private int corePoolSize;

    private int maxSize;

    private int timeout;

    private TimeUnit timeUnit;

    List<Thread> coreList = new ArrayList<>();

    List<Thread> supportList = new ArrayList<>();

    BlockingQueue<Runnable> blockingQueue;

    RejectHandle rejectHandle;

    public ThreadPool(int corePoolSize, int maxSize, int timeout, TimeUnit timeUnit, BlockingQueue blockingQueue, RejectHandle rejectHandle) {
        this.corePoolSize = corePoolSize;
        this.maxSize = maxSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.blockingQueue = blockingQueue;
        this.rejectHandle = rejectHandle;
    }

    void execute(Runnable runnable) {

        if(coreList.size() < corePoolSize) {
            Thread thread = new CoreThread();
            coreList.add(thread);
            thread.start();
        }

        if(blockingQueue.offer(runnable)) {
            return;
        }

        if(coreList.size() + supportList.size() < maxSize) {
            Thread thread = new SupportThread();
            supportList.add(thread);
            thread.start();
        }

        if(!blockingQueue.offer(runnable)) {
            rejectHandle.reject(runnable, this);
        }

    }

    class CoreThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    Runnable command = blockingQueue.take();
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }

            }
        }
    }

    class SupportThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    Runnable command = blockingQueue.poll(timeout, timeUnit);
                    if(command == null) {
                        break;
                    }
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }

            }
        }
    }
}
