package by.kononov.multithreading.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kononov.multithreading.exception.CustomException;
import by.kononov.multithreading.validator.FileValidator;

public class DataReader{
	static final Logger logger = LogManager.getLogger();

	public List<String> readFile(String filePath) throws CustomException {
		FileValidator validator = new FileValidator();
		if (!validator.isFile(filePath)) {
			throw new CustomException("bad file or file doesn't exist " + filePath);
		}
		List<String> linesList = new ArrayList<>();
		try {
			linesList = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(filePath).toURI()));
		} catch (IOException | URISyntaxException e) {
			logger.error("can't be read data: " + filePath, e);
			throw new CustomException("can't be read data: " + filePath, e);
		}
		return linesList;
	}
}