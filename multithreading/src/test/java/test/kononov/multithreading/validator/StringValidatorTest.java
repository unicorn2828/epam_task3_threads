package test.kononov.multithreading.validator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.kononov.multithreading.validator.StringValidator;

public class StringValidatorTest{
	private StringValidator validator;

	@BeforeClass
	public void setUp() {
		validator = new StringValidator();
	}

	@AfterClass
	public void tierDown() {
		validator = null;
	}

	@DataProvider
	public Object[][] correctStrings() {
		return new Object[][] {
				{ "car 111 2.0 10.5" },
				{ "truck 123456789 15.1 32.8" },
				{ "car 001 10.0 14.14" },
				{ "truck 1 28.1156 50.0" } };
	}

	@Test(dataProvider = "correctStrings", description = "check correct strings")
	public void isCorrectStringPositive(String correctString) {
		boolean condition = validator.isCorrectString(correctString);
		assertTrue(condition);
	}

	@DataProvider
	public Object[][] incorrectStrings() {
		return new Object[][] {
				{ "car 1.0 1.0" },
				{ "car mazda 111 1.0" },
				{ "" },
				{ "111 1.0" },
				{ "truck 001" },
				{ "truck 1z01 20.0" },
				{ " " }, };
	}

	@Test(dataProvider = "incorrectStrings", description = "check incorrect strings")
	public void isCorrectStringNegative(String incorrectString) {
		boolean condition = validator.isCorrectString(incorrectString);
		assertFalse(condition);
	}
}