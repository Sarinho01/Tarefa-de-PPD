package me.sarinho;

import java.util.List;
import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private final List<Fork> forks;
    private final Semaphore semaphore;
    private int food;

    public Philosopher(String name, int food, Semaphore semaphore, List<Fork> forks) {
        super(name);
        this.forks = forks;
        this.food = food;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println(super.getName() + " started");
        try {
            while (food > 0) {
                semaphore.acquire();
                takeForks();
                eat();
                leaveForks();
                semaphore.release();
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(super.getName() + " finished");
        }
    }

    public void eat() throws InterruptedException {
        if (!usingForks()) throw new RuntimeException(super.getName() + " cannot eat without forks");
        if (food <= 0) throw new RuntimeException(super.getName() + " cannot eat, because there is no more food");

        Thread.sleep(1500);
        food--;
        System.out.println(super.getName() + " ate a bit, status of plate: " + food);
    }

    public void takeForks() {
        if (!forksAreFree())
            throw new RuntimeException(super.getName() + " cannot take forks, because forks are in use");

        forks.forEach(Fork::takeFork);
        System.out.println(super.getName() + " took forks");
    }

    public void leaveForks() {
        if (!usingForks())
            throw new RuntimeException(super.getName() + " cannot release forks, because forks are not in use");

        forks.forEach(Fork::leaveFork);
        System.out.println(super.getName() + " released forks");
    }

    private boolean usingForks() {
        boolean isUsing = true;
        for (Fork fork : forks) isUsing = isUsing && fork.isUsingFork();
        return isUsing;
    }

    private boolean forksAreFree() {
        boolean isFree = true;
        for (Fork fork : forks) isFree = isFree && fork.isFree();
        return isFree;
    }


}
