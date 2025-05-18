package org.n1vnhil.awesomes.utils.concurrent;

public class ThrowRejectHandle implements RejectHandle{
    @Override
    public void reject(Runnable rejectTask, ThreadPool threadPool) {
        throw new RuntimeException("阻塞队列已满");
    }
}
