package com.mobiletest.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    public AppiumDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    public void setup() throws MalformedURLException {
        driver = new AndroidDriver(new URL("https://127.0.0.1:4723/wd/hub"), setVodqaCapabilities());
    }

    public Capabilities setVodqaCapabilities() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "Android");
        cap.setCapability("platformVersion", "8.0");
        cap.setCapability("deviceName", "Android Emulator");
        cap.setCapability("appPackage", "com.vodqareactnative");
        cap.setCapability("appActivity", "com.vodqareactnative.MainActivity");
        cap.setCapability("AutomationName", "UIAutomator2");
        cap.setCapability("newCommandTimeout", "60");
        return cap;
    }


}
