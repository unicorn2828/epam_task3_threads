package by.kononov.multithreading.validator;

import java.util.Optional;

import by.kononov.multithreading.entity.ParkingPlace;
import by.kononov.multithreading.entity.Vehicle;

/**
 * This is the VehicleValidator class; it is used to check the vehicle
 * before loading on the ferry.
 * 
 * @author Vitaly Kononov
 * @since 2019-12-12
 */
public class VehicleValidator{

	public boolean isValidAuto(Vehicle vehicle, double tonnage, double parkingSquare) {
		Optional<ParkingPlace> optional = vehicle.getUsingPlace();
		ParkingPlace place = null;
		if (optional.isPresent()) {
			place = optional.get();
		}
		return tonnage >= vehicle.getWeight() && parkingSquare >= vehicle.getSquare() && place != null && place.getStatus();
	}
}
