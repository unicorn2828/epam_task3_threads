package by.kononov.multithreading.printer;

import java.util.Iterator;
import java.util.function.Supplier;

import org.apache.logging.log4j.Logger;

import by.kononov.multithreading.entity.Riverside;
import by.kononov.multithreading.entity.Vehicle;

/**
 * This is the MessagePrinter class; it is used to print information
 * on console.
 * 
 * @author Vitaly Kononov
 * @since 2019-12-12
 */
public class MessagePrinter{

	/**
	 * This is the private constructor of this class; it makes it possible
	 * to use only the public static class method.
	 * 
	 */
	private MessagePrinter() {
	}

	/**
	 * This method prints infomation about amount of vehicles on a
	 * riverside.
	 * 
	 * @param logger  - the logger for print info on console.
	 * @param message - the text message.
	 * @param storage - the list of cars.
	 */
	public static void printRiversideInformation(Logger logger, Supplier<String> message, Riverside storage) {
		logger.info(message.get());
		Iterator<Vehicle> vehicleIterator = storage.iterator();
		while (vehicleIterator.hasNext()) {
			logger.info("#{}", vehicleIterator.next().getVehicleId());
		}
	}
}
