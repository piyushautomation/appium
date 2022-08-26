package mobile.android.testCases;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.SetupInit;
import createObject.CreateObject;
import createObject.LogSteps;

public class BrowserTestcase extends SetupInit {

	Map<Object, Object> data;
	protected CreateObject co;
	public LogSteps log;

	@SuppressWarnings("unchecked")
	@BeforeMethod
	public void beforeMethod() {
		co = new CreateObject(getMobileDriver());
	}

	@Test(enabled = true, priority = 1)
	public void test() throws Exception {
		try {
			co.bPage.swipe(0);
		} catch (Exception e) {
			logException("BrowserTestcase", "test");
			throw e;
		}
	}

}
