package Assignment;

import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


public class Day4Assignment3{

	By accept = By.id("android:id/button1");
	By btnSideMenu = By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']");
	By settings = By.xpath("//*[@text='Settings']");
	By premError = By.xpath("//*[@text='Premium error!']");
	By aboutAutomate = By.xpath("//*[@text='About Automate']");

	public AndroidDriver driver;

	@Test
	public void verifyVersionInAboutAutomateTest() throws MalformedURLException, InterruptedException
	{

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.APP,"C:\\com.llamalab.automate_199_apps.evozi.com.apk");
		cap.setCapability("autoGrantPermissions", true);

		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub/"),cap);
		Thread.sleep(3000);

		driver.findElement(accept).click();
		driver.findElement(btnSideMenu).click();
		scrollTillWebView();
		driver.quit();
	}

	public void scrollDown() {
		Dimension dimension = this.driver.manage().window().getSize();

		Double scrollHeightStart = dimension.getHeight() * 0.5;
		int scrollStart = scrollHeightStart.intValue();

		Double scrollHeightEnd = dimension.getHeight() * 0.2;
		int scrollEnd = scrollHeightEnd.intValue();

		new TouchAction((PerformsTouchActions) this.driver)
		.press(PointOption.point(0, scrollStart))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
		.moveTo(PointOption.point(0, scrollEnd))
		.release().perform();
	}

	public WebElement premErrorViews() {
		return this.driver.findElement(premError);
	}

	public List<WebElement> aboutAutomateViews(){
		return this.driver.findElements(aboutAutomate);
	}

	public void scrollTillWebView() throws InterruptedException {
		this.driver.findElement(settings).click();

		while(aboutAutomateViews().size() == 0) {
			scrollDown();
		}

		if(aboutAutomateViews().size() > 0) {
			aboutAutomateViews().get(0).isDisplayed();
		}
	}
}



