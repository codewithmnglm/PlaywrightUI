package com.factory;

import com.microsoft.playwright.*;

public class PlaywrightManager {

    private static ThreadLocal<BrowserType> browserType = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();


    public static void initBrowser(String browserName) {

        playwright.set(Playwright.create());
        BrowserFactory browserFactory = PlaywrightBrowserFactory.browserFactory(browserName);
        browserType.set(browserFactory.getBrowserType(playwright.get()));
        browserThreadLocal.set(browserType.get().launch(
                new BrowserType.LaunchOptions().setHeadless(false)));
        context.set(browserThreadLocal.get().newContext());
        page.set(context.get().newPage());


    }

    public static Page getPage() {
        return page.get();
    }

    public static void closeBrowser() {

        context.get().close();
        browserThreadLocal.get().close();
        playwright.get().close();
    }


}
