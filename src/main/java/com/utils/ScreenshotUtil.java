package com.utils;

import com.factory.PlaywrightManager;
import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class ScreenshotUtil {

    public static String takeScreenShot(){
        String screenshotPath =System.getProperty("user.dir")+"/screenshots/"+System.currentTimeMillis()+".png";
         PlaywrightManager.getPage().
                screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get(screenshotPath)));
         return screenshotPath;

    }
}
