package base;

import java.io.File;
import java.net.URL;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Wait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import utils.ReadProperty;
import utils.ReadXMLData;
import utils.Utility;

public class CommonConstants {
	public static final int LOADER_WAIT = 180;
	static String currentDir = System.getProperty("user.dir");
	public static String currentOS = System.getProperty("os.name");
	static final String CONFIG_FILE_NAME = ReadProperty.getPropertyValue("ConfigurationFileName");
	static final String REPORT_FOLDER = ReadProperty.getPropertyValue("REPORT_FOLDER");
	static final String SCREENSHOT_FOLDER = ReadProperty.getPropertyValue("SCREENSHOT_FOLDER");
	static final String VIDEOS_FOLDER = ReadProperty.getPropertyValue("VIDEOS_FOLDER");
	protected static final String TESTDATA_FOLDER = ReadProperty.getPropertyValue("TESTDATA_FOLDER");
	static final String RESOURCES_FOLDER = TESTDATA_FOLDER + "/" + ReadProperty.getPropertyValue("RESOURCES_FOLDER")
			+ "/";
	static final String DOWNLOADS_FOLDER = ReadProperty.getPropertyValue("DOWNLOADS_FOLDER");
	static final String DEPENDENCIES_FOLDER = (currentDir + File.separator
			+ ReadProperty.getPropertyValue("DEPENDENCIES_FOLDER")) + File.separator;
	static final String APPLICATIONS_FOLDER = (currentDir + File.separator
			+ ReadProperty.getPropertyValue("APPLICATIONS_FOLDER"));
	protected static AppiumDriverLocalService service;
	public String appiumServer = "127.0.0.1";
	public int appiumPort = 4723;
	public static String userName;
	public static String password;
	public static String test_data_file;
	protected String screenshotName;
	static final int DEFAULT_PAUSE_INSECONDS = 2;
	protected int GENERAL_TIMEOUT = 30;
	String configFilePath;
	int MAX_WAIT_TIME_IN_SEC = Integer.parseInt(ReadProperty.getPropertyValue("MAX_WAIT_TIME_IN_SEC"));
	@SuppressWarnings("rawtypes")
	public AndroidDriver mobileDriver;

	protected enum Condition {
		isDisplayed, isClickable, isPresent, isNotVisible
	}

	protected enum Speed {
		slow
	}

	public Date testStartTime;
	Wait<WebDriver> wait;
	static URL remote_grid;
	int reloadCounter = 0;
	public static String configFileName = ReadProperty.getPropertyValue("ConfigurationFileName");
	public static ReadXMLData configFileObj;
	protected ReadXMLData fwTestData = null;

	protected static String appType; // AUT Type
	protected String hubUrl; // Selenium hub IP
	protected String hubPort; // Selenium hub port
	protected String appActivity;
	protected static String appPackage;
	public static String appName;
	public static String deviceID;
	protected static String version;
	protected static String test_data_folder_path = null;
	protected static String screenshot_folder_path = null;
	protected static String resources_folder_path = null;
	protected static String locator_folder_path = null;
	public boolean recordSessionVideo = false;
	static ReadXMLData readFilePath = null;
	URL appiumURL = null;
	String regexTCID;
	String regexAuthor;
	String sessionid = "";
	String videoURL = "";
	protected String siteidFMC = "7777";
	public static Utility util;
	static {
		util = new Utility();
	}

	@SuppressWarnings("rawtypes")
	public AndroidDriver getMobileDriver() {
		return this.mobileDriver;
	}

	DesiredCapabilities capabilities;
}
