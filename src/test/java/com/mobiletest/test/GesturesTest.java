package com.mobiletest.test;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;


public class GesturesTest {

    AndroidDriver driver;
    TouchAction action;

    @BeforeClass
    public void setup() throws MalformedURLException {
        driver = new AndroidDriver(new URL("https://127.0.0.1:4723/wd/hub"), setVodqaCapabilities());
        action = new TouchAction(driver);
        login();
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

    public void login() {
        By element = By.xpath("//android.view.ViewGroup[@content-desc='login']/android.widget.Button/android.widget.TextView");
        verifyPresenceofElement(element);
        driver.findElement(element).click();
    }

    public void clickBack() {
        By element = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.TextView");
        verifyPresenceofElement(element);
        driver.findElement(element).click();
    }

    //@Test
    public void dragAndDrop() {
        driver.findElementByAccessibilityId("dragAndDrop").click();
        MobileElement dragme = (MobileElement) new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("dragMe")));
        WebElement dropZone = driver.findElementByAccessibilityId("dropzone");
        action.press(dragme).moveTo(dropZone).release().perform();
    }

    @Test
    public void longPress() {
        driver.findElementByAccessibilityId("longPress").click();
        MobileElement element = (MobileElement) new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("longpress")));
        action.longPress(element).perform();
        String msg = driver.findElement(By.id("android:id/message")).getText();
        Assert.assertEquals(msg, "you pressed me hard :P");
        driver.findElement(By.id("android:id/button1")).click();
        clickBack();
    }

    public void verifyPresenceofElement(By element) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(element));
    }

    @Test
    public void horizontalSlide() {
        driver.findElementByAccessibilityId("slider1").click();
        By element = MobileBy.AccessibilityId("slider");
        verifyPresenceofElement(element);
        WebElement slider=driver.findElement(element);
        Dimension size=slider.getSize();
        action.press(slider,0,size.height/2).moveTo(slider,size.width/2,size.height/2).release().perform();
        clickBack();
    }

}