package com.utils;

import java.util.HashMap;
import java.util.Map;

public class Mapping {

    public static Map<String, String> busTimings;

    static {
        busTimings = new HashMap<>();
        busTimings.put("MORNING", "06:00-12:00");
        busTimings.put("AFTERNOON", "12:00-18:00");
        busTimings.put("EVENING", "18:00-24:00");
        busTimings.put("NIGHT", "00:00-06:00");
    }

    public enum BusFeatures {
        LiveTracking,
        HighRatedBuses,
        Deals,
        PrimoBus
    }

    public enum BusTypes {
        AC,
        NONAC,
        SEATER,
        SLEEPER,
        VolvoBuses,
    }

    public enum Aminities {
        WaterBottle,
        Blankets,
        WIFI,
        Toilet,
        BedSheet,
    }

    public enum SpecialBusFeatures{

        NewBus,
        OnTime,
        Toilet,
        Highlyratedbywomen,
        WomenTraveling,

    }

    public enum Browser {
        Firefox,
        Chromium,
        Safari,
        Edge,

    }


}
