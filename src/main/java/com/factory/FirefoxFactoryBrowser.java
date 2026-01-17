package com.factory;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class FirefoxFactoryBrowser implements BrowserFactory {

    @Override
    public BrowserType getBrowserType(Playwright playwright) {

        return playwright.firefox();
    }
}

