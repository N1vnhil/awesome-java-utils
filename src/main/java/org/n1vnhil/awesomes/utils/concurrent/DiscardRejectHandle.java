package org.n1vnhil.awesomes.utils.concurrent;

public class DiscardRejectHandle implements RejectHandle {
    @Override
    public void reject(Runnable rejectTask, ThreadPool threadPool) {
        threadPool.blockingQueue.poll();
        threadPool.execute(rejectTask);
    }
}
