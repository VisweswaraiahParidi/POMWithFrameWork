// java
package com.qa.opencart.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.ExtentTest;
import com.qa.opencart.factory.DriverFactory;
import org.openqa.selenium.WebDriver;

public class ExtentLogger {

    private static ExtentTest getTest() {
        return ExtentReportListener.test == null ? null : ExtentReportListener.test.get();
    }

    public static void info(String message) {
        ExtentTest t = getTest();
        if (t != null) t.info(message);
    }

    public static void pass(String message) {
        ExtentTest t = getTest();
        if (t != null) t.pass(message);
    }

    public static void passWithScreenshot(String message, WebDriver driver, String methodName) {
        ExtentTest t = getTest();
        if (t == null) return;
        try {
            String path = DriverFactory.getScreenshot("Success_Screenshot_" + methodName + "_" + System.currentTimeMillis());
            t.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (Exception e) {
            t.pass(message + " (screenshot failed: " + e.getMessage() + ")");
        }
    }

    public static void fail(String message) {
        ExtentTest t = getTest();
        if (t != null) t.fail(message);
    }

    public static void fail(Throwable throwable) {
        ExtentTest t = getTest();
        if (t != null) t.fail(throwable);
    }

    public static void failWithScreenshot(String message, Throwable throwable) {
        ExtentTest t = getTest();
        if (t == null) return;
        try {
            String path = DriverFactory.getScreenshot("Failure_Screenshot_" + System.currentTimeMillis());
            t.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            if (throwable != null) t.fail(throwable);
        } catch (Exception e) {
            t.fail(message + " (screenshot failed: " + e.getMessage() + ")");
            if (throwable != null) t.fail(throwable);
        }
    }

    public static void skip(String message) {
        ExtentTest t = getTest();
        if (t != null) t.skip(message);
    }

    public static void warn(String message) {
        ExtentTest t = getTest();
        if (t != null) t.warning(message);
    }
}
