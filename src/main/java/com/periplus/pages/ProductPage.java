package com.periplus.pages;

import com.periplus.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.WaitUtility;

public class ProductPage extends BasePage {
    private By productTitle = By.tagName("h2");
    private By productPrice = By.xpath("//div[@class='quickview-price']//span");
    private By quantityInput = By.xpath("//input[contains(@class, 'input-number')]");
    private By plusButton = By.xpath("//button[@data-type='plus']");
    private By minusButton = By.xpath("//button[@data-type='minus']");
    private By addToCartButton = By.xpath("//button[contains(@class, 'btn-add-to-cart')]");
    // private By cartIcon = By.id("show-your-cart");
    private By modalCloseButton = By.xpath("//i[@class='ti-close']");
    private By unavailableModal = By.id("Notification-Modal");
    private By unavailableModalText = By.xpath("//div[contains(@class, 'modal-text')]");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return find(productTitle).getText();
    }

    public double getProductPrice() {
        WebElement priceElement = find(productPrice);
        String priceAttr = priceElement.getAttribute("content");
        String priceText = (priceAttr != null && !priceAttr.isEmpty()) ? priceAttr : priceElement.getText();
        return Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
    }

    public int getQuantity() {
        WebElement qtyInput = find(quantityInput);
        String value = qtyInput.getAttribute("value");
        return Integer.parseInt(value != null && !value.isEmpty() ? value : "1");
    }

    public void increaseQuantity() {
        click(plusButton);
    }

    public void decreaseQuantity() {
        click(minusButton);
    }

    public void adjustQuantity(int targetQuantity) {
        int currentQty = getQuantity();
        int diff = targetQuantity - currentQty;

        if (diff > 0) {
            for (int i = 0; i < diff; i++) {
                increaseQuantity();
            }
        } else if (diff < 0) {
            for (int i = 0; i < Math.abs(diff); i++) {
                decreaseQuantity();
            }
        }
    }

    public void addToCart() {
        click(addToCartButton);
        WaitUtility.waitForPreloaderInvisible(driver);
    }

    public void closeModal() {
        try {
            waitForElementVisible(modalCloseButton);
            click(modalCloseButton);
        } catch (Exception e) {
            // modal may not exist
        }
    }

    public CartPage goToCart() {
        String currentUrl = driver.getCurrentUrl();
        String baseUrl = currentUrl.contains("/") ? currentUrl.substring(0, currentUrl.indexOf("/", 8)) : "https://www.periplus.com/";
        driver.navigate().to(baseUrl + "/checkout/cart");
        WaitUtility.waitForPageReady(driver);

        return new CartPage(driver);
    }

    public boolean isUnavailableModalDisplayed() {
        try {
            WebElement modal = driver.findElement(unavailableModal);
            String style = modal.getAttribute("style");
            return style != null && !style.contains("display: none");
        } catch (Exception e) {
            return false;
        }
    }

    public String getUnavailableModalText() {
        try {
            return find(unavailableModalText).getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getProductId() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.replaceAll(".*/p/([0-9]+).*", "$1");
    }
}
