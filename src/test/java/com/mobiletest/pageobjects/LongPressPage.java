package com.mobiletest.pageobjects;

import com.mobiletest.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class LongPressPage {

    AppiumDriver driver;
    Helpers helper;

    @AndroidFindBy(accessibility = "longpress")
    @iOSFindBy(accessibility = "longpress")
    public MobileElement longPressButton;

    @AndroidFindBy(id="android:id/message")
    public MobileElement alertText;

    @AndroidFindBy(id="android:id/button1")
    public MobileElement okButton;

    public LongPressPage(AppiumDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
        helper=new Helpers(driver);
    }

    public void longPress() {
        helper.waitForElement(longPressButton);
        TouchAction action=new TouchAction(driver);
        action.longPress(longPressButton).perform();
    }

    public String getAlertText(){
        return alertText.getText();
    }

    public void clickOkButton(){
        okButton.click();
    }


}
