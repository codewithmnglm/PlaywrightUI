package com.factory;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class ChromiumFactory implements BrowserFactory {

    @Override
    public BrowserFactory getBrowserType(Playwright playwright) {
        return playwright.chromium();
    }
}
