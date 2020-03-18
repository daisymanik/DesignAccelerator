package testClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Properties {
	
	public static void main(String args[]) throws IOException {
		File file = new File("test.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		//properties.load(fileInput);
	}
}
