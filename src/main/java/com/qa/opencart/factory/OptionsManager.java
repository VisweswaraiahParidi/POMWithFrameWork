package com.qa.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Properties;

public class OptionsManager {

    private ChromeOptions co;
    private FirefoxOptions fo;
    private EdgeOptions eo;
    private SafariOptions so;

    private Properties prop;

    public OptionsManager(Properties prop) {
        this.prop = prop;
    }

    public ChromeOptions getChromeOptions() {
        co = new ChromeOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            co.addArguments("--headless=new");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            co.addArguments("--incognito");
        }
        return co;
    }


    public FirefoxOptions getFirefoxOptions() {
        fo = new FirefoxOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            co.addArguments("--headless=new");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            co.addArguments("--incognito");
        }
        return fo;
    }


    public EdgeOptions getEdgeOptions() {
        eo = new EdgeOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            co.addArguments("--headless=new");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            co.addArguments("--incognito");
        }
        return eo;
    }


    public SafariOptions getCSafariOptions() {
        so = new SafariOptions();
        if (Boolean.parseBoolean(prop.getProperty("headless"))) {
            co.addArguments("--headless=new");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
            co.addArguments("--incognito");
        }
        return so;
    }
}
