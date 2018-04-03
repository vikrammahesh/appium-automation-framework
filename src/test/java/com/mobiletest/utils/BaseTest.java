package com.mobiletest.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    public AppiumDriver driver;
    public WebDriverWait wait;
    public AppiumDriverLocalService service;

    //@BeforeSuite
    public void setupServer(){
        startServer();
    }

    //@AfterSuite
    public void endServer(){
        stopServer();
    }

    @BeforeClass
    public void setup() throws MalformedURLException {
        driver = new AndroidDriver(new URL("https://127.0.0.1:4723/wd/hub"), setVodqaCapabilities());
       // driver = new AndroidDriver(service.getUrl(), setVodqaCapabilities());
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

    public AppiumDriver getDriver(){
        return driver;
    }

    public void startServer(){
        AppiumServiceBuilder builder = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\maheshv\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).
                withArgument(GeneralServerFlag.LOG_LEVEL, "info").usingAnyFreePort();
        service=builder.build();
        service.start();
    }

    public void stopServer(){
        service.stop();
    }
}
