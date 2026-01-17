package com.factory;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class ChromiumBrowserFactory implements BrowserFactory {

    @Override
    public BrowserType getBrowserType(Playwright playwright) {
        return playwright.chromium();
    }
}
