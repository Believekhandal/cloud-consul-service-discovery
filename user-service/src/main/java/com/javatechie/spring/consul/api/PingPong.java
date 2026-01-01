package com.javatechie.spring.consul.api;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PingPong {
    private final Lock lock = new ReentrantLock();
    private final Condition pingTurn = lock.newCondition();
    private final Condition pongTurn = lock.newCondition();
    private boolean isPing = true; // Start with ping

    private void printPing() throws InterruptedException {
        lock.lock();
        try {
            while (!isPing) {
                pingTurn.await();
            }
            System.out.print("ping ");
            isPing = false;
            pongTurn.signal(); // Wake up pong
        } finally {
            lock.unlock();
        }
    }

    private void printPong() throws InterruptedException {
        lock.lock();
        try {
            while (isPing) {
                pongTurn.await();
            }
            System.out.print("pong ");
            System.out.print("pong ");
            isPing = true;
            pingTurn.signal(); // Wake up ping
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PingPong pingPong = new PingPong();

        // Thread for ping
        Thread pingThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) { // 3 times: ping pong pong
                    pingPong.printPing();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Thread for pong
        Thread pongThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) { // 3 times: ping pong pong
                    pingPong.printPong();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        pingThread.start();
        pongThread.start();
    }
}
