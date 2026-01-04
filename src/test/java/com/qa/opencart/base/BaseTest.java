package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utils.ElementsUtil;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class BaseTest {
    WebDriver driver;
    protected Properties prop;
    protected LoginPage loginPage;
    protected AccountsPage accountsPage;
    protected SearchResultsPage searchResultsPage;
    protected ProductInfoPage productInfoPage;
    DriverFactory df;
    ElementsUtil elementsUtil;
    protected SoftAssert softAssert;
    protected RegisterPage regPage;

    @BeforeTest
    public void setup() throws InterruptedException, FileNotFoundException {
        // Base test setup code
        df = new DriverFactory();
        prop = df.initProp();
        driver = df.initDriver(prop);
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();

    }

    @AfterTest
    public void tearDown() {
        // Base test teardown code
        if (driver != null) {
            driver.quit();
        }
    }


    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
        }
        driver.quit();
    }

}
