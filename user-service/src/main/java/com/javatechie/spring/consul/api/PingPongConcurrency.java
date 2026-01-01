package com.javatechie.spring.consul.api;

public class PingPongConcurrency {

	final static Object lock = new Object();
	protected static volatile boolean isPingTurn = true;

	public static void main(String[] args) {

		Thread pingThread = new Thread(new Ping());
		Thread pongThread = new Thread(new Pong());

		pingThread.start();
		pongThread.start();
	}

}

class Ping implements Runnable {

	@Override
	public void run() {
		synchronized (PingPongConcurrency.lock) {
			while (!PingPongConcurrency.isPingTurn) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			System.out.println("Ping");
			PingPongConcurrency.isPingTurn = false;
			PingPongConcurrency.lock.notifyAll();
		}

	}

}

class Pong implements Runnable {

	@Override
	public void run() {

		synchronized (PingPongConcurrency.lock) {
			while (PingPongConcurrency.isPingTurn) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			System.out.println("Pong");
			System.out.println("Pong");
			PingPongConcurrency.isPingTurn = true;
			PingPongConcurrency.lock.notifyAll();
		}
	}

}
