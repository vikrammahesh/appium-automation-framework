package com.mobiletest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverManager {
    public AppiumDriverLocalService service;

    public AppiumDriver getDriver(Properties prop){
        AppiumDriver driver=null ;
        URL server=null;
        try {
            server=new URL("https://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if(prop.getProperty("PLATFORM_NAME").equalsIgnoreCase("Android")){
           driver=new AndroidDriver(server, setCapabilities(prop));
        }
        else if (prop.getProperty("PLATFORM_NAME").equalsIgnoreCase("Ios")) {
            driver = new IOSDriver(server, setCapabilities(prop));
        }
        else{
            System.out.println("Supported PLATFORM should be ANDROID or IOS");
        }
        return driver;
    }

    public Capabilities setCapabilities(Properties prop) {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", prop.getProperty("PLATFORM_NAME"));
        //cap.setCapability("platformVersion", prop.getProperty("PLATFORM_VERSION"));
        cap.setCapability("deviceName", prop.getProperty("DEVICE_NAME"));
        if(prop.getProperty("INSTALL_APP").equalsIgnoreCase("true")) {
            String appPath=System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
                    File.separator + "resources" + File.separator;
            cap.setCapability("app",appPath+prop.getProperty("APP"));
        }
        cap.setCapability("appPackage", "com.vodqareactnative");
        cap.setCapability("appActivity", "com.vodqareactnative.MainActivity");
        cap.setCapability("AutomationName", "UIAutomator2");
        cap.setCapability("newCommandTimeout", "60");
        return cap;
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
