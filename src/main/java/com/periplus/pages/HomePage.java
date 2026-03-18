package com.periplus.pages;

import com.periplus.base.BasePage;
import utilities.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By loginButton = By.id("nav-signin-text");
    private By searchField = By.id("filter_name_desktop");
    private By logoutButton = By.xpath("//div[@class='shopping-item']//a[text()='Logout']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage goToLoginPage() {
        click(loginButton);
        return new LoginPage(driver);
    }

    public ProductsPage searchForProduct(String searchTerm) {
        set(searchField, searchTerm);
        find(searchField).sendKeys(Keys.ENTER);
        return new ProductsPage(driver);
    }

    public HomePage navigateToHome() {
        String baseUrl = ConfigManager.getProperty("app.url", "https://www.periplus.com/");
        driver.navigate().to(baseUrl);
        return this;
    }

    public boolean isLoggedIn() {
        try {
            return isElementPresent(logoutButton);
        } catch (Exception e) {
            return false;
        }
    }

    public CartPage goToCart() {
        String baseUrl = ConfigManager.getProperty("app.url", "https://www.periplus.com/");
        driver.navigate().to(baseUrl + "checkout/cart");
        return new CartPage(driver);
    }
}
