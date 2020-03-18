package testClass;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class AngularTest extends ExcelWriter15 {

	public static Map<String, String> mp = new HashMap<String, String>();

	@Test
	public static void sampleTest() throws Exception {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		d = new ChromeDriver();
	//	d.get("http://www.way2automation.com/angularjs-protractor/banking/#/manager/addCust");
		d.get("https://s2b.standardchartered.com/unifiedlogin/login/index.html?source=classic");
		
		designAccelator(d);
		ReadObjects obj = new ReadObjects();
		obj.readObject();
	}

	@AfterClass
	public static void tearDown() {
		d.quit();
	}
}
