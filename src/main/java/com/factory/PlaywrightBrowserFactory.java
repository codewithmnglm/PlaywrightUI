package com.factory;


public  class PlaywrightBrowserFactory {


    public BrowserFactory launchBrowser(String browserName) {

        switch (browserName) {

            case "chromium":
                return new ChromiumBrowser();
            case "firefox":
                return new FirefoxBrowser();
            default:
                throw new IllegalArgumentException("Invalid browser: " + browserName);


        }


    }

}
