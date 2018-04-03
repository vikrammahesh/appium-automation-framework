package com.mobiletest.utils.ExtentReports;

import com.mobiletest.utils.FileUtilties;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

    private static ExtentReports extent;

    public synchronized static ExtentReports getReporter(){
        if(extent == null){
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            FileUtilties fileUtilties =new FileUtilties();
            fileUtilties.createDirectory("reports");
            extent = new ExtentReports(workingDir+"\\reports\\ExtentReportResults.html", true);
        }
        return extent;
    }
}
