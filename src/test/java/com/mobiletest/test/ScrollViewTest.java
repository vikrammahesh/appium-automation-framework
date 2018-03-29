package com.mobiletest.test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ScrollViewTest {

    public static AppiumDriver driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("appPackage", "com.android.settings");
        caps.setCapability("appActivity", ".Settings");
        caps.setCapability("commandTimeout", "50");
        caps.setCapability("platformVersion", "8.0");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test(priority = 1)
    public void scrollElement() throws InterruptedException {
        scrollToElement("Security");
    }

    @Test(priority = 2)
    public void scrollToExact() {
        scrollToExactElement("Users & accounts");
    }

    public void scrollToElement(String str) {
        ((AndroidDriver) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
                        +str + "\").instance(0))");

    }

    public void scrollToExactElement(String str) {
        ((AndroidDriver) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
                        + str + "\").instance(0))");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
