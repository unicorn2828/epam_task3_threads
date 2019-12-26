package test.kononov.multithreading.producer;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.kononov.multithreading.entity.Vehicle;
import by.kononov.multithreading.producer.VehicleProducer;
import by.kononov.multithreading.type.VehicleType;

public class VehicleProducerTest{
	private VehicleProducer producer;
	private Queue<Vehicle> expectedList;
	private Queue<Vehicle> actualList;
	private List<String> badDataList;
	private List<String> dataList;

	@BeforeClass
	public void setUp() {
		producer = VehicleProducer.getInstance();
		expectedList = new LinkedList<Vehicle>();
		dataList = new ArrayList<String>();
		dataList.add("car 111 1.0 10.0");
		badDataList = new ArrayList<String>();
		badDataList.add("car 111 1.0 1.0 1.0");
		badDataList.add("car 001");
	}

	@AfterClass
	public void tierDown() {
		expectedList = null;
		actualList = null;
		badDataList = null;
		dataList = null;
	}

	@Test(description = "check production of correct vehicle")
	public void createehicleTestPositive() {
		actualList = producer.createVehicle(dataList);
		expectedList.add(new Vehicle(VehicleType.CAR, 111, 1.0, 10.0));
		assertEquals(actualList, expectedList);
	}

	@Test(description = "check production of incorrect vehicle")
	public void createVehicleTestNegative() {
		actualList = producer.createVehicle(badDataList);
		expectedList.clear();
		assertEquals(actualList, expectedList);
	}
}