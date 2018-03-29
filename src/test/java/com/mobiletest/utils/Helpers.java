package com.mobiletest.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helpers {

    public AppiumDriver driver;
    WebDriverWait wait;

    public Helpers(AppiumDriver driver){
        this.driver=driver;
        wait=new WebDriverWait(driver,20);
    }

    public By waitForElement(By element){
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
        return element;
    }

    public void waitForElement(MobileElement id){
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }
}
