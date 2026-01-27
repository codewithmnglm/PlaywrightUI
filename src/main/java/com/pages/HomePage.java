package com.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.logs.TestLog;
import com.utils.CalendarUtil;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.HashMap;
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
    Locator knowMore;
    Locator bookingForWomenPopUp;
    Locator closeButton;
    Locator yesBookingForWomen;
    Locator notNowBookingForWomen;
    Locator previousMonth;
    Locator nextMonth;



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
        knowMore= homePage.locator("//div[text()='Know more']");
        bookingForWomenPopUp= homePage.getByRole(AriaRole.DIALOG,new Page.GetByRoleOptions().setName("Booking for Women"));
        closeButton= bookingForWomenPopUp.getByLabel("Close");
        yesBookingForWomen= bookingForWomenPopUp.getByRole(AriaRole.BUTTON,new Locator.GetByRoleOptions().setName("Yes, booking for women"));
        notNowBookingForWomen= bookingForWomenPopUp.getByRole(AriaRole.BUTTON,new Locator.GetByRoleOptions().setName("Not now"));
        previousMonth= homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Previous month"));
        nextMonth= homePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next month"));
    }


    public String getHomePageTitle() {
        return homePage.title();
    }

    public String getHomePageUrl() {
        return homePage.url();
    }

    public void searchBus(String source, String destination, String date) throws InterruptedException {

        TestLog.stepInfo("Searching for bus tickets");

        from.clear();
        from.click();
        from.fill(source);
        waitForSuggestions();
        Locator firstSearch = homePage.locator("//div[contains(@class,'searchCategory')]").first();
        firstSearch.getByRole(AriaRole.OPTION).getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setName(source).setExact(true)).click();
        to.click();
        to.clear();
        to.fill(destination);
        waitForSuggestions();
        Locator firstSearchTo = homePage.locator("//div[contains(@class,'searchCategory')]").first();
        firstSearchTo.getByRole(AriaRole.OPTION).getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setName("Chennai").setExact(true)).click();


        searchBus.click();
    }

   public void waitForSuggestions() {

       homePage.getByRole(AriaRole.LISTBOX, new Page.GetByRoleOptions().setName("Search suggestions list")).waitFor();

   }

   public void bookingForWomen() throws InterruptedException {

        enableBookingForWomen();
        knowMore.click();
        bookingForWomenPopUp.waitFor();
       notNowBookingForWomen.click();

   }

   public void enableBookingForWomen() throws InterruptedException {

        boolean flag = Boolean.parseBoolean(bookingForWomen.getAttribute("aria-checked"));
        if(!flag){
            bookingForWomen.click();
            TestLog.stepInfo("Enabling Booking for Women");
        }

   }
    public void disableBookingForWomen() throws InterruptedException {

        boolean flag = Boolean.parseBoolean(bookingForWomen.getAttribute("aria-checked"));
        if(flag){
            bookingForWomen.click();
            TestLog.stepInfo("Disabling Booking for Women");
        }

    }

    public void selectDate(int year, String monthInput,int day) throws InterruptedException {

        HashMap<String,Integer> monthMap = CalendarUtil.mapCalendarMonth();
        int month = monthMap.get(monthInput);
        LocalDate selectedDate = LocalDate.of(year, month, day);
        LocalDate today = LocalDate.now();

        if (selectedDate.isBefore(today)) {
            throw new RuntimeException(
                    "Date before current date can't be selected in calendar"
            );
        }
        selectDate.click();
        while (true) {
            String monthYearText = getMonthYear().innerText(); // "March 2026"
            String[] parts = monthYearText.split(" ");

            String currentMonth = parts[0];
            int currentYear = Integer.parseInt(parts[1]);

            if (currentYear == year &&
                    monthMap.get(currentMonth) == month) {
                break;
            }

            nextMonth.click();
        }
        Locator calendarRows =
                homePage.getByRole(AriaRole.GRID)
                        .getByRole(AriaRole.ROW);
        Locator firstCalendarRow= calendarRows.first();
        int firstRowCellCount = firstCalendarRow.getByRole(AriaRole.GRIDCELL).getByRole(AriaRole.BUTTON).count();
        int rowIndex=0,cellIndex=0;
        int remainingDays = Math.max(day - firstRowCellCount, 0);
        if(remainingDays==0){
            cellIndex=day-1;
        }
        else{
            rowIndex= (remainingDays/7);
            cellIndex=remainingDays%7;
            if(cellIndex!=0){
                rowIndex++;

            }
        }
        calendarRows.nth(rowIndex).first().getByRole(AriaRole.GRIDCELL).nth(cellIndex-1).click();
    }


    public Locator getMonthYear(){
        return homePage.locator("//p[contains(@class,'monthYear')]");
    }


}
