package com.qa.opencart.Tests;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class ProductInfoTest extends BaseTest {

    @BeforeClass
    public void productInfoSetup() {
        accountsPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @DataProvider
    public Object[][] productTestData() {
        return new Object[][]{
                {"macbook", "MacBook Pro"},
                {"macbook", "MacBook Air"},
                {"iMac", "iMac"},
                {"samsung", "Samsung SyncMaster 941BW"},
                {"samsung", "Samsung Galaxy Tab 10.1"}

        };
    }

    @Test(dataProvider = "productTestData")
    public void productHeaderTest(String searchKey, String productName) {
        searchResultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = searchResultsPage.clickOnProductToSelect(productName);
        String header = productInfoPage.getProductHeaderText();
        System.out.println("Product Header: " + header);
        Assert.assertEquals(header, productName);
    }

    @DataProvider
    public Object[][] productData() {
        return new Object[][]{
                {"macbook", "MacBook Pro", 4},
                {"macbook", "MacBook Air", 4},
                {"iMac", "iMac", 3},
                {"samsung", "Samsung SyncMaster 941BW", 1},
                {"samsung", "Samsung Galaxy Tab 10.1", 7}

        };
    }

    @Test(dataProvider = "productData")
    public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
        searchResultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = searchResultsPage.clickOnProductToSelect(productName);
        int imageCount = productInfoPage.getProductImagesCount();
        System.out.println("Product Images Count: " + imageCount);
        Assert.assertEquals(imagesCount, imagesCount);
    }

    @Test
    public void productInfoTest() {
        searchResultsPage = accountsPage.doSearch("macbook");
        productInfoPage = searchResultsPage.clickOnProductToSelect("MacBook Pro");
        Map<String, String> productActualData = productInfoPage.getProductData();
        System.out.println(productActualData);

        softAssert.assertEquals(productActualData.get("Brand"), "Apple");
        softAssert.assertEquals(productActualData.get("Availability"), "Out Of Stock");
        softAssert.assertEquals(productActualData.get("productHeader"), "MacBook Pro");
        softAssert.assertEquals(productActualData.get("price"), "$2,000.00");
        softAssert.assertEquals(productActualData.get("Reward Points"), "800");
        softAssert.assertAll();
    }
}
