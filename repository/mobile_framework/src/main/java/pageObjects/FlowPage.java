package pageObjects;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class FlowPage {
	
	@SuppressWarnings("rawtypes")
	public AndroidDriver mobileDriver;	
	By accept = By.id("android:id/button1");
	By btnSideMenu = By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']");
	
	public FlowPage(@SuppressWarnings("rawtypes") AndroidDriver mobileDriver) {
		this.mobileDriver=mobileDriver;
	}

	public void accept() {
		try {
			mobileDriver.findElement(accept).click();
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click on Accept");
		}
	}

	public SideoptionPage clickOnSideMenu() {
		try {
			mobileDriver.findElement(btnSideMenu).click();
			return new SideoptionPage(mobileDriver);
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click on Side Menu");
		}
	}

}
