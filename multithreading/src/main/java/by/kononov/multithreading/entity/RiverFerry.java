package by.kononov.multithreading.entity;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kononov.multithreading.action.FerryAction;
import by.kononov.multithreading.action.impl.FerryActionImpl;
import by.kononov.multithreading.pool.ParkingPool;

public class RiverFerry implements Callable<Integer>{
	static final Logger logger = LogManager.getLogger();
	private static RiverFerry instance;
	private static Lock lock = new ReentrantLock();
	private static AtomicBoolean isExist = new AtomicBoolean(false);

	public static final String FERRY_NAME = "Charon";
	public static final int FERRY_CAPACITY = 8;
	private static final double FERRY_TONNAGE = 40;
	private static final double FERRY_PARKING_SQUARE = 80;
	private Riverside riversideA;
	private Riverside riversideB;
	private Queue<Vehicle> ferryBody;
	private ParkingPool<ParkingPlace> pool;

	private RiverFerry() {
		init();
	}

	public static RiverFerry getInstance() {
		if (!isExist.get()) {
			try {
				lock.lock();
				if (instance == null) {
					instance = new RiverFerry();
					isExist.set(true);
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	@Override
	public Integer call() {
		logger.info("The RiverFerry-{} starts work.", FERRY_NAME);
		FerryAction action = new FerryActionImpl();
		Phaser phaser = new Phaser();
		int count = 0;
		while (!riversideA.getVehicleList().isEmpty()) {
			phaser.register();
			action.loadFerry(riversideA, ferryBody, FERRY_TONNAGE, FERRY_PARKING_SQUARE);
			phaser.arriveAndAwaitAdvance();
			action.moveFerry();
			phaser.arriveAndAwaitAdvance();
			action.unloadFerry(riversideB, ferryBody);
			phaser.arriveAndDeregister();
			count++;
		}
		logger.info("The RiverFerry-{} finished work. There are no vehicles left on the riverside A.", FERRY_NAME);
		return count;
	}

	public void init() {
		ferryBody = new ArrayDeque<>(FERRY_CAPACITY);
		Queue<ParkingPlace> placesList = new ArrayDeque<>();
		for (long i = 0; i < FERRY_CAPACITY; i++) {
			placesList.add(new ParkingPlace(i + 1));
		}
		pool = ParkingPool.getInstance();
		pool.init(placesList);
		pool = ParkingPool.getInstance();
	}

	public void setRiversideA(Riverside riversideA) {
		this.riversideA = riversideA;
	}

	public void setRiversideB(Riverside riversideB) {
		this.riversideB = riversideB;
	}

	public Optional<ParkingPlace> getPlace() {
		ParkingPlace place = this.pool.getPlace();
		if (place == null) {
			return Optional.empty();
		}
		return Optional.of(place);
	}

	public void releasePlace(ParkingPlace place) {
		this.pool.releasePlace(place);
	}
}
