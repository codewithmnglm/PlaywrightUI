package com.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.logs.TestLog;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListeners implements ITestListener {

    private static ExtentReports extentReports;
    private static ExtentReports extent = getExtent();
    private static final String OUTPUT_FOLDER = "./report";
    private static final String fileName= "PlaywrightUIReport.html";
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();



    public static ExtentReports getExtent(){

         if(extentReports==null){

             ensureReportDirectory();
             ExtentSparkReporter spark =
                     new ExtentSparkReporter(OUTPUT_FOLDER + "/" + fileName);
             spark.config().setReportName("Playwright Automation UI");
             spark.config().setDocumentTitle("Automation UI");
             extentReports = new ExtentReports();
             extentReports.attachReporter(spark);

             extentReports.setSystemInfo("Framework", "Playwright + Java");
             extentReports.setSystemInfo("Author", "Mangalam");
             extentReports.setSystemInfo("OS", "MAC");

         }

         return extentReports;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        TestLog.stepInfo(testName + " Test Failed ");
        TestLog.stepInfo("Class Name : " + result.getTestClass().getName());
        TestLog.stepInfo("Test Start Time " + result.getStartMillis());
        TestLog.stepInfo("Test End Time " + result.getEndMillis());
        ExtentTest extentTest = test.get();
        if (extentTest == null) {
            extentTest = extent.createTest(testName);
            test.set(extentTest);
        }
        extentTest.fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(ScreenshotUtil.takeScreenShot()).build());
        extentTest.fail(result.getThrowable() != null ? result.getThrowable() : new Exception("Test failed"));

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        TestLog.stepInfo(testName + " Test Passed ");
        TestLog.stepInfo("Class Name : " + result.getTestClass().getName());
        TestLog.stepInfo("Test Start Time " + result.getStartMillis());
        TestLog.stepInfo("Test End Time " + result.getEndMillis());
        ExtentTest extentTest = test.get();
        if (extentTest == null) {
            extentTest = extent.createTest(testName);
            test.set(extentTest);
        }
        extentTest.pass("Test passed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        TestLog.stepInfo(testName + " Test Skipped ");
        ExtentTest extentTest = test.get();
        if (extentTest == null) {
            extentTest = extent.createTest(testName);
            test.set(extentTest);
        }
        extentTest.skip("Test skipped");
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        TestLog.stepInfo(testName + " Test Case From Class " + result.getTestClass().getName() + " Started " );
        test.set(extent.createTest(testName));

    }

    @Override
    public void onFinish(ITestContext context) {
        TestLog.stepInfo("Passed Test Cases " + context.getPassedTests().size());
        TestLog.stepInfo("Failed Test Cases " + context.getFailedTests().size());
        TestLog.stepInfo("Skipped Test Cases " + context.getSkippedTests().size());
        if (extent != null) {
            extent.flush();
        }
    }

    private static void ensureReportDirectory() {
        try {
            Path path = Paths.get(OUTPUT_FOLDER);
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
        } catch (Exception e) {
            TestLog.warn("Failed to create report directory: " + e.getMessage());
        }
    }

}
