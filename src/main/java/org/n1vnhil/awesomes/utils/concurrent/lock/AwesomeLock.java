package org.n1vnhil.awesomes.utils.concurrent.lock;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class AwesomeLock {

    AtomicBoolean flag = new AtomicBoolean();

    Thread owner = null;

    AtomicReference<Node> head = new AtomicReference<>(new Node());

    AtomicReference<Node> tail = new AtomicReference<>(head.get());

    void lock() {
        if(flag.compareAndSet(false, true)) {
            System.out.println(Thread.currentThread().getName() + "直接拿到锁");
            owner = Thread.currentThread();
            return;
        }
        Node current = new Node();
        current.thread = Thread.currentThread();

        while(true) {
            Node currentTail = tail.get();
            if(tail.compareAndSet(currentTail, current)) {
                System.out.println(Thread.currentThread().getName() + "加入链表尾");
                currentTail.next = current;
                current.pre =currentTail;
                break;
            }
        }

        while(true) {
            if(current.pre.equals(head.get()) && flag.compareAndSet(false, true)) {
                System.out.println(Thread.currentThread().getName() + "被唤醒后拿到锁");
                owner = Thread.currentThread();
                head.set(current);
                current.pre.next = null;
                current.pre = null;
                return;
            }
            LockSupport.park();
        }

    }

    void unlock() {
        if(!owner.equals(Thread.currentThread())) {
            throw new IllegalStateException("当前线程未持有锁");
        }
        Node headNode = head.get();
        Node next = headNode.next;
        flag.set(false);
        if(next != null) {
            LockSupport.unpark(next.thread);
            System.out.println(Thread.currentThread().getName() + "唤醒了" + next.thread.getName());
        }
    }

    class Node {
        Node pre, next;
        Thread thread;
    }

}
