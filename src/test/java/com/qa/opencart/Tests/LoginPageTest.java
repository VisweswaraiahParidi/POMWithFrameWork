package com.qa.opencart.Tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constans.AppConstans;
import com.qa.opencart.listeners.ExtentLogger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 1)
    public void loginPageTitleTest() {
        String title = loginPage.getLoginPageTitle();
        System.out.println("Login Page Title: " + title);
        Assert.assertEquals(title, AppConstans.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void loginPageURLTest() {
        String url = loginPage.getLoginPageURL();
        System.out.println("Login Page URL: " + url);
        Assert.assertTrue(url.contains(AppConstans.LOGIN_PAGE_URL_FRACTION));
    }

    @Test(priority = 3)
    public void forgotPwdLinkExistTest() {
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Test(priority = 4)
    public void loginTest() {
        ExtentLogger.info("Login with username: " + prop.getProperty("username") + " and password: " + prop.getProperty("password"));
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        System.out.println("Account Page Title after login: " + accountsPage.getAccountsPageTitle());
        Assert.assertEquals(accountsPage.getAccountsPageTitle(), AppConstans.ACCOUNTS_PAGE_TITLE);
        // example assertion
        try {
            // perform verification
            ExtentLogger.pass("Login verification passed");
        } catch (AssertionError e) {
            ExtentLogger.failWithScreenshot("Login verification failed", e);
            throw e; // rethrow so TestNG marks test failed
        }
    }

}
