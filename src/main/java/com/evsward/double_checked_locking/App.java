package com.evsward.double_checked_locking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
	public static void main(String[] args) {
		final Inventory inventory = new Inventory(100);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 3; i++) {
			executorService.execute(() -> {
				while (inventory.addItem(new Item())) {};
			});
		}

		executorService.shutdown();
		try {
			executorService.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Error waiting for ExecutorService shutdown.");
		}
	}
}
