package by.kononov.multithreading.pool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kononov.multithreading.entity.ParkingPlace;
import by.kononov.multithreading.entity.RiverFerry;

public class ParkingPool<T> {
	static final Logger logger = LogManager.getLogger();
	private static ParkingPool<ParkingPlace> instance;
	private static Lock lock = new ReentrantLock();
	private static AtomicBoolean isExist = new AtomicBoolean(false);
	private Semaphore semaphore = new Semaphore(RiverFerry.FERRY_CAPACITY, true);
	private Queue<T> availablePlaces = new ArrayDeque<>();

	private ParkingPool() {
	}

	public static ParkingPool<ParkingPlace> getInstance() {
		if (!isExist.get()) {
			try {
				lock.lock();
				if (instance == null) {
					instance = new ParkingPool<>();
					isExist.set(true);
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	public void init(Queue<T> source) {
		availablePlaces.addAll(source);
	}

	public T getPlace() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			logger.error("semaphore exception: ", e);
			Thread.currentThread().interrupt();
		}
		return availablePlaces.poll();
	}

	public void releasePlace(T place) {
		if (place instanceof ParkingPlace) {
			availablePlaces.offer(place);
			semaphore.release();
		}
	}

	public Queue<T> getPlaces() {
		return availablePlaces;
	}
}
