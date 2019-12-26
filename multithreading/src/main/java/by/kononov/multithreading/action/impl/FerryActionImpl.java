package by.kononov.multithreading.action.impl;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kononov.multithreading.action.FerryAction;
import by.kononov.multithreading.entity.RiverFerry;
import by.kononov.multithreading.entity.Riverside;
import by.kononov.multithreading.entity.Vehicle;
import by.kononov.multithreading.printer.MessagePrinter;
import by.kononov.multithreading.validator.VehicleValidator;

public class FerryActionImpl implements FerryAction{
	static final Logger logger = LogManager.getLogger();

	@Override
	public void loadFerry(Riverside riversideA, Queue<Vehicle> ferryBody, double tonnage, double parkingSquare) {
		VehicleValidator validator = new VehicleValidator();
		MessagePrinter.printRiversideInformation(logger, () -> "There are vehicles on the riverside A: ", riversideA);
		int count = riversideA.getVehicleList().size();
		for (int i = 0; i < count; i++) {
			Vehicle vehicle = riversideA.getVehicle();
			if (validator.isValidAuto(vehicle, tonnage, parkingSquare)) {
				ferryBody.add(vehicle);
				logger.info("-> The {} #{} is loaded on RiverFerry-{}", vehicle.getType(), vehicle.getVehicleId(),
						RiverFerry.FERRY_NAME);
				tonnage -= vehicle.getWeight();
				parkingSquare -= vehicle.getSquare();
				logger.info("The rest of tonnage: {}", tonnage);
				logger.info("The rest of parking's square: {}", parkingSquare);
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					logger.error("The thread can not sleep; it throws an exception: ", e);
					Thread.currentThread().interrupt();
				}
			} else {
				riversideA.setVehicle(vehicle);
			}
		}
	}

	@Override
	public void moveFerry() {
		try {
			logger.info("<-> The RiverFerry-{} sailed off.", RiverFerry.FERRY_NAME);
			TimeUnit.SECONDS.sleep(new Random(2).nextInt(3));
			logger.info("<-> The RiverFerry-{} arrived.", RiverFerry.FERRY_NAME);
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			logger.error("The thread can't sleep; it throws an exception: ", e);
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void unloadFerry(Riverside riversideB, Queue<Vehicle> ferryBody) {
		while (!ferryBody.isEmpty()) {
			Vehicle vehicle = ferryBody.poll();
			riversideB.setVehicle(vehicle);
			vehicle.getSemaphore().release();
			logger.info("<- The {} #{} is unloaded from RiverFerry-{}", vehicle.getType(), vehicle.getVehicleId(),
					RiverFerry.FERRY_NAME);
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				logger.error("The thread can't sleep; it throws an exception: ", e);
				Thread.currentThread().interrupt();
			}
		}
		MessagePrinter.printRiversideInformation(logger, () -> "There are vehicles on the riverside B: ", riversideB);
	}
}