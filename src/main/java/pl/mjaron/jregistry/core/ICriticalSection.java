package pl.mjaron.jregistry.core;

import java.util.concurrent.Callable;

public interface ICriticalSection {

    void lock();
    void unlock();

    <V> V withLock(Callable<V> func);

    static <V> V doWithLock(ICriticalSection section, Callable<V> func) {
        try {
            section.lock();
            return func.call();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Critical section exception.", e);
        } finally {
            section.unlock();
        }
    }

}
