package com.mobiletest.utils.TestListeners;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.mobiletest.utils.FileUtilties;
import com.mobiletest.utils.BaseTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;
import org.testng.xml.XmlSuite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

public class ExtentReportWithScreenshotsFolder extends BaseTest implements IReporter,ITestListener{
    private static ExtentReports extent;
    private String screenShotNameWithTimeStamp;
    private AppiumDriver driver;
    private FileUtilties utilities;

    public ExtentReportWithScreenshotsFolder(){
        utilities=new FileUtilties();
        utilities.createDirectory("ScreenShots");
        utilities.deleteExisitngFolder(System.getProperty("user.dir") + File.separator + "ScreenShots");
    }

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        extent = new ExtentReports(outputDirectory + File.separator + "SnapdealTestReport.html", true);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();

            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }
        }
        extent.flush();
        extent.close();
        getDriver().quit(); // Quite Driver
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;
        if (tests.getAllResults().size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                Object object = result.getInstance();
                setDriver(((BaseTest) object).driver);
                test = extent.startTest(result.getMethod().getMethodName());

                test.getTest().setStartedTime(getTime(result.getStartMillis()));
                test.getTest().setEndedTime(getTime(result.getEndMillis()));

                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                String message = "Test " + status.toString().toLowerCase() + "ed";

                if (result.getThrowable() != null)
                    message = result.getThrowable().getMessage();
                String failedScreenPng = System.getProperty("user.dir") + "\\target\\screenshot\\"
                        + getDriver().toString().split(" ")[1].toLowerCase() + "\\" + result.getName() + "\\"
                        + result.getName() + "_failed" + ".png";
                try {
                    createGif(new File(failedScreenPng));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String failedScreenGif = System.getProperty("user.dir") + "\\target\\screenshot\\"
                        + getDriver().toString().split(" ")[1].toLowerCase() + "\\" + result.getName() + "\\"
                        + result.getName() + "_failed" + ".gif";
                String imgSrc = null;
                if (new File(failedScreenGif).isFile()) {

                    imgSrc = "<div class='col l4 m6 s12'><div class='card-panel'><img src=" + failedScreenGif
                            + " style=\"width:304px%;height:228px;\"></div></div>";
                }
                test.log(status, message, imgSrc);

            /*    test.setDescription(driverFactory.getDeviceModel() + "_" + driverFactory.getDeviceManufacturer() + "_"
                        + driverFactory.getDeviceSerial().get(0));
*/
                extent.endTest(test);
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public void onTestStart(ITestResult result) {

    }
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub

    }
    public void onTestFailure(ITestResult result) {
        Object currentClass = result.getInstance();
        setDriver(((BaseTest) currentClass).driver);
        try {
            if (!result.isSuccess()) {
                captureScreenShot(result.getName(), getDriver(), result.getName());
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void captureScreenShot(String screenShotName, AppiumDriver driver, String methodName)
            throws InterruptedException, IOException {
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        screenShotNameWithTimeStamp = currentDateAndTime();
        Properties prop = utilities.readProperties();
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("WebApp")) {
            String androidModel = screenShotNameWithTimeStamp
                    + getDriver().getCapabilities().getCapability(MobileCapabilityType.DEVICE_NAME);
            screenShotAndFrame(screenShotName, 2, scrFile, methodName, androidModel, "chrome");
        }
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("NativeApp")) {
            if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver")) {
                String androidModel = screenShotNameWithTimeStamp
                        + getDriver().getCapabilities().getCapability(MobileCapabilityType.DEVICE_NAME);
                screenShotAndFrame(screenShotName, 2, scrFile, methodName, androidModel, "android");
            } else if (getDriver().toString().split(":")[0].trim().equals("IosDriver")) {
                String androidModel = screenShotNameWithTimeStamp
                        + getDriver().getCapabilities().getCapability(MobileCapabilityType.DEVICE_NAME);
                screenShotAndFrame(screenShotName, 2, scrFile, methodName, androidModel, "Ios");
            }
        }
    }

    public String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }

    public void screenShotAndFrame(String screenShotName, int status, File scrFile, String methodName, String model,
                                   String platform) {
        String failedScreen = System.getProperty("user.dir") + "\\target\\screenshot\\" + platform + "\\" + methodName
                + "\\" + methodName + "_failed" + ".png";

        try {
            if (status == ITestResult.FAILURE) {
                FileUtils.copyFile(scrFile, new File(failedScreen));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onStart(ITestContext context) {
        File screenShot = new File(System.getProperty("user.dir") + "/target/screenshot/");
        if (screenShot.isDirectory()) {
            try {
                FileUtils.deleteDirectory(screenShot);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub
    }

    public static void createAnimatedGif(File testScreenshots, File animatedGif) throws IOException {
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(animatedGif.getAbsolutePath());
        encoder.setDelay(1500 /* 1.5 seconds */);
        encoder.setQuality(10 /* highest */);
        encoder.setRepeat(1 /* infinite */);
        encoder.setTransparent(Color.WHITE);

        int width = 0;
        int height = 0;
        if (testScreenshots.exists()) {
            BufferedImage bufferedImage = ImageIO.read(testScreenshots);
            width = Math.max(bufferedImage.getWidth(), width);
            height = Math.max(bufferedImage.getHeight(), height);

            encoder.setSize(width, height);
            encoder.addFrame(ImageIO.read(testScreenshots));
            encoder.finish();
        }
    }

    /**
     * Create Gif File
     *
     * @throws IOException
     */
    public static void createGif(File file) throws IOException {
        if (file.getName().contains("png")) {
            createAnimatedGif(file, new File(file.getParent() + "/" + file.getName().replace(".png", "") + ".gif"));
            file.deleteOnExit();
        }
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void setDriver(AppiumDriver driver) {
        this.driver = driver;
    }


}
