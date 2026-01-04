package Wait;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class WaitSession {
    private WebDriverWait wait;
    private final WebDriver driver;
    private Alert alert;

    public WaitSession(WebDriver driver) {
        this.driver = driver;
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
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
     *
     * @param element The WebElement to wait for visibility
     * @param timeOut The maximum time to wait in seconds
     * @return The visible WebElement
     */
    public WebElement waitForVisibilityOfElement(WebElement element, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOf(element));
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

    private Boolean waitForurlContains(String urlFraction, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.urlContains(urlFraction));
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
    public List<WebElement> waitForVisibilityOfElements(List<WebElement> elements, int timeOut) {
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
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
