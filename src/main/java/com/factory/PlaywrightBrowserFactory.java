package com.factory;


public class PlaywrightBrowserFactory {


    public static BrowserFactory browserFactory(String browserName) {


        if (browserName == null) {
            throw new IllegalArgumentException("Browser name cannot be null");
        }

        switch (browserName.toLowerCase()) {
            case "chrome":
                return new ChromiumBrowserFactory();
            case "firefox":
                return new FirefoxFactoryBrowser();
            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browserName
                );
        }


    }

}
