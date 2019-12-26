package by.kononov.multithreading.producer;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import by.kononov.multithreading.entity.Vehicle;
import by.kononov.multithreading.parser.StringParser;
import by.kononov.multithreading.type.VehicleDataType;
import by.kononov.multithreading.type.VehicleType;

public class VehicleProducer{
	private final static VehicleProducer INSTANCE = new VehicleProducer();

	private VehicleProducer() {
	}

	public static VehicleProducer getInstance() {
		return INSTANCE;
	}

	public Queue<Vehicle> createVehicle(List<String> data) {
		Queue<Vehicle> vehicleList = new ArrayDeque<>();
		StringParser parser = new StringParser();
		List<String> vehicleData = parser.checkString(data);
		vehicleData.forEach(vehicleDataString -> {
			Map<VehicleDataType, String> values = parser.parseString(vehicleDataString);
			Vehicle vehicle = createVehicle(values.get(VehicleDataType.TYPE), values.get(VehicleDataType.ID),
					values.get(VehicleDataType.WEIGHT), values.get(VehicleDataType.SQUARE));
			vehicleList.add(vehicle);
		});
		return vehicleList;
	}

	private Vehicle createVehicle(String type, String id, String weight, String square) {
		VehicleType vehicleType = VehicleType.valueOf(type.toUpperCase());
		long vehicleId = Long.parseLong(id);
		double vehicleWeight = Double.parseDouble(weight);
		double vehicleSquare = Double.parseDouble(square);
		return new Vehicle(vehicleType, vehicleId, vehicleWeight, vehicleSquare);
	}
}