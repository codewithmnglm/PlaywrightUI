package com.pages;

import com.framework.Constant;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.logs.TestLog;

import java.util.ArrayList;
import java.util.Collections;

public class SearchPage {

    Page searchPage;
    Locator from;
    Locator to;
    Locator swap;
    Locator busLink;
    Locator trainLink;
    Locator bookingTab;
    Locator helpTab;
    Locator account;
    Locator searchBus;
    Locator busFilters;
    Locator sortByRating;
    Locator sortByDepartureTime;
    Locator sortByPrice;
    Locator totalNoOfbuses;
    Locator primoBus;
    Locator clearBusFilters;
    Locator filterBuses;
    Locator acBus;
    Locator sleeperBus;
    Locator seaterBus;
    Locator singleSeaterBus;
    Locator nonACBus;
    Locator eveningBus;
    Locator highRatedBus;
    Locator liveTrackingBus;
    Locator volvoBus;


    public SearchPage(Page searchPage) {
        this.searchPage = searchPage;
        from = searchPage.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select From"));
        to = searchPage.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select To"));
        swap = searchPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Swap source and destination"));
        busLink = searchPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bus tickets"));
        trainLink = searchPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Train tickets"));
        bookingTab = searchPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bookings"));
        helpTab = searchPage.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Help"));
        account = searchPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Account"));
        searchBus = searchPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search buses"));
        busFilters = searchPage.locator("//div[contains(@class,'topFilters')]//div");
        sortByDepartureTime = searchPage.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Departure time"));
        sortByRating = searchPage.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Ratings"));
        sortByPrice = searchPage.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Price"));
        totalNoOfbuses = searchPage.locator("//span[contains(@class,'subtitle') and contains(text(),'buses')]");
        filterBuses = searchPage.locator("//h2[text()='Filter buses']");
        primoBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("Primo Bus"));
        acBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("AC"));
        sleeperBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("SLEEPER"));
        seaterBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("SEATER"));
        singleSeaterBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("Single Seats"));
        nonACBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("NONAC"));
        eveningBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("18:00-24:00"));
        highRatedBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("High Rated Buses"));
        liveTrackingBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("Live Tracking"));
        volvoBus = searchPage.locator("//div[contains(@class,'topFilter')]").getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText("Volvo Buses"));
        clearBusFilters = searchPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear all"));


    }

    public int fetchMinAndMaxPrice(String filter) { // highToLow or lowToHigh
        ArrayList<Integer> priceList = new ArrayList<>();
        Locator busList = searchPage.locator("//ul[contains(@class,'srpList')]//li");
        TestLog.stepInfo("Total No of Bus Count " + busList.count());
        for (int i = 0; i < busList.count(); i++) {
            Locator bus = busList.nth(i);
            int finalPrice = Integer.parseInt(bus.locator("p[class*='finalFare']").textContent().replaceAll("[^0-9]", ""));
            priceList.add(finalPrice);
        }
        Collections.sort(priceList);
        return filter.equals("highToLow") ? priceList.get(priceList.size() - 1) : priceList.get(priceList.get(0));

    }

    public void sortResultsBy(String filterType, String filter) {

        Locator deptTime = searchPage.getByText(filterType,
                new Page.GetByTextOptions().setExact(true));

        if (filter.contains("lowToHigh")) deptTime.click();
        else if (filter.contains("highToLow")) {
            deptTime.dblclick();
        }

    }

    public void filterByDepartureTime(String departureTime) {
        Locator departureTimeFromSource = searchPage.locator("//div[@id='dt']");
        departureTimeFromSource.click();
        Locator timeSlot = departureTimeFromSource.getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText(departureTime));
        boolean isChecked = Boolean.parseBoolean(departureTimeFromSource.getAttribute("checked"));
        if (!isChecked) {
            timeSlot.click();
            TestLog.stepInfo("Filter By Departure Time :" + departureTime + " is completed");
        } else {
            TestLog.stepInfo("Time Slot " + departureTime + "is already checked");
        }

    }

    public void filterByArrivalTime(String arrivalTime) {
        Locator arrivalAtSource = searchPage.locator("//div[@id='at']");
        arrivalAtSource.click();
        Locator searchArrivalTimeAtDestination = arrivalAtSource.getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText(arrivalTime));
        boolean isChecked = Boolean.parseBoolean(searchArrivalTimeAtDestination.getAttribute("checked"));
        if (!isChecked) {
            searchArrivalTimeAtDestination.click();
            TestLog.stepInfo("Filter By Arrival Time :" + arrivalTime + " is completed");
        } else {
            TestLog.stepInfo("Time Slot " + arrivalTime + "is already checked");
        }

    }

    public void filterByBusFeature(String busFeature) {
        Locator busFilter = searchPage.locator("//div[@id='onlyShow']");
        busFilter.click();
        Locator sortByBusFeature = busFilter.getByRole(AriaRole.CHECKBOX).filter(new Locator.FilterOptions().setHasText(busFeature));
        boolean isChecked = Boolean.parseBoolean(sortByBusFeature.getAttribute("checked"));
        if (!isChecked) {
            sortByBusFeature.click();
            TestLog.stepInfo("Filter By Bus Feature :" + busFeature + " is completed");
        } else {
            TestLog.stepInfo("Time Slot " + busFeature + "is already checked");
        }

    }

    public void filterByBusType(String busType) {
        Locator sortByBusFeature =
                searchPage
                        .locator("div[data-autoid='busType']");
        sortByBusFeature.click();
        Locator busTypeFilter = sortByBusFeature.getByRole(AriaRole.LIST).getByRole(AriaRole.LISTITEM).getByRole(AriaRole.CHECKBOX);
        Locator lc;
        if (busType.contains("AC")) {
            lc = busTypeFilter.filter(new Locator.FilterOptions().setHasText(busType)).nth(0);
        } else if (busType.contains("NONAC")) {
            lc = busTypeFilter.filter(new Locator.FilterOptions().setHasText(busType)).nth(1);

        } else {
            lc = busTypeFilter.filter(new Locator.FilterOptions().setHasText(busType));
        }
        boolean isChecked = Boolean.parseBoolean(lc.getAttribute("checked"));
        if (!isChecked) {
            lc.click();
            TestLog.stepInfo("Bus Type :" + busType + " filter is checked");
        } else {
            TestLog.stepInfo("Bus Type :" + busType + " filter is already checked");
        }

    }

    public void filterByAmenities(String amenities) {
        Locator busByAmenities = searchPage.locator("//div[@id='amtList']");
        busByAmenities.click();
        searchPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View all amenities")).click();
        Locator searchArrivalTimeAtDestination = busByAmenities.getByRole(AriaRole.LIST).getByRole(AriaRole.LISTITEM).getByRole(AriaRole.CHECKBOX);
        Locator lc = searchArrivalTimeAtDestination.filter(new Locator.FilterOptions().setHasText(amenities));
        boolean isChecked = Boolean.parseBoolean(lc.getAttribute("checked"));
        if (!isChecked) {
            lc.click();
            TestLog.stepInfo("Bus Type :" + amenities + " filter is checked");
        } else {
            TestLog.stepInfo("Bus Type :" + amenities + " filter is already checked");
        }

    }

    public void filterBySpecialBusFeature(String specialBusFeature) {
        Locator spclBusFeature = searchPage.locator("//div[@id='persuasionList']");
        spclBusFeature.click();
        Locator searchArrivalTimeAtDestination = spclBusFeature.getByRole(AriaRole.LIST).getByRole(AriaRole.LISTITEM).getByRole(AriaRole.CHECKBOX);
        Locator lc = searchArrivalTimeAtDestination.filter(new Locator.FilterOptions().setHasText(specialBusFeature));
        boolean isChecked = Boolean.parseBoolean(lc.getAttribute("checked"));
        if (!isChecked) {
            lc.click();
            TestLog.stepInfo("Bus Type :" + specialBusFeature + " filter is checked");
        } else {
            TestLog.stepInfo("Bus Type :" + specialBusFeature + " filter is already checked");
        }

    }

    public void filterByBusOperator(String operatorName) {
        Locator busOperator = searchPage.locator("//div[@id='travelsList']");
        busOperator.click();

        busOperator.getByTitle(Constant.FILTER_BY_SEARCH).fill(operatorName);
        Locator filterByBusOperator = busOperator.getByRole(AriaRole.LIST).getByRole(AriaRole.LISTITEM).getByRole(AriaRole.CHECKBOX);
        int busCount = filterByBusOperator.count();
        if (busCount < 1) {
            TestLog.stepInfo("No Bus Found With Operator : " + operatorName);
        } else {
            Locator lc = filterByBusOperator.filter(new Locator.FilterOptions().setHasText(operatorName));
            if (lc.count() > 1) {
                lc = lc.nth(0);
                lc.click();
            }
            boolean isChecked = Boolean.parseBoolean(lc.getAttribute("checked"));
            if (!isChecked) {
                lc.click();
            } else {
                TestLog.stepInfo("Bus Operator:" + operatorName + " filter is already checked");
            }

        }


    }
    public void searchByDroppingPoint(String dropLocation) {
        Locator droppingPoint = searchPage.locator("//div[@id='dpIdentifier']");
        droppingPoint.click();
        //searchPage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("View all dropping points")).click();
        Locator filterByDropLocation =
                droppingPoint.getByRole(AriaRole.LIST)
                        .getByRole(AriaRole.LISTITEM)
                        .getByRole(AriaRole.CHECKBOX);
        filterByDropLocation.first().waitFor();
        int locationCount = filterByDropLocation.count();
        TestLog.stepInfo("Total No Of Drop Location " + locationCount);
        droppingPoint.getByTitle(Constant.FILTER_BY_SEARCH).fill(dropLocation);
        if (locationCount < 1) {
            TestLog.stepInfo("No Drop Location Found With Name : " + dropLocation);
        } else {
            Locator lc = filterByDropLocation.filter(new Locator.FilterOptions().setHasText(dropLocation));
            if (lc.count() > 1) {
                lc = lc.nth(0);
            }
            boolean isChecked = Boolean.parseBoolean(lc.getAttribute("checked"));
            if (!isChecked) {
                lc.click();
                TestLog.stepInfo("Drop Location : " + dropLocation + " filter is checked");
                Locator buses = searchPage.locator("//span[contains(@class,'subtitle')]");
                TestLog.stepInfo("Total No of Bused Found: " + buses.textContent());
                droppingPoint.click();
            } else {
                TestLog.stepInfo("Drop Location : " + dropLocation + " filter is already checked");
            }

        }

    }
    public void searchByDroppingPoint2(String dropLocation) {

        Locator droppingPoint = searchPage.locator("#dpIdentifier");
        droppingPoint.click();

        Locator searchBox = droppingPoint.getByTitle(Constant.FILTER_BY_SEARCH);
        searchBox.fill(dropLocation);

        // Wait until the exact text is visible in the list
        Locator dropText =
                droppingPoint.getByRole(AriaRole.LISTITEM)
                        .getByText(
                                dropLocation
                                );

        dropText.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
        );

        // Click the TEXT (label), NOT the checkbox
        dropText.click();

        TestLog.stepInfo("Drop Location selected: " + dropLocation);

        droppingPoint.click(); // close filter
    }






}
