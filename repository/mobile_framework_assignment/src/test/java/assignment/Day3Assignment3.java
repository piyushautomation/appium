package assignment;

import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class Day3Assignment3 {

	@Test
	public void AppiumTest() throws MalformedURLException, InterruptedException
	{

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.APP,System.getProperty("user.dir")+"applications"+"com.llamalab.automate_199_apps.evozi.com.apk");
		
		// Appium automatically determine which permissions your app requires and grant them to the app on install.
		cap.setCapability("autoGrantPermissions", true);
		
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub/"),cap);
		Thread.sleep(2000);
		driver.quit();
	}
}

