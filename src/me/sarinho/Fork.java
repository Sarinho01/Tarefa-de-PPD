package me.sarinho;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private final String name;
    private final ReentrantLock mutex;

    public Fork(String name) {
        this.name = name;
        this.mutex = new ReentrantLock();
    }

    public boolean isFree() {
        return !mutex.isLocked();
    }

    public void takeFork() {
        boolean canTake = mutex.tryLock();
        if (!canTake) throw new RuntimeException(name + " is in use");

        System.out.println(getThreadName() + " take " + name);
    }

    public void leaveFork() {
        if (!isUsingFork()) throw new RuntimeException(name + " is not yours or is not in use");
        mutex.unlock();
        System.out.println(getThreadName() + " release " + name);
    }

    public boolean isUsingFork(){
        return mutex.isHeldByCurrentThread();
    }

    private String getThreadName() {
        return Thread.currentThread().getName();
    }


}
