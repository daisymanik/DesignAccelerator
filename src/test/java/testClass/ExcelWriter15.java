package testClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import BusinessMethods.Login_Page;

public class ExcelWriter15 {

	static int newFlag = 0;
	static int createsheet = 0;
	static XSSFWorkbook wwb;
	static XSSFSheet sheet, sheet1;
	static File filepath;
	static File file;
	static String parentValue;
	static String grandParentValue;
	static String parent;
	static String grandParent;
	static String precedingSibling;
	static String parentSibling;
	static String followingSibling;
	static String parentSiblingChild;
	static String fieldText = null;
	static String xpath = null;

	static StringBuilder placeHolder = new StringBuilder();
	static StringBuilder id = new StringBuilder();
	static StringBuilder name = new StringBuilder();
	static StringBuilder classname = new StringBuilder();
	static StringBuilder fieldvalue = new StringBuilder();
	static StringBuilder title = new StringBuilder();
	static StringBuilder valuee = new StringBuilder();
	static StringBuilder password = new StringBuilder();

	public static String xpathConstructor;

	public static String[] storeclasses;

	public static String tagName;
	public static String newXpath = "";
	public static String updateXpath = "";
	public static String tempXpath = "";

	public static WebDriver d;
	static List<String> storeXpath = new ArrayList<String>();
	public static Set<String> xpathSet = new HashSet<String>();
	public static Set<String> xpathArray = new HashSet<String>();
	public static List<String> tempStrArray = new ArrayList<String>();

	/*
	 * Method: designAccelator() What does it do? : Enter the header values in the
	 * excel sheet with headerUpdateWorkbook() Find the WebElements from the web
	 * page with the specified tags and send it to generatelocators()
	 */
	public static void designAccelator(WebDriver driver) throws Exception {
		filepath = new File("./src/test/java/testdata/testdata1.xlsx");
		if (filepath.exists() || newFlag == 0) {
			CreateWorkbook();
			newFlag = 1;
		}

		headerUpdateWorkbook();

		List<WebElement> linkElements3 = driver.findElements(By.tagName("select"));
		for (int i = 0; i < linkElements3.size(); i++) {
			if (!tempStrArray.isEmpty()) {
				tempStrArray.removeAll(tempStrArray);
			}
			System.out.println(linkElements3.size());
			System.out.println(i + "select size");
			generatelocators("select", linkElements3.get(i), driver);
		}

		List<WebElement> linkElements5 = driver.findElements(By.tagName("button"));
		for (int i = 0; i < linkElements5.size(); i++) {
			if (!tempStrArray.isEmpty()) {
				tempStrArray.removeAll(tempStrArray);
			}
			System.out.println(i + "button size");
			generatelocators("button", linkElements5.get(i), driver);
		}

		List<WebElement> linkElements6 = driver.findElements(By.tagName("a"));
		int LinkCount = linkElements6.size();
		System.out.println(LinkCount);
		for (WebElement links : linkElements6) {
			if (links != null) {
				if (!tempStrArray.isEmpty()) {
					tempStrArray.removeAll(tempStrArray);
				}
				System.out.println(links.getText() + " - " + links.getAttribute("href"));
				generatelocators("a", links, driver);
			}
		}

		List<WebElement> linkElements7 = driver.findElements(By.tagName("img"));
		for (int i = 0; i < linkElements7.size(); i++) {
			if (!tempStrArray.isEmpty()) {
				tempStrArray.removeAll(tempStrArray);
			}
			System.out.println(linkElements7.size());
			System.out.println(i + "select size");
			generatelocators("img", linkElements7.get(i), driver);
		}

		List<WebElement> linkElements2 = driver.findElements(By.tagName("input"));
		for (int i = 0; i < linkElements2.size(); i++) {
			if (!tempStrArray.isEmpty()) {
				tempStrArray.removeAll(tempStrArray);
			}
			System.out.println(linkElements2.size());
			System.out.println(i + "select size");
			generatelocators("input", linkElements2.get(i), driver);
		}

	}

	/*
	 * Method: generatelocators() What does it do? : Generate different locator
	 * values and pass it to xpathGenerator() to create xPaths
	 */
	public static void generatelocators(String tag, WebElement element, WebDriver driver) throws Exception {

		// if (storeXpath.isEmpty()) {

		StringBuilder place = placeHolder.append(element.getAttribute("placeholder"));
		System.out.println(place);
		System.out.println("Place length = " + placeHolder.length());
		if (placeHolder.length() > 1) {
			placeHolder.delete(0, placeHolder.length());
			placeHolder.append(element.getAttribute("placeholder"));
		}
		StringBuilder idd = id.append(element.getAttribute("id"));
		System.out.println(idd);
		if (id.length() > 1) {
			id.delete(0, id.length());
			id.append(element.getAttribute("id"));
		}
		StringBuilder namee = name.append(element.getAttribute("name"));
		System.out.println(namee);
		if (name.length() > 1) {
			name.delete(0, name.length());
			name.append(element.getAttribute("name"));
		}

		StringBuilder classs = classname.append(element.getAttribute("class"));
		System.out.println(classs);
		System.out.println("classs length = " + classname.length());

		// printing class name single only (not concating all) (Soumya)
		if (classname.length() > 1) {
			classname.delete(0, classname.length());
			classname.append(element.getAttribute("class"));
		}
		StringBuilder valuee = fieldvalue.append(element.getAttribute("value"));
		System.out.println(valuee);
		if (fieldvalue.length() > 1) {
			fieldvalue.delete(0, fieldvalue.length());
			fieldvalue.append(element.getAttribute("value"));
		}
		StringBuilder titlee = title.append(element.getAttribute("title"));
		System.out.println(titlee);
		if (title.length() > 1) {
			title.delete(0, title.length());
			title.append(element.getAttribute("title"));
		}
		StringBuilder passwordd = password.append(element.getAttribute("password"));
		System.out.println(passwordd);
		if (password.length() > 1) {
			password.delete(0, password.length());
			password.append(element.getAttribute("password"));
		}

		System.out.println("Entered fetching Select objects");

		xpathGenerator(element, tag, driver);

		for (String xpath : tempStrArray) {
			String FieldText = xpath.substring(xpath.indexOf("~$") + 2);
			xpath = xpath.substring(0, xpath.indexOf("~$"));

			if (xpath.toString().contains("null") && (xpath.toString().endsWith("null"))) {
				xpath = null;
			} else {
				xpath = xpath.toString();
				dataUpdateWorkbook(FieldText, tag, id.toString(), name.toString(), "", classname.toString(),
						valuee.toString(), placeHolder.toString(), title.toString(), "", xpath,
						generateGrandParentSibling(xpath), generateGrandParent(xpath),
						generateParentSiblingChild(xpath), generateParentSibling(xpath), generateParent(xpath),
						generatePrecedingSibling(xpath), generateFollowingSibling(xpath));
			}
		}
	}

	/*
	 * Method: CreateWorkbook() What does it do? : Create a new workbook and
	 * generate the sheet name
	 */
	public static void CreateWorkbook() {

		if (createsheet == 0) {

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

			try {
				System.out.println("Create a Workbook");
				file = new File(dateFormat.format(date));
				System.out.println(file);
				FileOutputStream outStream = new FileOutputStream(filepath);
				wwb = new XSSFWorkbook();
				sheet = wwb.createSheet("DesignAcclerator - " + file);

				wwb.write(outStream);
				outStream.close();
				createsheet = 1;

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (createsheet == 1) {
			sheet1 = wwb.createSheet("Sheet2 - " + file);
		}
	}

	/*
	 * Method: headerUpdateWorkbook() What does it do? : Enter the header values in
	 * the excel sheet
	 */
	public static void headerUpdateWorkbook() throws IOException {
		FileInputStream inputstreams = new FileInputStream(filepath);
		wwb = new XSSFWorkbook(inputstreams);
		sheet = wwb.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		System.out.println(rowCount);

		if (rowCount < 1) {
			Row row = sheet.createRow(0);
			row.createCell(0).setCellValue("Field_Text");
			row.createCell(1).setCellValue("Field_Type");
			row.createCell(2).setCellValue("Attribute_ID");
			row.createCell(3).setCellValue("Attribute_Name");
			row.createCell(4).setCellValue("Attribute_InnerText");
			row.createCell(5).setCellValue("Attribute_Class");
			row.createCell(6).setCellValue("Attribute_Value");
			row.createCell(7).setCellValue("Attribute_Placeholder");
			row.createCell(8).setCellValue("Attribute_Title");
			row.createCell(9).setCellValue("Attribute_TextValue");
			row.createCell(10).setCellValue("xpath");

			row.createCell(11).setCellValue("Grand_ParentFollow_sibling");
			row.createCell(12).setCellValue("Grand_Parent_sibling");
			row.createCell(13).setCellValue("Grand_Parent");
			row.createCell(14).setCellValue("Parent_sib_child");
			row.createCell(15).setCellValue("Parent_sibling");
			row.createCell(16).setCellValue("Parent");
			row.createCell(17).setCellValue("PrecedingSibling");
			row.createCell(18).setCellValue("FollowingSibling");

		}

		inputstreams.close();
		FileOutputStream outStream = new FileOutputStream(filepath);
		wwb.write(outStream);
		outStream.close();
	}

	public static void dataUpdateWorkbook(String sf1, String sf2, String sf3, String sf4, String sf5, String sf6,
			String sf7, String sf8, String sf9, String sf10, String sf11, String sf13, String sf14, String sf15,
			String sf16, String sf17, String sf18, String sf19) throws IOException {
		System.out.println("Xpath Value: " + sf11);
		System.out.println("Sample");
		FileInputStream inputstreams = new FileInputStream(filepath);
		wwb = new XSSFWorkbook(inputstreams);
		sheet = wwb.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		System.out.println(rowCount);
		// soumya
		System.out.print("Original xpath" + sf11);
		if (sf11.contains("following-sibling")) {
			String[] temp = sf11.split("::");
			sf11 = temp[1];
			System.out.println("xpath in temp is " + sf11);
		}

		Row row = sheet.createRow(rowCount + 1);
		System.out.println(sf1);
		sf1 = sf1.replace(":", "").trim();
		sf1 = sf1.replace("*", "").trim();
		row.createCell(0).setCellValue(sf1.replaceAll(" ", "_").trim());
		row.createCell(1).setCellValue(sf2);
		row.createCell(2).setCellValue(sf3);
		row.createCell(3).setCellValue(sf4);

		row.createCell(4).setCellValue(sf5);
		row.createCell(5).setCellValue(sf6);
		row.createCell(6).setCellValue(sf7);
		row.createCell(7).setCellValue(sf8);
		row.createCell(8).setCellValue(sf9);
		row.createCell(9).setCellValue(sf10);
		row.createCell(10).setCellValue(sf11);
		row.createCell(12).setCellValue(sf13);
		row.createCell(13).setCellValue(sf14);
		row.createCell(14).setCellValue(sf15);
		row.createCell(15).setCellValue(sf16);
		row.createCell(16).setCellValue(sf17);
		row.createCell(17).setCellValue(sf18);
		row.createCell(18).setCellValue(sf19);

		inputstreams.close();
		FileOutputStream outStream = new FileOutputStream(filepath);
		wwb.write(outStream);
		outStream.close();
	}

	/*
	 * Method: xpathGenerator() What does it do? : Create xPaths with specified tags
	 * and attributes
	 */
	@SuppressWarnings({ "null", "unused" })
	public static void xpathGenerator(WebElement linkElements, String type, WebDriver driver) {

		String[] strFlow = { "xpath", "FollowingSibling", "PrecedingSibling", "Parent", "Parent_sibling",
				"Grand_Parent", "Grand_Parent_sibling", "Grand_ParentFollow_sibling" };
		System.out.println("strflow size" + strFlow.length);

		int identifiedFlag = 0;

		// Adding commonly used attributes to a List of String
		List<String> attributeList = new ArrayList<>();
		attributeList.add("id");
		attributeList.add("name");
		attributeList.add("class");
		attributeList.add("href");
		attributeList.add("src");
		attributeList.add("value");
		attributeList.add("type");
		attributeList.add("title");
		attributeList.add("text");
		attributeList.add("file");
		attributeList.add("radio");
		attributeList.add("checkbox");
		attributeList.add("placeholder");
		attributeList.add("ng-class");

		// Adding commonly used tags to a List of String
		List<String> tagNames = new ArrayList<>();
		tagNames.add("input");
		tagNames.add("select");
		tagNames.add("button");
		tagNames.add("a");
		tagNames.add("img");
		tagNames.add("text");

		try {
			if (type.equalsIgnoreCase(linkElements.getTagName())) {
				for (String tag : tagNames) {
					if (tag.equalsIgnoreCase(type)) {
						for (String attribute : attributeList) {
							String attributeValue = linkElements.getAttribute(attribute);
							if (attributeValue == null) {
								continue;
							}
							if (!attributeValue.isEmpty()) {

								if (attributeValue.contains("/")) {
									String[] newAttribute = attributeValue.split("/");
									newXpath = "//" + tag + "[contains(@" + attribute + ",'"
											+ newAttribute[newAttribute.length - 1] + "')]";
									System.out.println(newXpath);
									tempXpath = newXpath;
									xpathSet.add(newXpath);
									break;
								} else if (attribute.equalsIgnoreCase("text")) {
									newXpath = "//" + tag + "[contains(" + attribute + "(),'" + attributeValue + "')]";
									System.out.println(newXpath);
									tempXpath = newXpath;
									xpathSet.add(newXpath);
									break;
								} else {
									newXpath = "//" + tag + "[@" + attribute + "='" + attributeValue + "']";
									System.out.println(newXpath);
									tempXpath = newXpath;
									xpathSet.add(newXpath);
									break;
								}
							} else {
								System.out.println("No " + attribute + " present..");
							}
						}
						if (xpathSet.contains(tempXpath)) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// code for containing XPath more than once
		try {
			for (String str : xpathSet) {
				List<WebElement> assoElements = driver.findElements(By.xpath(str));
				System.out.println("Xpath array size:" + xpathSet.size());
				System.out.println("assoElements array size:" + assoElements.size());
				if (assoElements.size() > 1) {
					for (int i = 0; i < assoElements.size(); i++) {
						updateXpath = "(" + str + ")" + "[" + (i + 1) + "]";
						if (xpathArray.contains(updateXpath)) {
							break;
						} else {
							xpathArray.add(updateXpath);
							storeXpath.add(updateXpath);
						}
					}
					System.out.println(xpathSet.size());
					continue;
				} else if (!xpathArray.contains(str)) {
					xpathArray.add(str);
					storeXpath.add(str);
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for (String storedXpath : storeXpath) {
			fieldText = driver.findElement(By.xpath(storedXpath)).getText();
			List<WebElement> siblingElements = driver.findElements(By.xpath(storedXpath + "/preceding-sibling::*"));
			if (fieldText == null || fieldText.isEmpty()) {
				for (WebElement tag : siblingElements) {
					if (tag.getTagName().equalsIgnoreCase("label")) {
						fieldText = driver.findElement(By.xpath(storedXpath + "/..")).getText();
					}
					if (fieldText.indexOf("\n") > 0) {
						fieldText = fieldText.substring(0, fieldText.indexOf("\n"));
					}
				}
			} else {
				if (fieldText.indexOf("\n") > 0) {
					fieldText = fieldText.substring(0, fieldText.indexOf("\n"));
				}
			}
			newXpath = storedXpath + "~$" + fieldText;
			tempStrArray.add(newXpath);
		}
		storeXpath.removeAll(storeXpath);

	}

	// generating all related XPaths for an element
	public static String generateFollowingSibling(String xpath) {

		List<WebElement> following = d.findElements(By.xpath(xpath + "/following-sibling::*"));
		System.out.println(following.size());
		if (following.size() > 0) {

			System.out.println("Entering into following");
			followingSibling = xpath + "/following-sibling::*";
		} else {
			followingSibling = null;
		}
		return followingSibling;
	}

	public static String generatePrecedingSibling(String xpath) {

		List<WebElement> preceding = d.findElements(By.xpath(xpath + "/preceding-sibling::*"));
		System.out.println(preceding.size());
		if (preceding.size() > 0) {
			System.out.println("Entering into predeing");
			precedingSibling = xpath + "/preceding-sibling::*";
		} else {
			precedingSibling = null;
		}
		return precedingSibling;
	}

	public static String generateParent(String xpath) {

		List<WebElement> Parents = d.findElements(By.xpath(xpath + "/parent::*"));
		System.out.println(Parents.size());
		if (Parents.size() > 0) {

			System.out.println("Entering into Parents");
			parent = xpath + "/..";
		}
		return parent;
	}

	public static String generateParentSibling(String xpath) {

		List<WebElement> Parent_Sibling = d.findElements(By.xpath(xpath + "/../following-sibling::*"));
		System.out.println(Parent_Sibling.size());
		if (Parent_Sibling.size() > 0) {

			System.out.println("Entering into parent sibling");
			parentSibling = xpath + "/../following-sibling::*";
		} else {
			parentSibling = null;
		}
		return parentSibling;
	}

	public static String generateParentSiblingChild(String xpath) {

		List<WebElement> ParentSiblingChild = d.findElements(By.xpath(xpath + "/../following-sibling::*/child::*"));
		System.out.println(ParentSiblingChild.size());
		if (ParentSiblingChild.size() > 0) {

			System.out.println("Entering into GrandParent Follow Siblings");
			parentSiblingChild = xpath + "/../following-sibling::*/child::*";
		} else {
			parentSiblingChild = null;
		}
		return parentSiblingChild;
	}

	public static String generateGrandParent(String xpath) {

		List<WebElement> GrandParents = d.findElements(By.xpath(xpath + "/parent::*/.."));
		System.out.println(GrandParents.size());
		if (GrandParents.size() > 0) {

			System.out.println("Entering into GrandParents");
			grandParent = xpath + "/../..";
		}
		return grandParent;
	}

	public static String generateGrandParentSibling(String xpath) {

		List<WebElement> Grand_Parent_Sibling = d.findElements(By.xpath(xpath + "/../../following-sibling::*"));
		System.out.println(Grand_Parent_Sibling.size());
		if (Grand_Parent_Sibling.size() > 0) {

			System.out.println("Entering into grand parent sibling");
			grandParent = xpath + "/../../following-sibling::*";
		} else {
			grandParent = null;
		}
		return grandParent;
	}

	// as per our code, the below methods not used
	public static String generateXPathBuilder(String xpathValue, String xpathType, String newXpath) {
		StringBuffer attribute = new StringBuffer();
		StringBuffer label = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer fieldText = new StringBuffer();
		StringBuffer xValue = new StringBuffer();
		attribute.append(getValue(xpathValue, "Attribute"));
		label.append(getValue(xpathValue, "Label"));
		value.append(getValue(xpathValue, "Value"));
		xValue.append(generateXpath(attribute.toString(), label.toString(), value.toString()));
		System.out.println("New xpath is value before " + newXpath);

		System.out.println("New xpath is value at end " + newXpath);
		return newXpath;
	}

	public static String FindChildElementTag(List<WebElement> element) {
		String tagName = null;
		System.out.println(element.get(0).getTagName());
		System.out.println(element.get(0).getText());
		for (int j = 0; j < element.size(); j++) {
			System.out.println(element.get(j).getTagName());
			if (element.get(j).getAttribute("for") != null) {
				tagName = "FOR" + "~#" + element.get(j).getTagName() + "~*" + element.get(j).getAttribute("for") + "~$"
						+ element.get(j).getText();
				break;
			} else if (!element.get(j).getText().equals("")) {
				System.out.println(element.get(j).getTagName());
				tagName = "Text-decendent" + "~#" + element.get(j).getTagName() + "~*" + element.get(j).getText() + "~$"
						+ element.get(j).getText();
				break;
			}
		}
		return tagName;
	}

	public static String findElementsTag(List<WebElement> element) {
		String tagName = null;
		System.out.println("Preceding value " + element.get(0).getTagName());

		for (int j = 0; j < element.size(); j++) {
			System.out.println(element.get(j).getTagName());
			if (element.get(j).getAttribute("for") != null) {
				tagName = "FOR" + "~#" + element.get(j).getTagName() + "~*" + element.get(j).getAttribute("for") + "~$"
						+ element.get(j).getText();
				System.out.println(tagName);
				break;
			} else if (!element.get(j).getText().equals("")) {
				System.out.println(element.get(j).getTagName());
				List<WebElement> childElements = element.get(j).findElements(By.xpath("descendant::*"));
				if (childElements.size() > 0) {
				} else {
					tagName = "Text-decent" + "~#" + element.get(j).getTagName() + "~*"
							+ element.get(j).getAttribute("id") + "~$" + element.get(j).getText();
					System.out.println("else executed" + tagName);
				}
			} else {
				tagName = "" + "~#" + element.get(j).getTagName() + "~*" + element.get(j).getAttribute("id") + "~$"
						+ element.get(j).getText();
				System.out.println("else executed" + tagName);
			}
			break;
		}
		return tagName;
	}

	public static String findElementsTag(List<WebElement> element, String check) {
		String tagName = null;
		System.out.println(element.get(0).getTagName());
		for (int j = 0; j < element.size(); j++) {
			System.out.println(element.get(j).getTagName());
			if (element.get(j).getAttribute("for") != null) {
				tagName = "FOR" + "~#" + element.get(j).getTagName() + "~*" + element.get(j).getAttribute("for") + "~$"
						+ element.get(j).getText();
				break;
			} else if (!element.get(j).getText().equals("")) {
				System.out.println(element.get(j).getTagName());
				List<WebElement> childElements = element.get(j).findElements(By.xpath("descendant::*"));
				if (childElements.size() > 0) {

				} else {
					tagName = "Text-decent" + "~#" + element.get(j).getTagName() + "~*" + element.get(j).getText()
							+ "~$";
				}
				break;
			}
		}
		return tagName;
	}

	public static String findParentTag(WebElement element, String type) {
		String tagName = null;

		System.out.println(element.getTagName() + " - " + element.getText());
		System.out.println(element.getTagName());
		parentValue = element.getTagName();
		System.out.println("Parent .... " + parentValue);

		if (element.getAttribute("for") != null) {
			tagName = "FOR" + "~#" + element.getTagName() + "~*" + element.getAttribute("for") + "~$";
			System.out.println(element.getText());
		}

		List<WebElement> childElements = element.findElements(By.xpath("descendant::*"));
		if (childElements.size() > 0) {
			for (int i = 0; i < childElements.size(); i++) {
				if (childElements.get(i).getText() != null & !(childElements.get(i).getText().equals(""))) {
					tagName = "Text-decent" + "~#" + childElements.get(i).getTagName() + "~*"
							+ childElements.get(i).getText() + "~$";
					break;
				}
			}
		} else {
			tagName = "Text" + "~#" + element.getTagName() + "~*" + element.getText();
		}
		System.out.println("Parent tag name");
		return tagName;
	}

	public static String findGrandParentTag(WebElement element, String type) {
		String tagName = null;

		System.out.println(element.getTagName() + " - " + element.getText());
		System.out.println(element.getTagName());
		grandParentValue = element.getTagName();

		if (element.getAttribute("for") != null) {
			tagName = "FOR" + "~#" + element.getTagName() + "~*" + element.getAttribute("for") + "~$";
			System.out.println(element.getText());
		} else if (!element.getText().equals("") && !(type.equals("select"))) {
			System.out.println(element.findElement(By.xpath("descendant::*")).getTagName());

			List<WebElement> childElements = element.findElements(By.xpath("descendant::*"));
			if (childElements.size() > 0) {
				for (int i = 0; i < childElements.size(); i++) {
					if (childElements.get(i).getText() != null & !(childElements.get(i).getText().equals(""))) {
						tagName = "Text-decent" + "~#" + childElements.get(i).getTagName() + "~*"
								+ childElements.get(i).getText() + "~$";
						break;
					}
				}
			} else {
				tagName = "Text" + "~#" + element.getTagName() + "~*" + element.getText();
			}
		}
		return tagName;
	}

	public static String generateXpath(String attribute, String label, String value) {
		String xpathValue = null;
		value = value.replace("*", "");
		System.out.println("generate Xpath test" + value);
		switch (attribute) {
		case "FOR":
			xpathValue = "//" + label + "[@for='" + value + "']";
			break;
		case "Text":
			if (value.length() > 30) {
				if (value.indexOf("\n") > 0) {
					value = value.substring(0, value.indexOf("\n"));
				} else {
					value = value.substring(0, 30);
				}
				xpathValue = "//" + label + "[contains(text(), '" + value + "')]/..";
			} else {
				xpathValue = "//" + label + "[text()= '" + value + "')]/..";
			}
			break;

		case "Text-decendent":
			if (value.length() > 30) {
				if (value.indexOf("\n") > 0) {
					value = value.substring(0, value.indexOf("\n"));
				} else {
					value = value.substring(0, 30);
				}
				xpathValue = "//" + label + "[contains(text(), '" + value + "')]/";
			} else {
				xpathValue = "//" + label + "[text(), '" + value + "')]/";
			}
			break;
		default:
			break;
		}
		System.out.println("xPath value--------" + xpathValue);
		return xpathValue;
	}

	public static String getValue(String value, String valueType) {
		String tempValue = null;
		switch (valueType) {
		case "Attribute":
			tempValue = value.substring(0, value.indexOf("~#"));
			System.out.println("Attribute for/Not+++++++++ " + tempValue);
			break;

		case "Label":
			tempValue = value.substring(value.indexOf("~#") + 2, value.indexOf("~*"));
			break;

		case "Value":
			tempValue = value.substring(value.indexOf("~*") + 2, value.indexOf("~$"));
			break;

		case "Field Text":
			tempValue = value.substring(value.indexOf("~$") + 2);

		default:
			break;
		}
		System.out.println("temp value#########" + tempValue);
		return tempValue;
	}

}
