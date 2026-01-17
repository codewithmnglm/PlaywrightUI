package com.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private static Properties prop;

    public static synchronized Properties loadConfigFile() throws IOException {

        if (prop == null) {
            prop = new Properties();
            try (FileReader reader = new FileReader(
                    System.getProperty("user.dir") + "/src/main/resources/config.properties")) {
                prop.load(reader);
            }
        }
        return prop;
    }

}
