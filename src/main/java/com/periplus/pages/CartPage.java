package com.periplus.pages;

import com.periplus.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.ConfigManager;
import utilities.WaitUtility;

import java.util.List;

public class CartPage extends BasePage {
    private By cartItems = By.xpath("//div[contains(@class,'row-cart-product')]");
    private By emptyCartMessage = By.xpath("//div[@class='content' and contains(text(), 'Your shopping cart is empty')]");
    private By cartTotal = By.xpath("//li[contains(text(),'Total')]/span[@id='sub_total']");
    private By plusButton = By.xpath(".//button[@data-type='plus' and contains(@name, 'plus')]");
    private By minusButton = By.xpath(".//button[@data-type='minus' and contains(@name, 'minus')]");
    private By quantityInput = By.xpath(".//input[contains(@class, 'input-number')]");
    private By removeButton = By.xpath(".//a[contains(@class,'btn-cart-remove')]");
    private By productNameLink = By.xpath(".//a[contains(@href, '/p/')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasItems() {
        return !driver.findElements(cartItems).isEmpty();
    }

    public boolean isEmpty() {
        return !hasItems() || isElementPresent(emptyCartMessage);
    }

    public boolean isEmptyMessageDisplayed() {
        return isElementPresent(emptyCartMessage);
    }

    public List<WebElement> getAllCartItems() {
        return driver.findElements(cartItems);
    }

    public int getNumberOfProducts() {
        return getAllCartItems().size();
    }

    public double getCartTotal() {
        try {
            WaitUtility.waitForPreloaderInvisible(driver);
            WaitUtility.waitForElementVisible(driver, cartTotal);
            WebElement totalElement = find(cartTotal);
            String totalText = totalElement.getText();
            return Double.parseDouble(totalText.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            logger.warning("Could not get cart total: " + e.getMessage());
            return 0.0;
        }
    }

    public boolean containsProduct(String productId) {
        List<WebElement> items = getAllCartItems();
        for (WebElement item : items) {
            try {
                WebElement nameLink = findWithin(item, productNameLink);
                String href = nameLink.getAttribute("href");
                if (href != null && href.contains("/p/")) {
                    String itemProductId = href.replaceAll(".*/p/([0-9]+).*", "$1");
                    if (itemProductId.equals(productId)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }

    public int getProductQuantity(String productId) {
        List<WebElement> items = getAllCartItems();
        for (WebElement item : items) {
            try {
                WebElement nameLink = findWithin(item, productNameLink);
                String href = nameLink.getAttribute("href");
                if (href != null && href.contains("/p/")) {
                    String itemProductId = href.replaceAll(".*/p/([0-9]+).*", "$1");
                    if (itemProductId.equals(productId)) {
                        WebElement qtyInput = findWithin(item, quantityInput);
                        return Integer.parseInt(qtyInput.getAttribute("value"));
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        return 0;
    }

    public void increaseQuantity() {
        click(plusButton);
        WaitUtility.waitForPreloaderInvisible(driver);
        WaitUtility.waitForElementVisible(driver, cartTotal);
    }

    public void decreaseQuantity() {
        click(minusButton);
        WaitUtility.waitForPreloaderInvisible(driver);
        WaitUtility.waitForElementVisible(driver, cartTotal);
        if (isAlertPresent()) {
            acceptAlert();
        }
    }

    public void removeProduct(String productId) {
        List<WebElement> items = getAllCartItems();
        if (!items.isEmpty()) {
            try {
                clickWithin(items.get(0), removeButton);
                waitForElementVisible(cartTotal);
            } catch (Exception e) {
                logger.warning("Could not remove product: " + e.getMessage());
            }
        }
    }

    public void clearCart() {
        List<WebElement> items = getAllCartItems();
        for (WebElement item : items) {
            try {
                clickWithin(item, removeButton);
                waitForElementVisible(cartTotal);
            } catch (Exception e) {
                logger.warning("Could not remove item: " + e.getMessage());
            }
        }
    }

    public static CartPage navigateToCart(WebDriver driver) {
        String baseUrl = ConfigManager.getProperty("app.url", "https://www.periplus.com/");
        driver.navigate().to(baseUrl + "checkout/cart");
        WaitUtility.waitForPageReady(driver);
        return new CartPage(driver);
    }
}
