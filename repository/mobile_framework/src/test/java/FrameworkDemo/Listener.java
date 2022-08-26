package FrameworkDemo;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Frameworkbase.BaseClass;
import io.appium.java_client.android.AndroidDriver;

public class Listener extends BaseClass implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		  System.out.println("Testcase Passed :"+result.getName());
		
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		  System.out.println("Testcase Failed is :"+result.getName());
		  String testMethodName = result.getMethod().getMethodName();
		  AndroidDriver mobileDriver = null;		  
		  try {
			  mobileDriver =(AndroidDriver)result.getTestClass().getRealClass().getDeclaredField("mobileDriver").get(result.getInstance());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		  
		  try {
			getScreenShotPath(testMethodName, mobileDriver);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
