package com.factory.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import java.util.regex.Pattern;


public class HomePage {

    Page homePage;

    Locator redBusIcon;
    Locator busTicketLink;
    Locator trainTicketLink;
    Locator accountTab;
    Locator booking;
    Locator from;
    Locator to;
    Locator swapButton;
    Locator searchBus;
    Locator searchTrain;
    Locator today;
    Locator tomorrow;
    Locator selectDate;
    Locator bookingForWomen;


    public HomePage(Page homePage) {
        this.homePage = homePage;

        // Initialize locators AFTER homePage is set
        redBusIcon = homePage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("redBus logo"));
        busTicketLink = homePage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bus tickets"));
        trainTicketLink = homePage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Train tickets"));
        accountTab = homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Account"));
        booking = homePage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Booking"));
        from = homePage.getByLabel("From");
        to = homePage.locator("//div[contains(@class,'inputWrapper')]").nth(1).getByLabel("To");
        swapButton = homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Swap source and destination"));
        searchBus = homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search buses"));
        searchTrain = homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search trains"));
        today = homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search for Today"));
        tomorrow = homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search for Tomorrow"));
        selectDate = homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("Select Date of Journey")));
        bookingForWomen= homePage.getByRole(AriaRole.SWITCH,new Page.GetByRoleOptions().setName("Booking for Women"));
    }


    public String getHomePageTitle() {
        return homePage.title();
    }

    public String getHomePageUrl() {
        return homePage.url();
    }

    public void searchBus(String source, String destination, String date) throws InterruptedException {

        from.click(new Locator.ClickOptions().setForce(true));
        from.clear();
        from.fill(source);
        waitForSuggestions(source);
        to.click(new Locator.ClickOptions().setForce(true));
        to.clear();
        to.fill(destination);
        waitForSuggestions(destination);
        tomorrow.click();
        searchBus.click();
    }

   public void waitForSuggestions(String input) throws InterruptedException {

       Thread.sleep(2000);
       Locator listBox =
               homePage.getByRole(
                       AriaRole.LISTBOX,
                       new Page.GetByRoleOptions()
                               .setName("Search suggestions list")
               );
       Thread.sleep(2000);
       Locator suggestionsCategory =
               listBox.locator("//div[contains(@class,'searchCategory')]").first();
       Locator suggestionOptions =
               suggestionsCategory.getByRole(AriaRole.OPTION);
       homePage.waitForCondition(() -> suggestionOptions.count() > 0);

       int count = suggestionOptions.count();

       for (int i = 0; i < count; i++) {
           Locator option = suggestionOptions.nth(i);
           String text = option.textContent().trim();

           if (text.contains(input)) {
               option.click();
               break;
           }
       }

   }

   public void enableBookingForWomen() throws InterruptedException {

        boolean flag = Boolean.parseBoolean(bookingForWomen.getAttribute("aria-checked"));
        if(!flag){
            bookingForWomen.click();
        }

   }
    public void disableBookingForWomen() throws InterruptedException {

        boolean flag = Boolean.parseBoolean(bookingForWomen.getAttribute("aria-checked"));
        if(flag){
            bookingForWomen.click();
        }

    }


}
