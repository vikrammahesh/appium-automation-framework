package com.mobiletest.test;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.ws.WebEndpoint;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class Practice {

 AndroidDriver driver;

 @BeforeClass
 public void setup() throws Exception{
  driver=new AndroidDriver(new URL("https://127.0.0.1:4723/wd/hub"),setCapabilitiesForFlipKart());
  //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
 }

 public Capabilities setCapabilitiesForFlipKart(){
  //File app=new File(System.getProperty("user.dir"),"com.flipkart.android_v6.1-880400_Android-4.1.apk");
  //System.out.println(app.getAbsolutePath());
  DesiredCapabilities cap=new DesiredCapabilities();
  cap.setCapability("platformName","Android");
  cap.setCapability("platformVersion","8.0");
  cap.setCapability("deviceName","Android Emulator");
  //cap.setCapability("app",app.getAbsolutePath());
  //cap.setCapability("udid","192.168.55.101:5555"); -- this is not required when executing on single instance
  cap.setCapability("appPackage", "com.flipkart.android");
  cap.setCapability("appActivity", "com.flipkart.android.activity.MSignupActivity");
  return cap;
 }

 public Capabilities setCapabilitiesForCalculator(){
  DesiredCapabilities cap=new DesiredCapabilities();
  cap.setCapability("platformName","Android");
  cap.setCapability("platformVersion","8.0");
  cap.setCapability("deviceName","Android Emulator");
  cap.setCapability("udid","192.168.55.101:5555");
  cap.setCapability("appPackage", "com.android.calculator2");
  cap.setCapability("appActivity", "com.android.calculator2.Calculator");
  return cap;
 }

 @Test(enabled = false)
 public void addTest(){
  driver.findElement(By.id("com.android.calculator2:id/digit_4")).click();
  driver.findElement(By.id("com.android.calculator2:id/op_add")).click();
  driver.findElement(By.id("com.android.calculator2:id/digit_8")).click();
  driver.findElement(By.id("com.android.calculator2:id/eq")).click();
  String result = driver.findElement(By.id("com.android.calculator2:id/result")).getText();
  System.out.println("Result of Addition is : " + result);
  Assert.assertEquals(result,"12");
  driver.pressKeyCode(AndroidKeyCode.HOME);

 }

 @Test(enabled = false)
 public void startBackgroundApp(){
  Activity activity=new Activity("com.android.calculator2",".Calculator");
  driver.startActivity(activity);
  Assert.assertEquals(driver.currentActivity(),".Calculator");
 }

 @Test(priority = 1)
 public void flipkartLogin(){
  driver.findElement(By.id("com.flipkart.android:id/btn_mlogin")).click();
  driver.findElement(By.id("com.flipkart.android:id/mobileNo")).clear();
  driver.findElement(By.id("com.google.android.gms:id/cancel")).click();
  driver.findElement(By.id("com.flipkart.android:id/mobileNo")).sendKeys("9000200055");
  driver.findElement(By.id("com.flipkart.android:id/et_password")).clear();
  driver.findElement(By.id("com.flipkart.android:id/et_password")).sendKeys("flipkart1");
  driver.findElement(By.id("com.flipkart.android:id/btn_mlogin")).click();
 }

 @Test(priority = 2)
 public void scrollToItemsInCartAndClickBuyNow() throws Exception{
  //TouchAction action=new TouchAction(driver);
  //WebElement itemsInCart=driver.findElement(By.xpath("//android.widget.TextView[@text='Item in your Cart']"));
  //action.moveTo(itemsInCart).perform();
  //WebElement element = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.flipkart.android:id/widget_title\")).getChildByText("
  //+"new UiSelector().className(\"android.widget.TextView\"), \"Item in your Cart\")"));
  scrollDown();
  String text=driver.findElement(By.id("com.flipkart.android:id/title_action_bar")).getText();
  Assert.assertEquals(text,"Delivery");
 }

 public void scrollDown() {
  int pressX = driver.manage().window().getSize().width / 2;
  int bottomY = driver.manage().window().getSize().height * 4/5;
  int topY = driver.manage().window().getSize().height / 8;
  int i = 0;
  do{
   boolean isPresent = driver.findElements(By.id("com.flipkart.android:id/widget_action_button")).size()>0;
   if(isPresent){
    WebElement we = driver.findElement(By.id("com.flipkart.android:id/widget_action_button"));
    we.click();
    break;
   }
   else{
    scroll(pressX, bottomY, pressX, topY);}
   i++;
  } while(i <= 4);
 }

 private void scroll(int fromX, int fromY, int toX, int toY) {
  TouchAction touchAction = new TouchAction(driver);
  touchAction.longPress(fromX, fromY).moveTo(toX, toY).release().perform();
 }


 @Test(priority = 3, dependsOnMethods = "scrollToItemsInCartAndClickBuyNow" )
 public void increaseQuantity(){
  driver.findElement(By.xpath("//android.view.View[@content-desc='Qty:']")).click();
  driver.findElement(By.xpath("//android.view.View[@content-desc='2']")).click();
  String text= driver.findElement(By.className("android.view.View")).getText();
  System.out.println(text);
  System.out.println(driver.findElement(By.id("CONTINUE")).isDisplayed());
 }


}
