package com.mobiletest.pageobjects;

import com.mobiletest.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    AppiumDriver  driver;

    @AndroidFindBy(accessibility = "login")
    @iOSFindBy(accessibility = "login")
    private MobileElement loginButton;
    Helpers helper;


    public LoginPage(AppiumDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
        helper=new Helpers(driver);
    }

    public HomePage clickLoginButton(){
        helper.waitForElement(loginButton);
        loginButton.click();
        return new HomePage(driver);
    }
}
