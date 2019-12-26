package test.kononov.multithreading.reader;

import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.kononov.multithreading.exception.CustomException;
import by.kononov.multithreading.reader.DataReader;

public class ReaderTest{
	private static final String FILE_NAME = "data/vehicleData.txt";
	private static final String FAKE_FILE_NAME = "wrong/path/vehicleData.txt";
	private DataReader dataReader;

	@BeforeClass
	public void setUp() {
		dataReader = new DataReader();
	}

	@AfterClass
	public void tierDown() {
		dataReader = null;
	}

	@Test(description = "check if the file can be read")
	public void readFileTestPositive() throws CustomException {
		List<String> actual = dataReader.readFile(FILE_NAME);
		assertNotNull(actual);
	}

	@Test(timeOut = 500, description = "check for exception if the file can't be read longer then specified time")
	public void readFileTestTime() throws CustomException {
		dataReader.readFile(FILE_NAME);
	}

	@Test(expectedExceptions = CustomException.class, description = "check for exception if the file can't be read")
	public void readFileExceptionTest() throws CustomException {
		dataReader.readFile(FAKE_FILE_NAME);
	}
}