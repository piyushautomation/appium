package mobile.android.pages;

import org.openqa.selenium.By;

import base.SetupInit;
import createObject.LogSteps;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class BrowserPage extends SetupInit {

	LogSteps log;

	By logo = By.xpath("//img[@id='hplogo']");

	By trendingSearches = By.xpath("//*[text()='Trending searches']");

	public BrowserPage(LogSteps log, AndroidDriver<MobileElement> driver) {
		this.mobileDriver = driver;
		this.log = log;
	}

	public void swipe(int... time) {
		try {
			scroll(trendingSearches, logo);
			scroll(trendingSearches, logo);
			scroll(trendingSearches, logo);
			log("Scroll performed successfully");
		} catch (Exception e) {
			throw new RuntimeException("Unbale to scroll");
		}
	}

}