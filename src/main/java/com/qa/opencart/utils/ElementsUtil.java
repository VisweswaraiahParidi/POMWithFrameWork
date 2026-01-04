package com.qa.opencart.utils;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.frameworkException.FrameworkException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class ElementsUtil {
    private final WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    private Alert alert;
    private JavaScriptUtil javaScriptUtil;

    public ElementsUtil(WebDriver driver) {
        this.driver = driver;
        actions = new Actions(driver);
        javaScriptUtil = new JavaScriptUtil(driver);
    }

    public void doSendKeys(By locator, String value) {
        if (value == null || value.isEmpty()) {
            System.out.println("Value is null so cannot send to the element: " + locator);
            throw new FrameworkException("VALUE_IS_NULL");
        }
        getElement(locator).sendKeys(value);
    }

    public WebElement getElement(By locator) {
        WebElement element = driver.findElement(locator);
        if (Boolean.parseBoolean(DriverFactory.highlight)) {
            javaScriptUtil.flashElement(element);
        }
        return element;
    }

    public void doClick(By locator) {
        getElement(locator).click();
    }

    public String getText(By locator) {
        String eleTxt = getElement(locator).getText();
        System.out.println("Element text is: " + eleTxt);
        return eleTxt;
    }

    public boolean checkElementDisplayed(By locator) {
        boolean isDisplayed = getElement(locator).isDisplayed();
        System.out.println("Is element displayed: " + isDisplayed);
        return isDisplayed;
    }

    public String getAttribute(By locator, String attributeName) {
        String attrValue = getElement(locator).getAttribute(attributeName);
        System.out.println("Attribute value of " + attributeName + " is: " + attrValue);
        return attrValue;
    }

    public boolean isElementEnabled(By locator) {
        boolean isEnabled = getElement(locator).isEnabled();
        System.out.println("Is element enabled: " + isEnabled);
        return isEnabled;
    }

    public String getValueAttribute(By locator) {
        String value = getElement(locator).getAttribute("value");
        System.out.println("Value attribute is: " + value);
        return value;
    }

    public List<WebElement> getElements(By locator) {
        int elementsCount = driver.findElements(locator).size();
        List<WebElement> element = driver.findElements(locator);
        System.out.println("Number of elements found: " + elementsCount);
        return element;
    }

    public int getElementsCount(By locator) {
        int elementsCount = driver.findElements(locator).size();
        System.out.println("Number of elements found: " + elementsCount);
        return elementsCount;
    }

    public List<String> getElementTextList(By locator) {
        List<WebElement> eleList = getElements(locator);
        List<String> text = new ArrayList<>();
        for (WebElement e : eleList) {
            String eleText = e.getText();
            if (!e.getText().isEmpty()) {
                text.add(eleText);
            }
        }
        return text;
    }

    public List<String> getElementAttributeList(By locator, String attributeName) {
        List<WebElement> eleList = getElements(locator);
        List<String> attrList = new ArrayList<>();
        for (WebElement e : eleList) {
            String attrValue = e.getAttribute(attributeName);
            if (!attrValue.isEmpty()) {
                attrList.add(attrValue);
            }
        }
        System.out.println("Attribute values are: " + attrList);
        return attrList;
    }

    public String getAmazonFooterText(String footerText) {
        String text = driver.findElement(By.xpath("//a[text()='" + footerText + "']/ancestor::div[@class='navFooterLinkCol navAccessibility']/div")).getText();
        return text;
    }

    public void clickonElement(By locator, String elementText) {

        List<WebElement> elements = getElements(locator);
        for (WebElement e : elements) {
            System.out.println("Total links available: " + elements.size());
            String text = e.getText();
            System.out.println();
            if (text.equals(elementText)) {
                e.click();
                break;
            }
        }
    }

    public void checkPolidrom(String input) {
        String reversed = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            reversed = reversed + input.charAt(i);
        }
        if (input.equals(reversed)) {
            System.out.println(input + " is a palindrome");
        } else {
            System.out.println(input + " is not a palindrome");
        }
    }

    public void doSearch(By locator, By loc, String searchKey, String suggestion) throws InterruptedException {
        if (searchKey == null || searchKey.isEmpty()) {
            System.out.println("Search key is null so cannot perform search operation on the element: " + locator);
            throw new FrameworkException("SEARCH_KEY_IS_NULL");
        }
        doSendKeys(locator, searchKey);
        Thread.sleep(2000);
        List<WebElement> list = getElements(loc);
        System.out.println("Total suggestions: " + list.size());
        for (WebElement e : list) {
            System.out.println(e.getText());
            if (e.getText().contains(suggestion)) {
                e.click();
                break;
            }
        }
    }

    public void forignHeattoCelsiusConversion(double fahrenheit) {
        double celsius = (fahrenheit - 32) * 5 / 9;
        System.out.println(fahrenheit + " degree Fahrenheit is equal to " + celsius + " degree Celsius.");
    }

    public void sortArrayindecendingOrder(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int num : arr) {
            list.add(num);
        }
        Collections.sort(list);
        System.out.println("Array in ascending order: " + list);
    }

    /***DROPDOWN UTILITIES***/
    public void selectfromDropDownByValue(By locator, String value) {
        if (value == null || value.isEmpty()) {
            System.out.println("Value is null so cannot select from dropdown element: " + locator);
            throw new FrameworkException("DROPDOWN_VALUE_IS_NULL");
        }
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(value);
    }

    public void selectfromDropDownByIndex(By locator, int index) {
        if (index < 0) {
            System.out.println("Index cannot be negative for dropdown element: " + locator);
            throw new FrameworkException("DROPDOWN_INDEX_IS_INVALID");
        }
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);
    }

    public void selectfromDropDownByVisibleText(By locator, String text) {
        if (text == null || text.isEmpty()) {
            System.out.println("Visible text is null so cannot select from dropdown element: " + locator);
            throw new FrameworkException("DROPDOWN_VISIBLE_TEXT_IS_NULL");
        }
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(text);
    }

    public void selectfromDropDownByContainsText(By locator, String text) {
        if (text == null || text.isEmpty()) {
            System.out.println("Visible text is null so cannot select from dropdown element: " + locator);
            throw new FrameworkException("DROPDOWN_CONTAINS_TEXT_IS_NULL");
        }
        Select select = new Select(getElement(locator));
        select.selectByContainsVisibleText(text);
    }

    public List<String> getDropDownTextList(By locator) {
        Select select = new Select(getElement(locator));
        List<WebElement> selectOptions = select.getOptions();
        List<String> optionsText = new ArrayList<String>();
        for (WebElement e : selectOptions) {
            optionsText.add(e.getText());
        }
        return optionsText;
    }

    public int getSelectOptionsCount(By locator) {
        Select select = new Select(getElement(locator));
        List<WebElement> selectOptions = select.getOptions();
        System.out.println("Total options in the dropdown are: " + selectOptions.size());
        return selectOptions.size();
    }

    public void doSelectClassDropDownWithoutSelectClass(By locator, String dropDownvalue) {
        if (dropDownvalue == null || dropDownvalue.isEmpty()) {
            System.out.println("Value is null so cannot select from dropdown element: " + locator);
            throw new FrameworkException("DROPDOWN_VALUE_IS_NULL");
        }
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();
        System.out.println("Total options in the dropdown are: " + optionsList.size());
        for (WebElement e : optionsList) {
            String text = e.getText();
            System.out.println(text);
            if (text.equals(dropDownvalue)) {
                e.click();
                break;
            }
        }
    }

    public void doSlectElimentwithoutusingslectElement(By locator, String value) {
        if (value == null || value.isEmpty()) {
            System.out.println("Value is null so cannot select from dropdown element: " + locator);
            throw new FrameworkException("DROPDOWN_VALUE_IS_NULL");
        }
        List<WebElement> optionsList = getElements(locator);
        System.out.println("Total options in the dropdown are: " + optionsList.size());
        for (WebElement e : optionsList) {
            String text = e.getText();
            System.out.println(text);
            if (text.equals(value)) {
                e.click();
                break;
            }
        }
    }

    /***ACTIONS CLASS UTILITIES***/

    public void selectRightClickOption(By locator, String option) {
        actions = new Actions(driver);
        actions.contextClick(getElement(locator)).perform();
        By optionlocator = By.xpath("//*[text()='" + option + "']");
        doClick(optionlocator);
    }

    public void mouseHover(By locator) {
        actions = new Actions(driver);
        actions.moveToElement(getElement(locator)).perform();
    }

    public void twoLevelMenuHandling(By parentMenu, By childMenu) throws InterruptedException {
        actions = new Actions(driver);
        actions.moveToElement(getElement(parentMenu)).perform();
        Thread.sleep(1000);
        doClick(childMenu);
    }

    public void threeLevelMenuHandling(By parentMenu, By childMenu, By subChildMenu) throws InterruptedException {
        actions = new Actions(driver);
        actions.moveToElement(getElement(parentMenu)).perform();
        Thread.sleep(1000);
        actions.moveToElement(getElement(childMenu)).perform();
        Thread.sleep(1000);
        doClick(subChildMenu);
    }

    public void doSendKeysUsingActions(By locator, String value) {
        if (value == null || value.isEmpty()) {
            System.out.println("Value is null so cannot send to the element: " + locator);
            throw new FrameworkException("VALUE_IS_NULL");
        }
        actions = new Actions(driver);
        actions.sendKeys(getElement(locator), value).perform();
    }

    public void doClickUsingActions(By locator) {
        actions = new Actions(driver);
        actions.click(getElement(locator)).perform();
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not necessary that is visible on UI.
     *
     * @param locator The locator used to find the element
     * @param timeOut The maximum time to wait in seconds
     * @return The visible WebElement
     */
    public WebElement waitforElementPresence(By locator, int timeOut) {
        // Implementation for waiting for an element's presence
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        if (Boolean.parseBoolean(DriverFactory.highlight)) {
            javaScriptUtil.flashElement(element);
        }
        return element;
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
     *
     * @param element The WebElement to wait for visibility
     * @param timeOut The maximum time to wait in seconds
     * @return The visible WebElement
     */
    public WebElement waitForVisibilityOfElement(By element, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        if (Boolean.parseBoolean(DriverFactory.highlight)) {
            javaScriptUtil.flashElement(element1);
        }
        return element1;
    }

    public Boolean waitForTitleContains(String titleFraction, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        try {
            Thread.sleep(2000);
            return wait.until(ExpectedConditions.titleContains(titleFraction));
        } catch (InterruptedException e) {
            System.out.println("Some issue occurred while waiting for title to contain: " + titleFraction);
        }
        return null;
    }

    public String waitFOrTitleIs(String titleValue, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        try {
            if (wait.until(ExpectedConditions.titleIs(titleValue))) {
                return driver.getTitle();
            } else {
                System.out.println("Title is not Present: " + titleValue);
                return null;
            }
        } catch (TimeoutException e) {
            System.out.println("Title is not Present: " + titleValue);
            return null;
        }
    }

    public void waitForFrameAndSwitchToIt(By locator, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public void waitFOrFrameAndSwitchToIt(int frameIndex, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameAndSwitchToIt(String frameNameOrId, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
    }

    public void waitForFrameAndSwitchToIt(WebElement frameElement, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public String waitForurlContains(String urlFraction, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.urlContains(urlFraction));
        return driver.getCurrentUrl();
    }

    public String waitForAlertsAndAccept(int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        alert = wait.until(ExpectedConditions.alertIsPresent());
        assert alert != null;
        String alertText = alert.getText();
        System.out.println("Alert text is: " + alertText);
        alert.accept();
        return alertText;
    }

    public Boolean waitForNumberOfWindowsToBe(int numberOfWindows, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    /**
     * An expectation for checking that there is at least one element present on a web page.
     *
     * @param locator The locator used to find the elements
     * @param timeOut The maximum time to wait in seconds
     * @return A list of WebElements found
     */
    public List<WebElement> waitForPresenceOfElements(By locator, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * An expectation for checking that all elements are present on the DOM of a page and visible.
     * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
     *
     * @param elements The list of WebElements to wait for visibility
     * @param timeOut  The maximum time to wait in seconds
     * @return A list of visible WebElements
     */
    public List<WebElement> waitForVisibilityOfElements(By elements, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elements));
    }

    /**
     * An expectation for checking an element is visible and enabled such that you can click it.
     *
     * @param element The locator of the WebElement to wait for clickability
     * @param timeOut The maximum time to wait in seconds
     * @return The clickable WebElement
     */
    public WebElement waitForElementToBeClickable(By element, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * An expectation for checking an element is visible and enabled such that you can send text to it.
     *
     * @param locator              The locator of the WebElement to wait for
     * @param text                 The text to send to the WebElement
     * @param durationInSeconds    The maximum time to wait in seconds
     * @param pollingTimeInSeconds The polling interval in seconds
     */
    public void customPollingTimeAndSendKeys(By locator, String text, int durationInSeconds, int pollingTimeInSeconds) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(durationInSeconds));
        wait.pollingEvery(Duration.ofSeconds(pollingTimeInSeconds))
                .ignoring(NoSuchElementException.class)
                .withMessage("Element is not found within the given time: " + durationInSeconds + " seconds" + locator)
                .until(ExpectedConditions.visibilityOfElementLocated(locator))
                .sendKeys(text);
    }

    /**
     * An expectation for checking an element is visible and enabled such that you can click it.
     *
     * @param locator              The locator of the WebElement to wait for
     * @param durationInSeconds    The maximum time to wait in seconds
     * @param pollingTimeInSeconds The polling interval in seconds
     */
    public void waitForElementAndClick(By locator, int durationInSeconds, int pollingTimeInSeconds) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(durationInSeconds));
        wait.pollingEvery(Duration.ofSeconds(pollingTimeInSeconds))
                .ignoring(NoSuchElementException.class)
                .withMessage("Element is not found within the given time: " + durationInSeconds + " seconds" + locator)
                .until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }
//*** Fluent Wait Methods ***

    /**
     * Waits for the presence of an element using Fluent Wait.
     *
     * @param locator     The locator used to find the element
     * @param timeOut     The maximum time to wait in seconds
     * @param pollingTime The polling interval in seconds
     * @return The visible WebElement
     */
    public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Element is not found within the given time: " + timeOut + " seconds" + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(this.driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoAlertPresentException.class)
                .withMessage("Alert is not present within the given time: " + timeOut + " seconds");
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Custom wait implementation to find an element with retries.
     *
     * @param locator The locator used to find the element
     * @param timeout The maximum number of attempts to find the element
     * @return The found WebElement or null if not found
     * @throws InterruptedException if the thread is interrupted during sleep
     */
    public WebElement customWait(By locator, int timeout) throws InterruptedException {
        WebElement element = null;
        int attempts = 0;
        while (attempts < timeout) {
            try {
                element = driver.findElement(locator);
                System.out.println("Element is found on attempt: " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Element is not found in attempt: " + (attempts + 1));
                Thread.sleep(500);
            }
            attempts++;
        }
        if (element == null) {
            System.out.println("Element is not found on attempt: " + attempts);
        }
        return element;

    }

    /**
     * Custom wait implementation to find an element with retries and custom polling time.
     *
     * @param locator     The locator used to find the element
     * @param timeout     The maximum number of attempts to find the element
     * @param pollingTime The time to wait between attempts in milliseconds
     * @return The found WebElement or null if not found
     * @throws InterruptedException if the thread is interrupted during sleep
     */

    public WebElement customWait(By locator, int timeout, int pollingTime) throws InterruptedException {
        WebElement element = null;
        int attempts = 0;
        while (attempts < timeout) {
            try {
                element = driver.findElement(locator);
                System.out.println("Element is found on attempt: " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Element is not found in attempt: " + (attempts + 1));
                Thread.sleep(pollingTime);
            }
            attempts++;
        }
        if (element == null) {
            System.out.println("Element is not found on attempt: " + attempts + "with interval of " + pollingTime + " ms");
        }
        return element;
    }

    public Boolean isPageLoaded(int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(driver -> Objects.equals(((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"));
    }
}

