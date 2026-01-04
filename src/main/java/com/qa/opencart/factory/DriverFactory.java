// java
package com.qa.opencart.factory;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * DriverFactory class is responsible for initializing the WebDriver and loading properties from a configuration file.
 */
public class DriverFactory {
    WebDriver driver;
    Properties prop;
    OptionsManager optionsManager;
    public static String highlight;

    // encapsulated ThreadLocal driver
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * This method initializes the WebDriver based on the provided browser name.
     *
     * @param prop The properties containing browser and url etc.
     * @return An instance of WebDriver for the specified browser.
     */
    public WebDriver initDriver(Properties prop) {
        String browser = prop.getProperty("browser").trim();
        String url = prop.getProperty("url").trim();
        System.out.println("Browser name is: " + browser);
        optionsManager = new OptionsManager(prop);
        highlight = prop.getProperty("highlightElements");

        WebDriver createdDriver = null;

        switch (browser.toLowerCase()) {
            case "chrome":
                System.out.println("Initializing ChromeDriver");
                createdDriver = new ChromeDriver(optionsManager.getChromeOptions());
                break;
            case "firefox":
                System.out.println("Initializing FirefoxDriver");
                createdDriver = new FirefoxDriver(optionsManager.getFirefoxOptions());
                break;
            case "edge":
                System.out.println("Initializing EdgeDriver");
                createdDriver = new EdgeDriver(optionsManager.getEdgeOptions());
                break;
            case "safari":
                System.out.println("Initializing SafariDriver");
                createdDriver = new SafariDriver();
                break;
            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }

        // set into ThreadLocal so listener/tests can access same instance
        setDriver(createdDriver);

        // safety checks
        if (getDriver() == null) {
            throw new RuntimeException("Failed to initialize WebDriver for browser: " + browser);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(url);
        return getDriver();
    }

    /**
     * Returns the WebDriver associated with current thread.
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * Set WebDriver for current thread.
     */
    public static void setDriver(WebDriver driver) {
        tlDriver.set(driver);
    }

    /**
     * Remove WebDriver from ThreadLocal (call on teardown).
     */
    public static void unload() {
        tlDriver.remove();
    }

    /**
     * This method initializes and loads properties from a configuration file.
     *
     * @return A Properties object containing the loaded properties.
     */
    public Properties initProp() {

        FileInputStream ip = null;
        prop = new Properties();
        String envName = System.getProperty("env");
        System.out.println("env name is: " + envName);
        try {
            if (envName == null) {
                System.out.println("no environment given.....running on QA");
                ip = new FileInputStream("src/test/resources/config/qa.config.properties");
            } else {
                switch (envName.toLowerCase().trim()) {
                    case "qa":
                        ip = new FileInputStream("src/test/resources/config/qa.config.properties");
                        break;
                    case "dev":
                        ip = new FileInputStream("src/test/resources/config/dev.config.properties");
                        break;
                    case "uat":
                        ip = new FileInputStream("src/test/resources/config/uat.config.properties");
                        break;
                    case "stage":
                        ip = new FileInputStream("src/test/resources/config/stage.config.properties");
                        break;
                    default:
                        System.out.println("please pass the right environment : " + envName);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (ip != null) {
                prop.load(ip);
            } else {
                System.err.println("Properties input stream is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }

    /**
     * Take screenshot and save under reports/screenshots and return relative path (for extent).
     */
    public static String getScreenshot(String methodName) {
        if (getDriver() == null) {
            System.err.println("WebDriver is null - cannot take screenshot");
            return null;
        }

        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

        String projectDir = System.getProperty("user.dir");
        String reportsDir = projectDir + File.separator + "reports";
        String screenshotsDir = reportsDir + File.separator + "screenshots";

        File dir = new File(screenshotsDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = methodName + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(dir, fileName);

        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return "screenshots/" + fileName;
    }

}
