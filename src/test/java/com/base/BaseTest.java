package com.base;

import com.factory.*;
import com.utils.ConfigLoader;
import com.logs.TestLog;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.Properties;


public class BaseTest {

    @BeforeMethod
    public void initializePage() throws IOException {
        TestLog.stepInfo("========== Test Setup Started ==========");

        try {
            Properties config = ConfigLoader.loadConfigFile();
            TestLog.stepInfo("Configuration loaded");
            String browserName = config.get("browser").toString();

            TestLog.stepInfo("Config file loaded successfully");
            TestLog.stepInfo("Browser selected: " + browserName);

            PlaywrightManager.initPage(browserName);

            TestLog.stepInfo("Browser launched successfully");

        } catch (Exception e) {
            TestLog.stepInfo("❌ Failed during browser initialization");
            e.printStackTrace();
            throw e;
        }
    }

    public void initializeBrowser() throws IOException {
        TestLog.stepInfo("========== Test Setup Started ==========");

        try {
            Properties config = ConfigLoader.loadConfigFile();
            TestLog.stepInfo("Configuration loaded");
            String browserName = config.get("browser").toString();

            TestLog.stepInfo("Config file loaded successfully");
            TestLog.stepInfo("Browser selected: " + browserName);

            PlaywrightManager.initBrowser(browserName);

            TestLog.stepInfo("Browser launched successfully");

        } catch (Exception e) {
            TestLog.stepInfo("❌ Failed during browser initialization");
            e.printStackTrace();
            throw e;
        }
    }

    @AfterMethod
    public void closeBrowser() {

        TestLog.stepInfo("========== Test Teardown Started ==========");

        try {
            TestLog.stepInfo("Closing browser...");
            PlaywrightManager.closeBrowser();
            TestLog.stepInfo("Browser closed successfully");
        } catch (Exception e) {
            TestLog.stepInfo("❌ Error while closing browser");
            e.printStackTrace();
        }

        TestLog.stepInfo("==========================================");
    }







}
