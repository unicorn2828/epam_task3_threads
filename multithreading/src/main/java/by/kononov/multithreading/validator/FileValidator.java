package by.kononov.multithreading.validator;

import java.io.File;
import java.net.URL;

/**
 * This is the FileValidator class; it is used to check the file
 * before reading.
 * 
 * @author Vitaly Kononov
 * @since 2019-12-12
 */
public class FileValidator{

	public boolean isFile(String filePath) {
		URL resource = getClass().getClassLoader().getResource(filePath);
		File file = null;
		if (resource != null) {
			file = new File(resource.getFile());
		}
		return file != null && !file.isDirectory() && file.exists() && file.length() != 0;
	}
}
