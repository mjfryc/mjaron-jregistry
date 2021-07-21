package pl.mjaron.jregistry.core;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantSection implements ICriticalSection {

    private final ReentrantLock locker = new ReentrantLock();

    @Override
    public void lock() {
        locker.lock();
    }

    @Override
    public void unlock() {
        locker.unlock();
    }

    @Override
    public <V> V withLock(Callable<V> func) {
        return ICriticalSection.doWithLock(this, func);
    }
}
