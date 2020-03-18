package testClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import Object_Repository.ObjectRepository;

public class ReadObjects {

	static HashMap<String, String> newObject;
	static HashMap<String, String> newattributes;
	static HashMap<String, String> newObjectName;
	static HashMap<String, String> oldObject;
	static HashMap<String, String> oldObjectComments;
	static Map<String, String> hm = new LinkedHashMap<String, String>();
	static ArrayList<String> newlist = new ArrayList<String>();
	static WebElement element;
	static WebDriver driver;
	public static String AutoCommonPath =  ".\\src\\test\\java\\createdOR_SkeletonScript_CommonMethod\\";
	// ObjectFactory ob = new ObjectFactory();

	public void readObject() {

		// public static void main(String[] args) {
		String path = "./src/test/java/testdata/testdata1.xlsx";
		newObjectName = new HashMap<String, String>();
		System.out.println("Mappings values are: " + newObjectName);
		newObject = new HashMap<String, String>();
		newattributes = new HashMap<String, String>();
		String fieldText = null;

		File file = new File(path);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();
			System.out.println(rowCount);
			Row row = sheet.getRow(0);
			String objectName = null, attribute = null, fieldType = null;
			@SuppressWarnings("unused")
			int colCount = row.getLastCellNum();
			for (int i = 1; i <= rowCount; i++) {
				row = sheet.getRow(i);
				System.out.println(row.getCell(1).getStringCellValue());
				if (!(row.getCell(0) == null || row.getCell(0).toString().isEmpty())) {
					fieldText = row.getCell(0).getStringCellValue();
					System.out.println(fieldText);
				} else {
					fieldText = "VAR";
				}
				if (!(row.getCell(1) == null)) {
					fieldType = row.getCell(1).getStringCellValue();
				}
				/*
				 * if (!(row.getCell(2) == null || row.getCell(2).toString().isEmpty())) {
				 * attribute = "ID"; objectName = row.getCell(2).getStringCellValue(); } else if
				 * (!(row.getCell(3) == null || row.getCell(3).toString().isEmpty())) {
				 * attribute = "name"; objectName = row.getCell(3).getStringCellValue(); } else
				 * if (!(row.getCell(5) == null || row.getCell(5).toString().isEmpty())) {
				 * attribute = "class"; objectName = row.getCell(5).getStringCellValue(); } else
				 * if (!(row.getCell(9) == null || row.getCell(9).toString().isEmpty())) {
				 * attribute = "Link"; objectName = row.getCell(9).getStringCellValue(); } else
				 */

				if (!(row.getCell(10) == null || row.getCell(10).toString().isEmpty())) {
					attribute = "Xpath";
					objectName = row.getCell(10).getStringCellValue();
				}

				newObject.put(objectName, fieldType);
				newattributes.put(objectName, attribute);
				if (!(newObjectName.containsKey(objectName))) {
					newObjectName.put(objectName, fieldText);
					/*
					 * if (newObjectName.containsValue(fieldText)) { fieldText = fieldText + "_" +
					 * objectName; newObjectName.put(objectName, fieldText); } else {
					 * newObjectName.put(objectName, fieldText); }
					 */
				}
			}
			for (Entry<String, String> entry : newObject.entrySet()) {
				System.out.println(entry.getKey() + "-" + entry.getValue());
			}
			for (Entry<String, String> entry : newattributes.entrySet()) {
				System.out.println(entry.getKey() + "-" + entry.getValue());
			}
			changeClass();
			mapComparison();
			createGenericMethods();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeClass() throws IOException {
		String className = "ObjectRepository";
		oldObject = new HashMap<String, String>();
		oldObjectComments = new HashMap<String, String>();

		String indexValues = null;
		String path = System.getProperty("user.dir");
		//File javaFile = new File(path + "\\src\\test\\java\\testClass\\" + className + ".java");
		File javaFile = new File(AutoCommonPath + className + ".java");
		
		javaFile.createNewFile();

		if (javaFile.exists()) {
			System.out.println("Class file created with path: " + javaFile);
		} else {
			System.out.println("No file created");
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(javaFile));

			String st;
			while ((st = br.readLine()) != null) {
				String sPrefix = "public static final By";
				if (st.contains(sPrefix)) {

					int len = sPrefix.length();
					String variable = st.substring(st.indexOf(sPrefix));
					variable = variable.substring(len);
					System.out.println(variable);
					String variablKey = variable.substring(1, variable.indexOf("=")).trim();
					String variablValue = variable.substring(1, variable.indexOf("=") + 1).trim();

					// oldObject.put(variablKey.trim(), variablValue.trim());
					// oldObjectComments.put(variablKey.trim(), indexValues);
				} else {
					if (!st.isEmpty()) {
						indexValues = st;
					}
				}
			}

			for (Entry<String, String> entry : oldObject.entrySet()) {
				System.out.println(entry.getKey() + "-" + entry.getValue());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void mapComparison() throws IOException {
		String comparisonValue = null;
		String className = "ObjectRepository";
		HashMap<String, String> propertyChange = new HashMap<String, String>();
		File javFile = new File (AutoCommonPath + className + ".java");
		//File javFile = new File (".\\src\\test\\java\\createdOR_SkeletonScript_CommonMethod\\" + className + ".java");
		//File javFile = new File(".\\src\\test\\java\\testClass\\" + className + ".java");
		System.out.println(newObject);
		System.out.println(newObject.size());
		for (int i = 0; i <= newObject.size(); i++) {
			// System.out.println(newObject.get(key);
		}
		for (String k : newObject.keySet()) {
			if (!(newObject.get(k) == null || newObject.get(k).isEmpty())) {
				if (!oldObject.containsKey(newObject.get(k))) {
					System.out.println(k);
					newlist.add(k);
				} else {
					switch (newattributes.get(k)) {

					case "ID":
						comparisonValue = "By.id(\"" + k + "\")";
						break;

					case "name":
						comparisonValue = "By.name(\"" + k + "\")";
						break;

					case "Class":
						comparisonValue = "By.ClassName(\"" + k + "\")";
						break;

					case "Link":
						comparisonValue = "By.linkText(\"" + k + "\")";
						break;

					case "Xpath":
						comparisonValue = "By.xpath(\"" + k + "\")";
						break;

					default:
						break;
					}
					System.out.println(oldObject.get(newObjectName.get(k)) + "--------" + comparisonValue.toString());

					if (!oldObject.get(newObjectName.get(k)).equalsIgnoreCase(comparisonValue.toString()))
						;
					{
						oldObject.put(newObjectName.get(k), comparisonValue);
						propertyChange.put(newObjectName.get(k), "Yes");
					}
				}
			} else {
				if (!oldObject.containsKey(k)) {
					System.out.println(k);
					newlist.add(k);
				} else {

					switch (newattributes.get(k)) {
					case "ID":
						comparisonValue = "By.id(\"" + k + "\");";
						break;

					case "name":
						comparisonValue = "By.name(\"" + k + "\");";
						break;

					case "Class":
						comparisonValue = "By.className(\"" + k + "\");";
						break;

					case "Link":
						comparisonValue = "By.linkText(\"" + k + "\");";
						break;

					case "Xpath":
						comparisonValue = "By.xpath(\"" + k + "\");";
						break;

					default:
						break;
					}
					System.out.println(oldObject.get(k) + "---------" + comparisonValue.toString());
					if (!oldObject.get(k).equalsIgnoreCase(comparisonValue.toString()))
						;
					{
						oldObject.put(k, comparisonValue);
						propertyChange.put(k, "Yes");
					}

				}
			}
		}

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javFile)));
		String FileContent = null;
		String outputValue = null;
		writer.write("package createdOR_SkeletonScript_CommonMethod;\n\n");
		writer.write("import org.openqa.selenium.By;\n\n");
		writer.write("public class " + className + "{\n\n");
		writer.write("//Existing Objects");
		for (String key : oldObject.keySet()) {
			if (propertyChange.containsKey(key)) {
				String timeStamp = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new Date());
				writer.write("\n //Modification done on object property - " + timeStamp);
			} else if (!(oldObjectComments.get(key) == null)) {
				writer.write("\n\n" + oldObjectComments.get(key));
			}
			writer.write("\n\n public static final By " + key + " = " + oldObject.get(key));
		}

		for (int i = 0; i < newlist.size(); i++) {
			String newValue;
			if (!(newObjectName.get(newlist.get(i)) == null || newObjectName.get(newlist.get(i)).isEmpty())) {
				newValue = newObjectName.get(newlist.get(i));
				if (newObjectName.containsValue(newValue)) {
					newValue = (newValue + "_" + i).toUpperCase();
					System.out.println("New XPath added - " + newlist.get(i).toString() + "------" + newValue);

					if (!hm.containsValue(newlist.get(i).toString())) {
						hm.put(newlist.get(i).toString(), newValue);
						// System.out.println(hm);
					}
				}
			} else {
				newValue = newlist.get(i).toUpperCase();
			}
			String timeStamp = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new Date());
			writer.write("\n //New Object added - " + timeStamp);
			writer.write("\n\n public static final By " + newValue + " = ");
			String sAttribute = newattributes.get(newlist.get(i));

			switch (sAttribute) {
			case "ID":
				outputValue = "By.id(\"" + newlist.get(i) + "\")";
				break;

			case "name":
				outputValue = "By.name(\"" + newlist.get(i) + "\")";
				break;

			case "Class":
				outputValue = "By.className(\"" + newlist.get(i) + "\")";
				break;

			case "Xpath":
				outputValue = "By.xpath(\"" + newlist.get(i) + "\")";
				break;
			case "Link":
				outputValue = "By.linkText(\"" + newlist.get(i) + "\")";
				break;

			default:
				break;
			}
			writer.write(outputValue + ";\n");
		}
		// for (Map.Entry<String,String> entry : hm.entrySet())
		// System.out.println("Key = " + entry.getKey() +
		// ", Value = " + entry.getValue());
		writer.write("\n}\n");
		writer.close();

		String skelClassName = "SkeletonCode";

		//File javaskelFile = new File(".\\src\\test\\java\\testClass\\" + skelClassName + ".java");
		File javaskelFile = new File(AutoCommonPath + skelClassName + ".java");
		BufferedWriter SkelWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaskelFile)));

		SkelWriter.write("package createdOR_SkeletonScript_CommonMethod;\n\n");
		/*
		 * SkelWriter.write("import org.openqa.selenium.By;\n\n");
		 * 
		 * //SkelWriter.write("import Utilities.Wrapper;\n\n");
		 * SkelWriter.write("import org.openqa.selenium.WebDriver; \n\n");
		 */

		// SkelWriter.write("public class " + skelClassName + " extends Wrapper" +
		// "{\n\n");
		SkelWriter.write("public class " + skelClassName + " extends ObjectRepository" + "{\n\n");
		// SkelWriter.write("@SuppressWarnings(" +"null"+")" + "\n\n");
		SkelWriter.write("public static void main(String[] args)" + "{\n\n");
		SkelWriter.write("CommonMethods cm = new CommonMethods();" + "\n\n");
		SkelWriter.write("//New Skel Code for newly identified Objects \n\n");

		for (int i = 0; i < newlist.size(); i++) {
			// System.out.println(newlist.size());
			String new_Value = newObject.get(newlist.get(i));

			String newValue = newlist.get(i).toString();

			if (!(newObjectName.get(newlist.get(i)) == null || newObjectName.get(newlist.get(i)).isEmpty())) {

				newValue = newlist.get(i).toString();
			} else {
				newValue = newlist.get(i).toString();
			}
			System.out.println(newValue);
			System.out.println(new_Value);

			// for (Map.Entry<String,String> entry : hm.entrySet())
			// System.out.println("Key = " + entry.getKey() +
			// ", Value = " + entry.getValue());

			switch (new_Value.toUpperCase()) {

			case "TEXT":
				SkelWriter.write("	//Skeleton code for new object and need to add the field ' "
						+ hm.get(newlist.get(i)) + "' in test data sheet \n");
				SkelWriter.write("//enterInputText(\"" + hm.get(newlist.get(i)) + "\" , testdata.get(\""
						+ hm.get(newlist.get(i)) + "\"));\n\n");
				SkelWriter.write("   cm.verifyText_SoftAssert(\"" + hm.get(newlist.get(i)) + "\", (\""
						+ hm.get(newlist.get(i)) + "\"));\n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).getText();\n\n");
				System.out.println("Text method created");
				break;

			case "INPUT":
				SkelWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				SkelWriter.write("   cm.enterInputText(" + hm.get(newlist.get(i)) + ", " + "\"inputText\");\n\n");
				System.out.println("Input method created");
				break;

			case "RADIO":
				SkelWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				SkelWriter.write("   cm.actionClick(" + hm.get(newlist.get(i)) + ");\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).click();\n\n");
				break;

			case "CHECKBOX":
				SkelWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				SkelWriter.write("   cm.actionClick(" + hm.get(newlist.get(i)) + ");\n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).checked();\n\n");
				break;

			case "BUTTON":
				SkelWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				SkelWriter.write("   cm.buttonClick(" + hm.get(newlist.get(i)) + ");\n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).click();\n\n");
				break;

			case "SELECT":
				SkelWriter.write("	//Skeleton code for new object and need to add the field '" + hm.get(newlist.get(i))
						+ "' in test data sheet \n");
				// SkelWriter.write(" selectDropdownText(\"" + hm.get(newlist.get(i)) + "\",
				// (\"" + hm.get(newlist.get(i)) + "\"));\n\n");
				SkelWriter.write("   cm.selectDropdownText(\"" + newValue + "\", (\"" + newValue + "\"));\n\n");
				// SkelWriter.write(" actionClick(\"" + newValue + "\");\n\n");
				break;

			case "LINK":
				SkelWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				SkelWriter.write("   cm.actionClick(" + hm.get(newlist.get(i)) + ");\n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).getText(); \n\n");

				break;

			case "A":
				SkelWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				SkelWriter.write("   cm.actionClick(" + hm.get(newlist.get(i)) + ");\n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).getText(); \n\n");

				break;

			case "SPAN":
				SkelWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				SkelWriter.write("   verifyText_SoftAssert(\"" + hm.get(newlist.get(i)) + "\", testdata.get(\""
						+ hm.get(newlist.get(i)) + "\")); \n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).getText();\n\n");
				SkelWriter.write("   cm.actionClick(" + hm.get(newlist.get(i)) + ");\n\n");
				break;

			default:
				break;
			}
		}
		SkelWriter.write("\n  }\n");
		SkelWriter.write("\n}\n");
		SkelWriter.close();
	}

	public static void createGenericMethods() throws IOException {

		String GenericlClassName = "CommonMethods";

		//File javaGenFile = new File(".\\src\\test\\java\\testClass\\" + GenericlClassName + ".java");
		File javaGenFile = new File(AutoCommonPath + GenericlClassName + ".java");
		BufferedWriter GenWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaGenFile)));

		GenWriter.write("package createdOR_SkeletonScript_CommonMethod;\n\n");

		GenWriter.write("import org.openqa.selenium.WebDriver; \n");
		GenWriter.write("import org.openqa.selenium.WebElement; \n");
		GenWriter.write("import org.openqa.selenium.By; \n\n");

		GenWriter.write("public class " + GenericlClassName + " extends SkeletonCode" + "{\n\n");
		// SkelWriter.write("@SuppressWarnings(" +"null"+")" + "\n\n");
		GenWriter.write("//New Skel Code for newly identified Objects \n\n");
		GenWriter.write("	public static WebDriver driver; \n");
		Set<String> tempSet = new HashSet<>();
		for (int i = 0; i < newlist.size(); i++) {
			String new_Value = newObject.get(newlist.get(i));
			String newValue = newlist.get(i).toString();

			if (!tempSet.contains(new_Value)) {
				tempSet.add(new_Value);
			} else {
				continue;
			}

			if (!(newObjectName.get(newlist.get(i)) == null || newObjectName.get(newlist.get(i)).isEmpty())) {
				newValue = newlist.get(i).toString();
			} else {
				newValue = newlist.get(i).toString();
			}
			System.out.println(newValue);
			System.out.println(new_Value);

			switch (new_Value.toUpperCase()) {

			case "TEXT":
				GenWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("//enterInputText(\"" + hm.get(newlist.get(i)) + "\" , testdata.get(\""
						+ hm.get(newlist.get(i)) + "\"));\n\n");
				GenWriter.write("   verifyText_SoftAssert(\"" + hm.get(newlist.get(i)) + "\", (\""
						+ hm.get(newlist.get(i)) + "\"));\n\n");
				break;

			case "INPUT":
				GenWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("  public void enterInputText(By xpath, String text){ \n\n");
				GenWriter.write("  WebElement element; \n\n");
				GenWriter.write("		element = " + "driver.findElement(xpath); \n");
				GenWriter.write("		if(element.isDisplayed()" + ")" + "{" + "\n");
				GenWriter.write("			element.sendKeys(text)" + ";" + "\n");
				GenWriter.write("			System.out.println" + "(\"" + "Text written" + "\"); \n");
				GenWriter.write("		}" + "\n");
				GenWriter.write("		else" + "\n");
				GenWriter.write("			{" + "\n");
				GenWriter.write(
						"				System.out.println" + "(\"" + "Failing because of wrong Element" + "\"); \n");
				GenWriter.write("			}" + "\n");
				GenWriter.write("}" + "\n");

				break;

			case "RADIO":
				GenWriter.write("	//Skeleton code for new object" + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("  public void radioClick(\"" + hm.get(newlist.get(i)) + "\"){ \n\n");

				break;

			case "CHECKBOX":
				GenWriter.write("	//Skeleton code for new object" + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("   checkBoxSelect(\"" + hm.get(newlist.get(i)) + "\"){ \n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).checked();\n\n");
				break;

			case "BUTTON":
				GenWriter.write("	//Skeleton code for new object" + " " + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("  public void buttonClick(By xpath){\n\n");
				GenWriter.write("  WebElement element; \n");
				GenWriter.write("		element = " + "driver.findElement(xpath); \n");
				GenWriter.write("		if(element.isDisplayed()" + ")" + "{" + "\n");
				GenWriter.write("			element.click()" + ";" + "\n");
				GenWriter.write("			System.out.println" + "(\"" + "Button clicked" + "\"); \n");
				GenWriter.write("		}" + "\n");
				GenWriter.write("		else" + "\n");
				GenWriter.write("			{" + "\n");
				GenWriter.write(
						"				System.out.println" + "(\"" + "Failing because of wrong Element" + "\"); \n");
				GenWriter.write("			}" + "\n");
				GenWriter.write("}" + "\n");

				break;

			case "SELECT":
				GenWriter.write("	//Skeleton code for new object and need to add the field '" + hm.get(newlist.get(i))
						+ "' in test data sheet \n");
				// SkelWriter.write(" selectDropdownText(\"" + hm.get(newlist.get(i)) + "\",
				// (\"" + hm.get(newlist.get(i)) + "\"));\n\n");
				GenWriter.write("   selectDropdownText(\"" + newValue + "\", (\"" + newValue + "\")){ \n\n");

				break;

			case "LINK":
				GenWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("  public void linkTagClick(By xpath){\n\n");
				GenWriter.write("  WebElement element; \n\n");
				GenWriter.write("		element = " + "driver.findElement(xpath); \n");
				GenWriter.write("		if(element.isDisplayed()" + ")" + "{" + "\n");
				GenWriter.write("			element.click()" + ";" + "\n");
				GenWriter.write(
						"			System.out.println" + "(\"" + "Testcase passing for correct testcase" + "\"); \n");
				GenWriter.write("		}" + "\n");
				GenWriter.write("		else" + "\n");
				GenWriter.write("			{" + "\n");
				GenWriter.write(
						"			System.out.println" + "(\"" + "Failing because of wrong Element" + "\"); \n");
				GenWriter.write("			}" + "\n");
				GenWriter.write("}" + "\n");

				break;

			case "A":
				GenWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("  public void linkClick(By xpath){\n\n");
				GenWriter.write("  WebElement element; \n\n");
				GenWriter.write("		element = " + "driver.findElement(By.xpath(xpath)); \n");
				GenWriter.write("		if(element.isDisplayed()" + ")" + "{" + "\n");
				GenWriter.write("			element.click()" + ";" + "\n");
				GenWriter.write("			System.out.println" + "(\"" + "Testcase passed" + "\"); \n");
				GenWriter.write("		}" + "\n");
				GenWriter.write("		else" + "\n");
				GenWriter.write("			{" + "\n");
				GenWriter.write(
						"			System.out.println" + "(\"" + "Failing because of wrong Element" + "\"); \n");
				GenWriter.write("			}" + "\n");
				GenWriter.write("}" + "\n");

				break;

			case "SPAN":
				GenWriter.write("	//Skeleton code for new object " + hm.get(newlist.get(i)) + "\n");
				GenWriter.write("   verifyText_SoftAssert(\"" + hm.get(newlist.get(i)) + "\", testdata.get(\""
						+ hm.get(newlist.get(i)) + "\")); \n\n");
				// SkelWriter.write(" driver.findElement(By.xpath(\"" + newValue +
				// "\")).getText();\n\n");
				GenWriter.write("   actionClick(\"" + hm.get(newlist.get(i)) + "\"){ \n\n");
				break;

			default:
				break;
			}
		}
		GenWriter.write("\n  }\n");
		GenWriter.close();
	}
}