package base;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import createObject.LogSteps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utils.ReadProperty;
import utils.ReadXMLData;
import utils.xmlUtils;

public class SetupInit extends CommonConstants {
	/**
	 * @param deviceID
	 * @param version
	 * @param app
	 * @param myDeviceContext
	 * @param testContext
	 */
	@Parameters({ "app", "device" })
	@BeforeMethod
	public void initializeSetupInit(String app, @Optional String device, ITestContext testContext) {
		appName = app;
		deviceID = device;
		System.out.println("Execution started");
		System.out.println(
				"####################################################################################################");
		screenshotName = "";
		test_data_folder_path = new File(TESTDATA_FOLDER).getAbsolutePath() + File.separator;
		screenshot_folder_path = new File(SCREENSHOT_FOLDER).getAbsolutePath() + File.separator;
		resources_folder_path = new File(RESOURCES_FOLDER).getAbsolutePath() + File.separator;
		configFilePath = test_data_folder_path + CONFIG_FILE_NAME;
		fetchSuiteConfiguration("Master");
		File downloadsDirectoryName = new File(DOWNLOADS_FOLDER);
		if (!downloadsDirectoryName.exists())
			downloadsDirectoryName.mkdir();
		testStartTime = new Date();
		configFileObj = new ReadXMLData(test_data_folder_path + configFileName);
		try {
			appiumURL = new URL("http://" + appiumServer + ":" + appiumPort + "/wd/hub");
			DesiredCapabilities cap;
			switch (appType) {
			case "browser":
				System.setProperty("webdriver.chrome.driver",
						"C:\\Program Files\\Appium\\resources\\app\\node_modules\\appium\\node_modules\\appium-chromedriver\\chromedriver\\win\\chromedriver.exe");
				cap = setBrowserCapabilitiesAndroid();
				this.mobileDriver = new AndroidDriver<MobileElement>(appiumURL, cap);
				this.mobileDriver.get("https://www.google.co.in/");
				break;
			case "app":
				cap = setAppCapabilitiesAndroid();
				this.mobileDriver = new AndroidDriver<MobileElement>(appiumURL, cap);
				System.out.println();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pauseInSeconds(int sec) {
		try {
			Thread.sleep(1000 * sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void pauseInMilliSeconds(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void log(String message) {
		Reporter.log(message);
	}

	public DesiredCapabilities setBrowserCapabilitiesAndroid() {
//		WebDriverManager.chromedriver().setup();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "emulator-5554");
		capabilities.setCapability("platformVersion", "10");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		capabilities.setCapability("newCommandTimeout", 1000);
		capabilities.setCapability("autoGrantPermissions", true);
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			capabilities.setCapability("autoAcceptAlerts", true);
		}
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", false);
		return capabilities;
	}

	public DesiredCapabilities setAppCapabilitiesAndroid() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "emulator-5554");
		File file = new File(currentDir + File.separator + "applications" + File.separator + appName);
		capabilities.setCapability("app", file.getAbsolutePath());
		capabilities.setCapability("platformVersion", "10");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage", appPackage);
//		capabilities.setCapability("appActivity", appActivity);
		capabilities.setCapability("newCommandTimeout", 1000);
//		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("autoGrantPermissions", true);
//		capabilities.setCapability("unicodeKeyboard", true);
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			capabilities.setCapability("autoAcceptAlerts", true);
		}
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("noReset", false);
		return capabilities;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {
//		Utility.executeCMDCommand("adb uninstall " + appPackage);
		this.mobileDriver.quit();
	}

	@AfterClass
	public void closeApp() {
//		this.mobileDriver.quit();
//		if(isWindowsOS()) 
//			stopServer();
	}

	public void captureVideo(AndroidDriver<MobileElement> driver) {
		((CanRecordScreen) driver)
				.startRecordingScreen(new AndroidStartScreenRecordingOptions().withTimeLimit(Duration.ofSeconds(1800)));
	}

//	public void makeScreenshot(String screenShotName, File screenShotLoaction) {
//		File screenshot;
//		AndroidDriver augmentedDriver = null;
//		try {
//			augmentedDriver = (AndroidDriver) new Augmenter().augment(this.mobileDriver);
//			screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
//			File f = new File(screenShotLoaction + File.separator + screenShotName);
//			FileUtils.copyFile(screenshot, f);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public void fetchSuiteConfiguration(String configuration) {
		// configFileObj = new ReadXMLData(test_data_folder_path + configFileName);
		String xmlFilePath = test_data_folder_path + configFileName;
		appType = xmlUtils.getChildNodeValue(xmlFilePath, "MasterType");
		if (appType.equals("android")) {
			hubUrl = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "Hub");
			hubPort = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "Port");
		}
		appPackage = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "packageName");
		appActivity = xmlUtils.getChildNodeValue(xmlFilePath, configuration, "appWaitActivity");
		regexTCID = xmlUtils.getChildNodeValue(xmlFilePath, "Configuration", "TestIdRegex");
		regexAuthor = xmlUtils.getChildNodeValue(xmlFilePath, "Configuration", "TestAuthorRegex");
	}

	// *************************************************

	public void clickOnElementWithOffset(int x, int y, By locator, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				new Actions(mobileDriver).moveToElement(element, x, y).click().build().perform();
			}
		} catch (Exception e) {
			String ExceptionMessage = "Click on Element is failed: " + getPortableString(message) + ": " + " by : "
					+ locator;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
	}

	public void clickOnElement(By locator, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.click();
			}
		} catch (Exception e) {
			String ExceptionMessage = "Click on Element is failed: " + getPortableString(message) + ": " + " by : "
					+ locator;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
	}

	public MobileElement getElement(By locator, int... timeOrAssert) {
		return getElement(Condition.isDisplayed, locator, getTimeOut(timeOrAssert));
	}

	public String getElementText(By locator, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isPresent, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
		} catch (Exception e) {
			String ExceptionMessage = "Get Element Text is failed: " + getPortableString(message) + ": " + " by : "
					+ locator;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
		return element.getText();
	}

	public String getElementAttribute(By locator, String attribute, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
		} catch (Exception e) {
			String ExceptionMessage = "Get Element Attribute is failed: " + getPortableString(message) + ": " + " by : "
					+ locator;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
		return element.getAttribute(attribute.trim()).trim();
	}

	public void clearAndsendKeys(By locator, String text, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.clear();
				element.sendKeys(text);
			}
		} catch (Exception e) {
			String ExceptionMessage = "Send Keys is failed: " + getPortableString(message) + ": " + " by : " + locator;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
	}

	public void sendKeys(By locator, Keys keys, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				element.sendKeys(keys);
			}
		} catch (Exception e) {
			String ExceptionMessage = "Send Keys is failed: " + getPortableString(message) + ": " + " by : " + locator;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
	}

	private int getTimeOut(int[] time) {
		int timeOut = MAX_WAIT_TIME_IN_SEC;
		if (time.length != 0)
			if (time[0] > 0)
				timeOut = time[0];
		return timeOut;
	}

	private Map<MobileElement, String> waitForElementState(By locator, Condition condition, int time) {
		MobileElement element;
		do {
		} while (!waitForLoader());
		Map<MobileElement, String> map = new HashMap<>();
		element = getElement(condition, locator, time);
		String message = "";
		if (element == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				message = "State = " + condition.toString() + " failed: ";
			}
		} else {
			message = "State = " + condition.toString() + " Passed: ";
		}
		map.put(element, message);
		return map;
	}

	private boolean waitForLoader() {
		reloadCounter = 0;
		pauseInMilliSeconds(900);
		if (isLoderDisplayed(By.xpath("//*[@text='Signing in...']"))) {
			Instant currentTime = getCurrentTime();
			while (isLoderDisplayed(By.xpath("//*[@text='Signing in...']"))) {
				Instant loopingTime = getCurrentTime();
				Duration timeElapsed = Duration.between(currentTime, loopingTime);
				long sec = timeElapsed.toMillis() / 1000;
				int durDiff = (int) sec;
				if (durDiff >= LOADER_WAIT) {
					reloadCounter++;
					if (reloadCounter == 6) {
						System.err.println(reloadCounter + "############Continuous Loader Displaying");
						assertTrue(false, "Continuous Loader Displaying");
					}
				}
			}
		}
		reloadCounter = 0;
		pauseInMilliSeconds(900);
		if (isLoderDisplayed(By.xpath("//*[@text='Processing, please wait..']"))) {
			Instant currentTime = getCurrentTime();
			while (isLoderDisplayed(By.xpath("//*[@text='Processing, please wait..']"))) {
				Instant loopingTime = getCurrentTime();
				Duration timeElapsed = Duration.between(currentTime, loopingTime);
				long sec = timeElapsed.toMillis() / 1000;
				int durDiff = (int) sec;
				if (durDiff >= LOADER_WAIT) {
					reloadCounter++;
					if (reloadCounter == 6) {
						System.err.println(reloadCounter + "############Continuous Loader Displaying");
						assertTrue(false, "Continuous Loader Displaying");
					}
				}
			}
		}
		reloadCounter = 0;
		pauseInMilliSeconds(900);
		if (isLoderDisplayed(By.xpath("//*[@text='Loading ...']"))) {
			Instant currentTime = getCurrentTime();
			while (isLoderDisplayed(By.xpath("//*[@text='Loading ...']"))) {
				Instant loopingTime = getCurrentTime();
				Duration timeElapsed = Duration.between(currentTime, loopingTime);
				long sec = timeElapsed.toMillis() / 1000;
				int durDiff = (int) sec;
				if (durDiff >= LOADER_WAIT) {
					reloadCounter++;
					if (reloadCounter == 6) {
						System.err.println(reloadCounter + "############Continuous Loader Displaying");
						assertTrue(false, "Continuous Loader Displaying");
					}
				}
			}
		}
		return true;
	}

	public boolean isLoderDisplayed(By locator) {
		boolean state = false;
		try {
			state = mobileDriver.findElement(locator).isDisplayed();
		} catch (Exception e) {
			state = false;
		}
		return state;
	}

	public Instant getCurrentTime() {
		return Instant.now();
	}

	private MobileElement getElement(Condition condition, By by, int time) {
		MobileElement element = null;
		WebDriverWait wait = new WebDriverWait(mobileDriver, time);
		try {
			switch (condition) {
			case isClickable:
				element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				if (element == null) {
					return element;
				} else if (element.getAttribute("clickable") == null) {
					return element;
				} else if (element.getAttribute("clickable") != null) {
					element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(by));
					return element;
				}
				if (!isVisibleInViewport(element) && element != null) {
					scrollToElement(element);
				}
				break;
			case isDisplayed:
				element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				if (!isVisibleInViewport(element) && element != null) {
					scrollToElement(element);
				}
				break;
			case isPresent:
				element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(by));
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}
		return element;
	}

	protected boolean isVisibleInViewport(MobileElement element) {
		return ((Boolean) ((JavascriptExecutor) ((MobileElement) element).getWrappedDriver()).executeScript(
				"var elem = arguments[0],                   box = elem.getBoundingClientRect(),      cx = box.left + box.width / 2,           cy = box.top + box.height / 2,           e = document.elementFromPoint(cx, cy); for (; e; e = e.parentElement) {           if (e === elem)                            return true;                         }                                        return false;                            ",
				new Object[] { element })).booleanValue();
	}

	protected void scrollToElement(MobileElement element) {
		((JavascriptExecutor) mobileDriver).executeScript(
				"window.scrollTo(" + element.getLocation().x + "," + (element.getLocation().y - 80) + ");");
	}

	public boolean isDisplayed(By by, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(by, Condition.isDisplayed, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				return true;
			}
		} catch (Exception e) {
			String ExceptionMessage = "Is Displayed failed for: " + getPortableString(message) + ": " + " by : " + by;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
		return false;
	}

	public boolean isPresent(By by, int... timeOrAssert) {
		MobileElement element = null;
		String message = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(by, Condition.isPresent, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			element = entry.getKey();
			message = entry.getValue();
		}
		try {
			if (element == null)
				throw new Exception();
			else {
				return true;
			}
		} catch (Exception e) {
			String ExceptionMessage = "Is Present failed for: " + getPortableString(message) + ": " + " by : " + by;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
		return false;
	}

	public void setLogSteps(LogSteps log, String message) {
		log.manualSteps.add(message.replaceAll("\\<.*?\\>", ""));
		log(message);
	}

	@SuppressWarnings("unchecked")
	public List<MobileElement> getElementList(By locator, int... timeOrAssert) {
		waitForElementState(locator, Condition.isDisplayed, getTimeOut(timeOrAssert));
		ArrayList<MobileElement> elementLst = new ArrayList<>();
		try {
			elementLst = (ArrayList<MobileElement>) mobileDriver.findElements(locator);
		} catch (Exception e) {
		}
		return elementLst;
	}

	@SuppressWarnings("rawtypes")
	public void swipe(MobileElement androidElement) {
		int startX = androidElement.getLocation().getX() + (androidElement.getSize().getWidth() / 2);
		int startY = androidElement.getLocation().getY() + (androidElement.getSize().getHeight() / 2);
		int endX = androidElement.getLocation().getX();
		int endY = androidElement.getLocation().getY();
		new TouchAction(mobileDriver).press(point(startX, startY)).waitAction(waitOptions(ofMillis(1000)))
				.moveTo(point(endX, endY)).release().perform();
	}

	@SuppressWarnings("rawtypes")
	public void swipe(By locator, int... timeOrAssert) {
		MobileElement androidElement = null;
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(locator, Condition.isDisplayed, getTimeOut(timeOrAssert));
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			androidElement = entry.getKey();
		}
		try {
			if (androidElement == null)
				throw new Exception();
			else {
				int startX = androidElement.getLocation().getX() + (androidElement.getSize().getWidth() / 2);
				int startY = androidElement.getLocation().getY() + (androidElement.getSize().getHeight() / 2);
				int endX = androidElement.getLocation().getX();
				int endY = androidElement.getLocation().getY();
				new TouchAction(mobileDriver).press(point(startX, startY)).waitAction(waitOptions(ofMillis(1000)))
						.moveTo(point(endX, endY)).release().perform();
			}
		} catch (Exception e) {
			String ExceptionMessage = "Swipe failed: " + getPortableString(e.getMessage()) + ": " + " by : " + locator;
			exceptionOnFailure(false, ExceptionMessage, timeOrAssert);
		}
	}

	@SuppressWarnings("rawtypes")
	public void scroll(By fromLocator, By toLocator) {
		Map<MobileElement, String> elementState = new HashMap<>();
		elementState = waitForElementState(fromLocator, Condition.isDisplayed, 0);
		MobileElement fromElement = null;
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			fromElement = entry.getKey();
		}
		// element.click();
		elementState = waitForElementState(toLocator, Condition.isDisplayed, 0);
		MobileElement toElement = null;
		for (Map.Entry<MobileElement, String> entry : elementState.entrySet()) {
			toElement = entry.getKey();
		}
		int startX = fromElement.getLocation().getX() + (fromElement.getSize().getWidth() / 2);
		int startY = fromElement.getLocation().getY() + (fromElement.getSize().getHeight() / 2);

		int endX = toElement.getLocation().getX() + (toElement.getSize().getWidth() / 2);
		int endY = toElement.getLocation().getY() + (toElement.getSize().getHeight() / 2);

		new TouchAction(mobileDriver).press(PointOption.point(startX, startY))
				.waitAction(new WaitOptions().withDuration(Duration.ofMillis(1000)))
				.moveTo(PointOption.point(endX, endY)).release().perform();
	}

	@SuppressWarnings("rawtypes")
	public void scrollUpAndroidElement(By mobileElement) {
		try {
			Dimension dimension = mobileDriver.manage().window().getSize();
			int scrollHeight = dimension.getHeight();
			int scrollWidth = dimension.getWidth();
			new TouchAction(mobileDriver).press(PointOption.point(scrollWidth / 2, scrollHeight / 2))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
					.moveTo(PointOption.point(scrollWidth / 2, 10)).release().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void scrollDownAndroidElement(By mobileElement) {
		try {
			Dimension dimension = mobileDriver.manage().window().getSize();
			int scrollHeight = dimension.getHeight();
			int scrollWidth = dimension.getWidth();
			new TouchAction(mobileDriver).press(PointOption.point(scrollWidth / 2, scrollHeight / 2))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
					.moveTo(PointOption.point(scrollWidth / 2, scrollHeight - 10)).release().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exceptionOnFailure(boolean success, String message, int[] assertion) {
		if (!success) {
			if (assertionResult(assertion)) {
				try {
					assertStatus(success);
				} catch (Exception e) {
					RuntimeException ex = new RuntimeException(message + " : " + e.getMessage());
					System.out.println("Exception Logging For: " + message);
					ex.setStackTrace(e.getStackTrace());
					throw ex;
				}
			}
		}
	}

	public boolean assertionResult(int[] j) {
		if (j != null) {
			if (j.length > 0) {
				if (j[0] != 0) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isVarArgsPassed(int[] j) {
		if (j != null) {
			if (j.length > 0) {
				if (j[0] > 0)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public void assertStatus(boolean success) throws Exception {
		if (!success) {
			throw new Exception("");
		}
	}

	public String getPortableString(String str) {
		if (str.length() > 150) {
			return str.substring(0, 150) + "...";
		} else if (str.length() != 0) {
			return str.substring(0, str.length() - 1) + "...";
		} else {
			return str;
		}
	}

	public void activateApp() {
		mobileDriver.activateApp(appActivity);
	}

	public boolean isWindowsOS() {
		if (currentOS.contains("Windows"))
			return true;
		else
			return false;
	}

	public String createScreenshotLink(String screenShotName, String link_text) {
		return "<br><Strong><font color=#FF0000>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Failed screenshot name = </font></strong><a target='_blank' href='"
				+ "file:///" + link_text + "'>" + screenShotName + "</a>";
	}

	public String makeScreenshot(String testClassName, String testMethod) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		String currentTime = timeFormat.format(date);
		String currentDir = System.getProperty("user.dir");
		String folderPath = currentDir + File.separator + ReadProperty.getPropertyValue("REPORT_FOLDER")
				+ File.separator + "HTML Reports" + File.separator + "FailureScreenShots" + File.separator
				+ testClassName + File.separator + currentDate.replaceAll("/", "_");
		folderPath = folderPath.trim();
		screenshotName = currentTime.replace(":", "_") + ".png";
		String filePath = folderPath + File.separator + testMethod + "_" + screenshotName;
		filePath = filePath.trim();
		File screenshotLocation = new File(folderPath);
		if (!screenshotLocation.getAbsoluteFile().exists())
			screenshotLocation.mkdir();
		File screenshot;
//		augmentedDriver = null;
//		WebDriver augmentedDriver = new Augmenter().augment(mobileDriver);
		screenshot = ((TakesScreenshot) mobileDriver).getScreenshotAs(OutputType.FILE);
		try {
			File f = new File(filePath);
			FileUtils.copyFile(screenshot, f);
			return filePath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Failed to capture a sccreenshot";
	}

	public void logException(String className, String methodName) {
		log("<br> <b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please click for screenshot - </b>");
		String screenShot = makeScreenshot(className, methodName);
		log(createScreenshotLink(screenshotName, screenShot));
	}
}