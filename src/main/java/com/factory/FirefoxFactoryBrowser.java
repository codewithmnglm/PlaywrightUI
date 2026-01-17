package com.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FirefoxBrowser implements BrowserFactory {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @Override
    public Page openBrowser() {
        playwright = Playwright.create();
        browser = playwright.firefox()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        return page;
    }

    @Override
    public void closeBrowser() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
