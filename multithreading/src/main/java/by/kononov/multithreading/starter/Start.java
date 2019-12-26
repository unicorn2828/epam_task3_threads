package by.kononov.multithreading.starter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kononov.multithreading.entity.RiverFerry;
import by.kononov.multithreading.entity.Riverside;
import by.kononov.multithreading.entity.Vehicle;
import by.kononov.multithreading.exception.CustomException;
import by.kononov.multithreading.producer.VehicleProducer;
import by.kononov.multithreading.reader.DataReader;

public class Start{
	static final Logger logger = LogManager.getLogger();
	private static final String FILE_PATH = "data/vehicleData.txt";

	public static void main(String[] args) {
		DataReader dataReader = new DataReader();
		VehicleProducer producer = VehicleProducer.getInstance();
		List<String> dataList = new ArrayList<>();
		try {
			dataList = dataReader.readFile(FILE_PATH);
		} catch (CustomException e) {
			logger.error("can't be read data from file: ", e);
		}
		Queue<Vehicle> vehicleList = producer.createVehicle(dataList);
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			for (Vehicle vehicle : vehicleList) {
				executor.submit(vehicle);
				TimeUnit.MILLISECONDS.sleep(100);
			}
			RiverFerry ferry = RiverFerry.getInstance();
			ferry.setRiversideA(Riverside.createStorage(vehicleList.size(), vehicleList));
			ferry.setRiversideB(Riverside.createStorage(vehicleList.size()));
			Future<Integer> future = executor.submit(ferry);
			executor.shutdown();
			logger.info("REPORT: the ferry has completed {} shipments.", future.get());
		} catch (InterruptedException | ExecutionException e) {
			logger.error("The thread can't sleep; it throws an exception: ", e);
			Thread.currentThread().interrupt();
		}
	}
}