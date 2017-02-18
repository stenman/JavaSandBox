package com.example.sandbox.stuff;

import java.util.Random;

public class MultiTasker implements Runnable {

	@Override
	public void run() {
		Random r = new Random();

		int sleep = 0;

		try {
			for (int i = 0; i < 10; i++) {
				sleep = r.nextInt(10000);
				Thread.sleep(sleep);
				System.out.println("Thread " + Thread.currentThread().getName() + " just slept for " + sleep + " seconds.");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}