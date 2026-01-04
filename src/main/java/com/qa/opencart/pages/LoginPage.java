package com.qa.opencart.pages;

import com.qa.opencart.constans.AppConstans;
import com.qa.opencart.listeners.ExtentLogger;
import com.qa.opencart.utils.ElementsUtil;
import org.openqa.selenium.*;

public class LoginPage {
    private WebDriver driver;
    private ElementsUtil elementsUtil;

    private By emailId = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwdLink = By.linkText("Forgotten Password111");
    private By registerLink = By.linkText("Register");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementsUtil = new ElementsUtil(this.driver);
    }

    public String getLoginPageTitle() {
        ExtentLogger.info("Getting login page title...");
        String title = elementsUtil.waitFOrTitleIs(AppConstans.LOGIN_PAGE_TITLE, AppConstans.DEFAULT_TIME_OUT);
        System.out.println("Login Page Title: " + driver.getTitle());
        ExtentLogger.passWithScreenshot("Login page title is: " + title, driver, "getLoginPageTitle");
        return title;
    }

    public String getLoginPageURL() {
        String url = elementsUtil.waitForurlContains(AppConstans.LOGIN_PAGE_URL_FRACTION, AppConstans.SHORT_TIME_OUT);
        System.out.println("Login Page URL: " + driver.getCurrentUrl());
        return url;
    }

    public boolean isForgotPwdLinkExist() {
        ExtentLogger.info("Checking if Forgot Password link exists...");
        return elementsUtil.waitForVisibilityOfElement(forgotPwdLink, AppConstans.DEFAULT_TIME_OUT).isDisplayed();
    }

    public AccountsPage doLogin(String username, String pwd) {
        ExtentLogger.info("Login process started with username: " + username + " and password: " + pwd);
        System.out.println("Login with: " + username + " : " + pwd);
        elementsUtil.waitForVisibilityOfElement(emailId, AppConstans.SHORT_TIME_OUT).sendKeys(username);
        elementsUtil.doSendKeys(password, pwd);
        elementsUtil.doClick(loginBtn);
        elementsUtil.waitFOrTitleIs(AppConstans.ACCOUNTS_PAGE_TITLE, AppConstans.SHORT_TIME_OUT);
        ExtentLogger.passWithScreenshot("Login successful for user: " + username, driver, "doLogin");
        return new AccountsPage(driver);
    }

    public RegisterPage navigateToRegisterPage() {
        elementsUtil.waitForVisibilityOfElement(registerLink, AppConstans.SHORT_TIME_OUT).click();
        return new RegisterPage(driver);
    }



}
