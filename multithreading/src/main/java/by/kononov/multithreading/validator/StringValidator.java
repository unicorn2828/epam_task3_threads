package by.kononov.multithreading.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the StringValidator class; it is used to check the string
 * before parsing; it contains the regex for checking string and method
 * which returns the boolean value.
 * 
 * @author Vitaly Kononov
 * @since 2019-12-12
 */
public class StringValidator{
	private final static Pattern VEHICLE_REGEX =
			Pattern.compile("([\\w+]{1})+[\\s]{1}+([\\d+]{1})+([\\s]{1}+[\\d+]+[\\.]{1}[\\d+]+){2}");

	public boolean isCorrectString(String string) {
		Matcher matcher = VEHICLE_REGEX.matcher(string);
		return matcher.matches();
	}
}