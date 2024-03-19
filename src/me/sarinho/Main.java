package me.sarinho;

import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5, true);

        Fork[] forks = {
                new Fork("Fork 01"),
                new Fork("Fork 02"),
                new Fork("Fork 03"),
                new Fork("Fork 04"),
                new Fork("Fork 05"),
        };

        int foodQuantity = 3;
        List<Philosopher> philosophers = List.of(
                new Philosopher("Alan", foodQuantity, semaphore, List.of(forks[4], forks[0])),
                new Philosopher("João", foodQuantity, semaphore, List.of(forks[0], forks[1])),
                new Philosopher("Vitor", foodQuantity, semaphore, List.of(forks[1], forks[2])),
                new Philosopher("Rai", foodQuantity, semaphore, List.of(forks[2], forks[3])),
                new Philosopher("Fausto", foodQuantity, semaphore, List.of(forks[3], forks[4]))
        );

        philosophers.forEach(Philosopher::start);
        philosophers.forEach(philosopher -> {
            try {
                philosopher.join();
            } catch (Exception e) {
                System.out.println(philosopher.getName() + " ERROR");
            }
        });
    }
}
