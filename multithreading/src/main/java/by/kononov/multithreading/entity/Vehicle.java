package by.kononov.multithreading.entity;

import java.util.Optional;
import java.util.concurrent.Semaphore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kononov.multithreading.type.VehicleType;

public class Vehicle implements Runnable{
	static final Logger logger = LogManager.getLogger();
	private VehicleType type;
	private long vehicleId;
	private double weight;
	private double square;
	private ParkingPlace place;
	private Semaphore semaphore;

	public Vehicle(VehicleType type, long vehicleId, double weight, double square) {
		this.type = type;
		this.vehicleId = vehicleId;
		this.weight = weight;
		this.square = square;
		semaphore = new Semaphore(0, true);
	}

	@Override
	public void run() {
		RiverFerry ferry = RiverFerry.getInstance();
		Optional<ParkingPlace> optional = ferry.getPlace();
		if (optional.isPresent()) {
			place = optional.get();
			place.using(true);
			logger.info("The {} #{} got the place #{}", this.type, this.vehicleId, place.getPlaceId());
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				logger.error("The thread can not sleep; it throws an exception: ", e);
				Thread.currentThread().interrupt();
			}
			ferry.releasePlace(place);
			place.using(false);
			logger.info("The {} #{} has left, the parking places #{} is free.", this.type, this.vehicleId,
					place.getPlaceId());
		}
	}

	public Optional<ParkingPlace> getUsingPlace() {
		if (place == null) {
			return Optional.empty();
		}
		return Optional.of(place);
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public double getWeight() {
		return weight;
	}

	public double getSquare() {
		return square;
	}

	public VehicleType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (vehicleId ^ (vehicleId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(square);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Vehicle other = (Vehicle) obj;
		if (vehicleId != other.vehicleId || Double.doubleToLongBits(square) != Double.doubleToLongBits(other.square)
				|| Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("[%s, type=%s, id=%s, weight=%s, square=%s]", getClass().getSimpleName(), type, vehicleId,
				weight, square);
	}
}