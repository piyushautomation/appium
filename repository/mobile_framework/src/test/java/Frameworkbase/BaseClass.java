package Frameworkbase;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseClass {
	
	@SuppressWarnings("rawtypes")
	public AndroidDriver mobileDriver;
	public String appiumServer = "127.0.0.1";
	public int appiumPort = 4723;
	URL appiumURL = null;
	
	@SuppressWarnings("rawtypes")
	public AndroidDriver initializeDriver()
	{
	try {
		appiumURL = new URL("http://" + appiumServer + ":" + appiumPort + "/wd/hub");
		this.mobileDriver = new AndroidDriver(appiumURL,setAppCapabilitiesAndroid());
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return mobileDriver;

	}
	
	public DesiredCapabilities setAppCapabilitiesAndroid() {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.APP
				,"C:\\com.llamalab.automate_199_apps.evozi.com.apk");
		cap.setCapability("platformVersion", "8.1");
		cap.setCapability("platformName", "Android");
		cap.setCapability("automationName", "UiAutomator2");
		return cap;
	}

	@SuppressWarnings("rawtypes")
	public String getScreenShotPath(String testCaseName,AndroidDriver mobileDriver) throws IOException
	{
		TakesScreenshot ts=(TakesScreenshot) mobileDriver;
		File source =ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir")+"/reports/"+testCaseName+".png";
		FileUtils.copyFile(source,new File(destinationFile));
		return destinationFile;
	}
	

}
