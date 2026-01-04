package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
    private WebDriver driver;
    private JavascriptExecutor jsExecutor;

    public JavaScriptUtil(WebDriver driver) {
        this.driver = driver;
        jsExecutor = (JavascriptExecutor) this.driver;
    }

    public String getTitleByJS() {
        return jsExecutor.executeScript("return document.title;").toString();
    }

    public void gobackwardByJS() {
        jsExecutor.executeScript("history.go(-1)");
    }

    public void goForwardByJS() {
        jsExecutor.executeScript("history.go(1)");
    }

    public void refreshBrowserByJS() {
        jsExecutor.executeScript("history.go(0)");
    }

    public void generateAlertByJS(String message) {
        jsExecutor.executeScript("alert('" + message + "')");
        driver.switchTo().alert().accept();
    }

    public void promptAlertByJSConfirm(String message) {
        jsExecutor.executeScript("confirm('" + message + "')");
        driver.switchTo().alert().accept();
    }

    public void generatePromptAlertByJS(String message, String inputText) {
        jsExecutor.executeScript("prompt('" + message + "')");
        driver.switchTo().alert().sendKeys(inputText);
        driver.switchTo().alert().accept();
    }

    public String getPageInnerText() {
        return jsExecutor.executeScript("return document.documentElement.innerText;").toString();
    }

    public void scrollPageDown() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollPageDown(String height) {
        jsExecutor.executeScript("window.scrollTo(0, " + height + ")");
    }

    public void scrollPageUp() {
        jsExecutor.executeScript("window.scrollTo(0,0)");
    }

    public void scrollToMiddle() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
    }

    public void scrollToElement(WebDriver driver, String elementSelector) {
        jsExecutor.executeScript("document.querySelector('" + elementSelector + "').scrollIntoView(true);");
    }

    public void scrollToViewElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickElementByJS(WebElement element) {
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public void sendKeysWithID(String id, String value) {
        jsExecutor.executeScript("document.getElementById('" + id + "').value='" + value + "''");
    }

    public void zoomPageForChromeEdgeByJS(int zoomLevel) {
        jsExecutor.executeScript("document.body.style.zoom='" + zoomLevel + "%'");
    }

    public void zoomPageForFirefoxByJS(int zoomLevel) {
        jsExecutor.executeScript("document.body.style.MozTransform='scale(" + zoomLevel + ")'");
    }

    public void drawBorder(WebElement element) {
        jsExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    public void flashElement(WebElement element) {
        String bgColor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 10; i++) {
            changeElementColor("rgb(0,200,0)", element); //1
            changeElementColor(bgColor, element); //2
        }
    }

    private void changeElementColor(String clolr, WebElement element) {
        jsExecutor.executeScript("arguments[0].style.backgroundColor='" + clolr + "'", element);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
