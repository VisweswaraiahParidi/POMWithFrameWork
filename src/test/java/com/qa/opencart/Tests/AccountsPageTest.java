package com.qa.opencart.Tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constans.AppConstans;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class AccountsPageTest extends BaseTest {


    @BeforeClass
    public void accountsPageSetup() {
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test
    public void accountPageTest() {
        String title = accountsPage.getAccountsPageTitle();
        System.out.println("Accounts Page Title: " + title);
        Assert.assertEquals(title, AppConstans.ACCOUNTS_PAGE_TITLE);
    }

    @Test
    public void logoutLinkTest() {
        Assert.assertTrue(accountsPage.logout());
    }

    @Test
    public void accountSectionTest() {
        Assert.assertEquals(accountsPage.headersCount(), AppConstans.ACCOUNTS_PAGE_HEADER_COUNT);
        Assert.assertEquals(accountsPage.accountsPageHeader(), AppConstans.ACCOUNTS_PAGE_HEADERS);
    }

    @DataProvider
    public Object[][] productData() {
        return new Object[][]{
                {"MacBook Pro",1},
                {"iMac",1},
                {"Apple",1},
                {"Samsung",2}
        };
    }
    @Test(dataProvider= "productData")
    public void searchTest(String productName, int expResultsCount) {
       searchResultsPage =  accountsPage.doSearch(productName);
      int actResultsCount = searchResultsPage.getSearchResultsCount();
      Assert.assertEquals(actResultsCount, expResultsCount);
    }


}
