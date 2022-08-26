package FrameworkDemo;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Frameworkbase.BaseClass;

import java.net.MalformedURLException;
import org.testng.Assert;
import org.testng.ITestResult;

import io.appium.java_client.android.AndroidDriver;
import pageObjects.FlowPage;
import pageObjects.SideoptionPage;


// @Listeners(assignmentPackage.Listener.class)
public class FlowTest extends BaseClass {
	@SuppressWarnings("rawtypes")
	public AndroidDriver mobileDriver;

	@BeforeMethod
	public void initialize()
	{	
		mobileDriver =initializeDriver();
	}
	
	@Test(description = "Verify context under Help.")
	public void verifyHelpTest() throws MalformedURLException, InterruptedException
	{
		FlowPage fp =new FlowPage(mobileDriver);
		fp.accept();
		SideoptionPage sp = fp.clickOnSideMenu();
		sp.selectHelpAndFeedback();
		Assert.assertTrue(sp.verifyHelpOnDocumentationIsDisplayed(),"Help context - Documentation is not displayed.");	
	}
	
	
	@Test(description = "Verify theme is changed to Dark.")
	public void verifyThemeChange() throws MalformedURLException, InterruptedException
	{
		FlowPage fp =new FlowPage(mobileDriver);
		fp.accept();
		SideoptionPage sp = fp.clickOnSideMenu();	
		sp.selectSettings();
		sp.selectTheme();
		sp.selectDarkTheme();
		Assert.assertTrue(sp.verifyThemeIsDark(),"Dark Theme is not applied");
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {
		this.mobileDriver.quit();
	}
}
