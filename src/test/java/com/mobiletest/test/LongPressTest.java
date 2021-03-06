package com.mobiletest.test;

import com.mobiletest.pageobjects.HomePage;
import com.mobiletest.pageobjects.LoginPage;
import com.mobiletest.pageobjects.LongPressPage;
import com.mobiletest.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LongPressTest extends BaseTest{

    LongPressPage longPressPage;
    HomePage homePage;

    @Test(description = "Verify the long press functionality")
    public void longPress(){
        homePage=new LoginPage(driver).clickLoginButton();
        longPressPage=homePage.navigateToLongPressView();
        longPressPage.longPress();
        String text=longPressPage.getAlertText();
        System.out.println(text);
        Assert.assertEquals(text,"you pressed me hard.. :P");
        longPressPage.clickOkButton();
        homePage.clickBack();
    }

    @Test(description = "Verify the long press functionality-2")
    public void longPress2(){
        homePage=new LoginPage(driver).clickLoginButton();
        longPressPage=homePage.navigateToLongPressView();
        longPressPage.longPress();
        String text=longPressPage.getAlertText();
        System.out.println(text);
        Assert.assertEquals(text,"you pressed me hard :P");
        longPressPage.clickOkButton();
        homePage.clickBack();
    }
}
