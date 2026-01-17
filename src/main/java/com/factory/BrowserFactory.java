package com.factory;

import com.microsoft.playwright.Page;

public interface BrowserFactory {

     Page openBrowser();
     void closeBrowser();

}
