package by.kononov.multithreading.parser;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import by.kononov.multithreading.type.VehicleDataType;
import by.kononov.multithreading.validator.StringValidator;

public class StringParser{

	public List<String> checkString(List<String> listDataLines) {
		StringValidator stringValidator = new StringValidator();
		List<String> autoDataList = new ArrayList<>();
		listDataLines.forEach(dataLine -> {
			if (stringValidator.isCorrectString(dataLine)) {
				autoDataList.add(dataLine);
			}
		});
		return autoDataList;
	}

	public Map<VehicleDataType, String> parseString(String autoDataString) {
		Map<VehicleDataType, String> values = new EnumMap<>(VehicleDataType.class);
		try (Scanner scanner = new Scanner(autoDataString)) {
			String type = scanner.next();
			values.put(VehicleDataType.TYPE, type);
			String id = scanner.next();
			values.put(VehicleDataType.ID, id);
			String weight = scanner.next();
			values.put(VehicleDataType.WEIGHT, weight);
			String square = scanner.next();
			values.put(VehicleDataType.SQUARE, square);
		}
		return values;
	}
}