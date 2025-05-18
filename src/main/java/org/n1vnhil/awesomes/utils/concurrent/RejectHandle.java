package org.n1vnhil.awesomes.utils.concurrent;

public interface RejectHandle {

    void reject(Runnable rejectTask, ThreadPool threadPool);

}
