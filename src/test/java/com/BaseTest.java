package com;

import com.factory.*;
import com.utils.ConfigLoader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.IOException;



public class BaseTest {

    @BeforeClass
    public void initializeBrowser() throws IOException {
        String browserName= ConfigLoader.loadConfigFile().get("browser").toString();
        PlaywrightManager.initBrowser(browserName);
    }

    @AfterClass
    public void closeBrowser() {
        PlaywrightManager.closeBrowser();
    }






}
