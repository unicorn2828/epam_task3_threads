package by.kononov.multithreading.action;

import java.util.Queue;

import by.kononov.multithreading.entity.Riverside;
import by.kononov.multithreading.entity.Vehicle;

public interface FerryAction{

	void loadFerry(Riverside riversideA, Queue<Vehicle> ferryBody, double tonnage, double ferryParkingSquare);

	void moveFerry();

	void unloadFerry(Riverside riversideB, Queue<Vehicle> ferryBody);
}
