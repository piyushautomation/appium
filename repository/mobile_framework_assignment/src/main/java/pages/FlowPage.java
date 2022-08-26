package pages;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class FlowPage {
	
	public AndroidDriver mobileDriver;	
	By acceptBtn = By.id("android:id/button1");
	By btnSideMenu = By.xpath("//android.widget.ImageButton[@content-desc='Open drawer']");
	
	public FlowPage(AndroidDriver mobileDriver) {
		this.mobileDriver=mobileDriver;
	}

	public void accept() {
		try {
			mobileDriver.findElement(acceptBtn).click();
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click on Accept");
		}
	}

	public SideMenuPage clickOnSideMenu() {
		try {
			mobileDriver.findElement(btnSideMenu).click();
			return new SideMenuPage(mobileDriver);
		} catch (Exception e) {
			throw new RuntimeException("Unbale to click on Side Menu");
		}
	}

}
