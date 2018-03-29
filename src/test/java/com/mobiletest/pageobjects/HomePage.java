package com.mobiletest.pageobjects;

import com.mobiletest.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public AppiumDriver driver;

    @AndroidFindBy(accessibility = "longPress")
    @iOSFindBy(accessibility = "longPress")
    public MobileElement longPressView;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.TextView")
    public MobileElement backButton;

    Helpers helper;

    public HomePage(AppiumDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        helper=new Helpers(driver);
    }

    public LongPressPage navigateToLongPressView(){
        helper.waitForElement(longPressView);
        longPressView.click();
        return new LongPressPage(driver);
    }

    public void clickBack() {
        By element = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.TextView");
        helper.waitForElement(element);
        backButton.click();
    }

}
