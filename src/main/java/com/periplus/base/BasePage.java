package com.periplus.base;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;
import utilities.WaitUtility;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WaitUtility.DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
        WaitUtility.waitForPageReady(driver);
    }

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void set(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {
        WaitUtility.waitAndClick(driver, locator);
    }

    protected void hover(By locator) {
        WebElement element = find(locator);
        new Actions(driver).moveToElement(element).perform();
    }

    protected void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected WebElement findWithin(WebElement parent, By childLocator) {
        WebElement child = parent.findElement(childLocator);
        return wait.until(ExpectedConditions.visibilityOf(child));
    }

    protected void clickWithin(WebElement parent, By childLocator) {
        WebElement child = parent.findElement(childLocator);
        wait.until(ExpectedConditions.elementToBeClickable(child)).click();
    }

    protected void setWithin(WebElement parent, By childLocator, String text) {
        WebElement element = findWithin(parent, childLocator);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void acceptAlert() {
        try {
            Alert alert = driver.switchTo().alert();
            logger.info("Alert detected: " + alert.getText());
            alert.accept();
        } catch (Exception e) {
        }
    }
}
