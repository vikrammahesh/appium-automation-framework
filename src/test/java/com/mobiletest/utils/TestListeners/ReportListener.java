package com.mobiletest.utils.TestListeners;

import com.mobiletest.utils.BaseTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class ReportListener extends BaseTest implements ITestListener {

    ExtentReports extentReports;
    ExtentTest extensTest;


    public ReportListener(){
        String workingDir=System.getProperty("user.dir");
        extentReports=new ExtentReports(workingDir+"\\Reports\\ExtentReportResults.html",true);
    }

    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart metnod.."+iTestResult.getMethod().getMethodName());
        extensTest=extentReports.startTest(iTestResult.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess metnod.."+iTestResult.getMethod().getMethodName());
        extensTest.log(LogStatus.PASS,"Test passed");
    }

    public void onTestFailure(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        AppiumDriver driver = ((BaseTest) testClass).getDriver();
        System.out.println("I am in onTestFailure metnod.."+iTestResult.getMethod().getMethodName());
        String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        //Extentreports log and screenshot operations for failed tests.
        extensTest.log(LogStatus.FAIL,"Test Failed",
                extensTest.addBase64ScreenShot(base64Screenshot));

    }

    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped metnod.."+iTestResult.getMethod().getMethodName());
        extensTest.log(LogStatus.SKIP,"Test Skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + iTestResult.getMethod().getMethodName());
    }

    public void onStart(ITestContext iTestContext) {
        System.out.println("I am in OnStart method.."+iTestContext.getName());
    }

    public void onFinish(ITestContext iTestContext) {
        extentReports.flush();

    }
}
