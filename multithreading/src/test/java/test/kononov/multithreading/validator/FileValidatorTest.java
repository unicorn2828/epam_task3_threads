package test.kononov.multithreading.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.kononov.multithreading.validator.FileValidator;

public class FileValidatorTest{
	private static final String FILE_NAME = "data/vehicleData.txt";
	private static final String EMPTY_FILE_NAME = "testData/emptyFile.txt";
	private static final String FAKE_FILE_NAME = "wrong/path/vehicleData.txt";
	private FileValidator validator;

	@BeforeClass
	public void setUp() {
		validator = new FileValidator();
	}

	@AfterClass
	public void tierDown() {
		validator = null;
	}

	@Test(description = "check correct file")
	public void isFilePositive() {
		boolean condition = validator.isFile(FILE_NAME);
		assertTrue(condition);
	}

	@Test(description = "check incorrect file")
	public void isFileNegative() {
		boolean condition = validator.isFile(FAKE_FILE_NAME);
		assertFalse(condition);
	}

	@Test(description = "check empty file")
	public void isFileEmptyTest() {
		boolean condition = validator.isFile(EMPTY_FILE_NAME);
		assertFalse(condition);
	}
}