package by.kononov.multithreading.entity;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class Riverside implements Iterable<Vehicle>{

	private Queue<Vehicle> vehicleList;

	private Riverside(int capacity) {
		vehicleList = new ArrayDeque<>(capacity);
	}

	public static Riverside createStorage(int capacity) {
		return new Riverside(capacity);
	}

	public static Riverside createStorage(int capacity, Queue<Vehicle> list) {
		Riverside storage = new Riverside(capacity);
		storage.vehicleList.addAll(list);
		return storage;
	}

	public Vehicle getVehicle() {
		return vehicleList.poll();
	}

	public boolean setVehicle(Vehicle vehicle) {
		return vehicleList.offer(vehicle);
	}

	public Queue<Vehicle> getVehicleList() {
		return vehicleList;
	}

	@Override
	public Iterator<Vehicle> iterator() {
		return vehicleList.iterator();
	}
}
