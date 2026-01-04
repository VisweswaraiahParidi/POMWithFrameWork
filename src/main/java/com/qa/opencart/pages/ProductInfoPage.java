package com.qa.opencart.pages;

import com.qa.opencart.constans.AppConstans;
import com.qa.opencart.utils.ElementsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoPage {
    private WebDriver driver;
    private ElementsUtil elementsUtil;

    Map<String, String> productMap;

    private final By productHeader = By.cssSelector("div#content h1");
    private final By productImages = By.cssSelector("ul.thumbnails img");
    private final By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
    private final By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
    private final By quantity = By.id("input-quantity");
    private final By addToCartBtn = By.id("button-cart");

    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elementsUtil = new ElementsUtil(this.driver);
    }

    public String getProductHeaderText() {
        return elementsUtil.waitForVisibilityOfElement(productHeader, AppConstans.SHORT_TIME_OUT).getText();
    }

    public int getProductImagesCount() {
        int actualImagesCount = elementsUtil.waitForVisibilityOfElements(productImages, AppConstans.DEFAULT_TIME_OUT).size();
        System.out.println("Total product images: " + getProductHeaderText() + " : " + actualImagesCount);
        return actualImagesCount;
    }

    private void getProductMetaData() {
        List<WebElement> metaList = elementsUtil.waitForVisibilityOfElements(productMetaData, AppConstans.SHORT_TIME_OUT);
        for (WebElement e : metaList) {
            String metaText = e.getText();
            String key = metaText.split(":")[0].trim();
            String value = metaText.split(":")[1].trim();
            productMap.put(key, value);
        }
    }

    private void getProductPriceData() {
        List<WebElement> priceList = elementsUtil.waitForVisibilityOfElements(productPriceData, AppConstans.SHORT_TIME_OUT);
        String actPrice = priceList.get(0).getText().trim();
        String exTax = priceList.get(1).getText().split(":")[0].trim();
        String exTaxValue = priceList.get(1).getText().split(":")[1].trim();

        productMap.put("price", actPrice);
        productMap.put(exTax, exTaxValue);

    }

    public Map<String, String> getProductData() {
        productMap = new HashMap<String, String>();
        productMap.put("productHeader", getProductHeaderText());
        productMap.put("productImageCount", String.valueOf(getProductImagesCount()));

        getProductMetaData();
        getProductPriceData();

        return productMap;
    }

}
