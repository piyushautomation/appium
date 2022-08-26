package mobile.android.pages;

import org.openqa.selenium.By;

import base.SetupInit;
import createObject.LogSteps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AppPage extends SetupInit {

	LogSteps log;

	By btnAccept = By.id("android:id/button1");

	By btnSideMenu = By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']");

	By trendingSearches = By.xpath("//*[text()='Trending searches']");

	By settings = By.xpath("//*[@text='Settings']");

	By premiumError = By.xpath("//*[@text='Premium error!']");

	By externalStorage = By.xpath("//*[@text='External storage']");

	public AppPage(LogSteps log, AndroidDriver<MobileElement> driver) {
		this.mobileDriver = driver;
		this.log = log;
	}

	public void swipe() {
		try {
			scroll(externalStorage, premiumError);
			log("Scroll performed successfully");
		} catch (Exception e) {
			throw new RuntimeException("Unbale to scroll");
		}
	}

	public void accept(int... time) {
		try {
			clickOnElement(btnAccept, time);
			log("Click on accept");
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click");
		}
	}

	public void clickOnSideMenu(int... time) {
		try {
			clickOnElement(btnSideMenu, time);
			log("Click on side menu");
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click");
		}
	}

	public void clickOnSettings(int... time) {
		try {
			clickOnElement(settings, time);
			log("Click on settings");
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click");
		}
	}

}