package com.qa.opencart.pages;

import com.qa.opencart.constans.AppConstans;
import com.qa.opencart.utils.ElementsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage {

    WebDriver driver;
    ElementsUtil elementsUtil;

    private By searchProductResults = By.cssSelector("div.product-layout div.product-thumb");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        elementsUtil = new ElementsUtil(this.driver);
    }

    public void getSearchResultsPageTitle() {
        String title = elementsUtil.waitFOrTitleIs("Search -", 10);
        System.out.println("Search Results Page Title: " + title);
    }

    public int getSearchResultsCount() {
        return elementsUtil.waitForVisibilityOfElements(searchProductResults, AppConstans.DEFAULT_TIME_OUT).size();
    }

    public ProductInfoPage clickOnProductToSelect(String productName) {
        By productLink = By.linkText(productName);
        elementsUtil.waitForVisibilityOfElement(productLink, AppConstans.DEFAULT_TIME_OUT).click();
        return new ProductInfoPage(driver);
    }

}
