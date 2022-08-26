package createObject;

import base.SetupInit;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import mobile.android.pages.AppPage;
import mobile.android.pages.BrowserPage;

public class CreateObject extends SetupInit {
	public LogSteps log;
	public BrowserPage bPage;
	public AppPage aPage;

	public CreateObject(AndroidDriver<MobileElement> mobileDriver) {
		log = new LogSteps();
		if (mobileDriver != null) {
			bPage = new BrowserPage(log, mobileDriver);
			aPage = new AppPage(log, mobileDriver);
		}
	}

}
