package org.n1vnhil.awesomes.utils.concurrent.lock;

import org.n1vnhil.awesomes.utils.ArrayList;
import org.n1vnhil.awesomes.utils.List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] count = new int[]{1000};
        AwesomeLock lock = new AwesomeLock();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            threads.add(new Thread(() -> {
                lock.lock();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for(int j = 0; j < 10; j++) {
                    count[0]--;
                }
                lock.unlock();
            }));
        }

        for(Thread thread: threads) thread.start();

        for(Thread thread: threads) thread.join();

        System.out.println(count[0]);
    }
}
