package com.factory;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public interface BrowserFactory {

    BrowserType getBrowserType(Playwright playwright);

}
