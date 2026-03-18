package com.periplus.pages;

import com.periplus.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utilities.WaitUtility;

import java.util.List;

public class ProductsPage extends BasePage {
    private By productItems = By.xpath("//div[contains(@class,'single-product')]//a");
    private By unavailableProducts = By.xpath("//div[contains(@class, 'currently-unavailable')]");
    private By availabilityDropdown = By.id("availability");
    private By filterButton = By.xpath("//button[contains(@class, 'btn-filter')]");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllProducts() {
        return driver.findElements(productItems);
    }

    public boolean hasProducts() {
        return !getAllProducts().isEmpty();
    }

    public ProductPage selectProductByIndex(int index) {
        WaitUtility.waitForPreloaderInvisible(driver);

        List<WebElement> products = getAllProducts();
        if (index >= 0 && index < products.size()) {
            WaitUtility.waitAndClick(driver, productItems);
            return new ProductPage(driver);
        }
        throw new IndexOutOfBoundsException("Product index " + index + " not found");
    }

    public ProductPage selectFirstProduct() {
        return selectProductByIndex(0);
    }

    public List<WebElement> getUnavailableProducts() {
        List<WebElement> products = driver.findElements(unavailableProducts);
        return products;
    }

    public ProductsPage filterByAvailability(String availabilityValue) {
        click(availabilityDropdown);

        WebElement dropdownElement = find(availabilityDropdown);
        Select select = new Select(dropdownElement);
        select.selectByValue(availabilityValue);

        click(filterButton);

        WaitUtility.waitForPreloaderInvisible(driver);

        return this;
    }
}
