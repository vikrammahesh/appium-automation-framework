package com.mobiletest.utils;

import com.mobiletest.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public AppiumDriver driver;
    public DriverManager driverManager;
    public FileUtilties fileUtilties;

    //@BeforeSuite
    public void setupServer(){
        driverManager.startServer();
    }

    //@AfterSuite
    public void endServer(){
        driverManager.stopServer();
    }

    @BeforeClass
    public void setup() {
        fileUtilties = new FileUtilties();
        driverManager = new DriverManager();
        driver = driverManager.getDriver(fileUtilties.readProperties());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void start(){
        if(driver!=null){
            driver.launchApp();
        }
        else{
            driver = driverManager.getDriver(fileUtilties.readProperties());
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }

    @AfterClass
    public void quitDriver(){
        driver.quit();
    }

    public AppiumDriver getDriver(){
        return driver;
    }


}
