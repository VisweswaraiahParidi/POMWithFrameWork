package com.qa.opencart.pages;

import com.qa.opencart.constans.AppConstans;
import com.qa.opencart.utils.ElementsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {

    private final WebDriver driver;
    private final ElementsUtil elementsUtil;

    private By logoutLink = By.linkText("Logout");
    private By searchBox = By.name("search");
    private By searchButton = By.cssSelector("div#search button");
    private By accountSections = By.cssSelector("div#content h2");


    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        elementsUtil = new ElementsUtil(this.driver);
    }

    public String getAccountsPageTitle() {
        return elementsUtil.waitFOrTitleIs(AppConstans.ACCOUNTS_PAGE_TITLE, AppConstans.DEFAULT_TIME_OUT);
    }

    public Boolean logout() {
        return elementsUtil.waitForVisibilityOfElement(logoutLink, AppConstans.DEFAULT_TIME_OUT).isDisplayed();
    }

    public List<String> accountsPageHeader() {
        List<String> headerList = new ArrayList<>();
        List<WebElement> elementList = elementsUtil.waitForVisibilityOfElements(accountSections, AppConstans.DEFAULT_TIME_OUT);
        for (WebElement e : elementList) {
            String text = e.getText();
            headerList.add(text);
        }
        return headerList;
    }

    public int headersCount() {
        return elementsUtil.waitForVisibilityOfElements(accountSections, AppConstans.DEFAULT_TIME_OUT).size();
    }

    public SearchResultsPage doSearch(String productName) {
        System.out.println("Searching for product: " + productName);
        elementsUtil.waitForVisibilityOfElement(searchBox, AppConstans.DEFAULT_TIME_OUT).clear();
        elementsUtil.doSendKeys(searchBox, productName);
        elementsUtil.doClick(searchButton);
        return new SearchResultsPage(driver);
    }


}
