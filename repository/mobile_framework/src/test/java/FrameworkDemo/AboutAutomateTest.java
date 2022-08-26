package FrameworkDemo;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Frameworkbase.BaseClass;

import org.testng.AssertJUnit;
import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import pageObjects.FlowPage;
import pageObjects.SideoptionPage;

@Listeners(FrameworkDemo.Listener.class)
public class AboutAutomateTest extends BaseClass{
	@SuppressWarnings("rawtypes")
	public AndroidDriver mobileDriver;

	@BeforeMethod
	public void initialize()
	{	
		mobileDriver =initializeDriver();
	}
	
	@Test(description = "Verify Automate Version On page")
	public void verifyAboutAutomateExist() throws MalformedURLException, InterruptedException
	{
		FlowPage fp =new FlowPage(mobileDriver);
		fp.accept();
		SideoptionPage sp = fp.clickOnSideMenu();
		sp.scrollTillAboutAutomate();
		Assert.assertTrue(sp.verifyAutomationVersion(),"Automation version is not visible.");	
		
	}
	
	@Test(description = "Verify Automate Verison Failed.")
	public void failAboutAutomateExist() throws MalformedURLException, InterruptedException
	{
		FlowPage fp =new FlowPage(mobileDriver);
		fp.accept();
		SideoptionPage sp = fp.clickOnSideMenu();
		sp.scrollTillAboutAutomate();
		Assert.assertFalse(sp.verifyAutomationVersion(),"Automation version is not visible.");	
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {
		this.mobileDriver.quit();
	}
}
