package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SideoptionPage {
	
	@SuppressWarnings("rawtypes")
	public AndroidDriver mobileDriver;
	
	By settings = By.xpath("//*[@text='Settings']");
	By helpAndFeedback = By.xpath("//*[@text ='Help & feedback']");
	By documentationText = By.xpath("//*[@text='Documentation']");
	By helpText = By.xpath("//android.view.ViewGroup/android.widget.TextView");
	By theme = By.xpath("//*[@text='Theme']");
	By darkTheme = By.xpath("//*[@text='Dark']");
	By premError = By.xpath("//*[@text='Premium error!']");
	By aboutAutomate = By.xpath("//*[@text='About Automate']");
	By automationVersion = By.xpath("//android.view.View[@content-desc='Version 1.32.6']");
	
	
	public SideoptionPage(@SuppressWarnings("rawtypes") AndroidDriver mobileDriver) {
		this.mobileDriver=mobileDriver;
	}

	public void selectHelpAndFeedback() {
		try {
			mobileDriver.findElement(helpAndFeedback).click();
		} catch (Exception e) {
			throw new RuntimeException("Unbale to select Help And Feedback");
		}
	}
	
	public void selectSettings() {
		try {
			mobileDriver.findElement(settings).click();
		} catch (Exception e) {
			throw new RuntimeException("Unbale to select Settings");
		}
	}
	
	public void selectTheme() {
		try {
			mobileDriver.findElement(theme).click();
		} catch (Exception e) {
			throw new RuntimeException("Unbale to select Theme");
		}
	}
	
	
	public void selectDarkTheme() {
		try {
			mobileDriver.findElement(darkTheme).click();
		} catch (Exception e) {
			throw new RuntimeException("Unbale to select Dark Theme");
		}
	}
	
	public boolean verifyHelpOnDocumentationIsDisplayed() {
		WebDriverWait wait = new WebDriverWait(mobileDriver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(helpText));
		try {
			return mobileDriver.findElement(helpText).isDisplayed();
		} catch (Exception e) {
			throw new RuntimeException("Help context - Help on Documentation is not displayed.");
		}
	}
	
	public boolean verifyAutomationVersion() {
		WebDriverWait wait = new WebDriverWait(mobileDriver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(automationVersion));
		try {
			return mobileDriver.findElement(automationVersion).isDisplayed();
		} catch (Exception e) {
			throw new RuntimeException("Automation version is not displayed.");
		}
	}
	
	
	
	public boolean verifyThemeIsDark() {
		WebDriverWait wait = new WebDriverWait(mobileDriver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(darkTheme));
		try {
			return mobileDriver.findElement(darkTheme).isDisplayed();
		} catch (Exception e) {
			throw new RuntimeException("Dark Theme is not applied");
		}
	}
	
	public WebElement premErrorViews() {
	return mobileDriver.findElement(premError);
	}

	@SuppressWarnings("unchecked")
	public List<WebElement> aboutAutomateViews(){
	return mobileDriver.findElements(aboutAutomate);
	}

	public void scrollTillAboutAutomate() throws InterruptedException {
		mobileDriver.findElement(settings).click();
		
		while(aboutAutomateViews().size() == 0) {
			scrollDown();
		}
		
		if(aboutAutomateViews().size() > 0) {
			aboutAutomateViews().get(0).click();
		}
		Thread.sleep(4000);	
		
	}
	
	@SuppressWarnings("rawtypes")
	public void scrollDown() {
	Dimension dimension = this.mobileDriver.manage().window().getSize();
	
	Double scrollHeightStart = dimension.getHeight() * 0.5;
	int scrollStart = scrollHeightStart.intValue();
	
	Double scrollHeightEnd = dimension.getHeight() * 0.2;
	int scrollEnd = scrollHeightEnd.intValue();
	
	new TouchAction((PerformsTouchActions) this.mobileDriver)
	.press(PointOption.point(0, scrollStart))
	.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
	.moveTo(PointOption.point(0, scrollEnd))
	.release().perform();
	}

	}
	
