package org.n1vnhil.awesomes.utils.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(10, 16, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue(4), new DiscardRejectHandle());
        for(int i = 0; i < 10; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println("Main Thread: Running");

    }
}
