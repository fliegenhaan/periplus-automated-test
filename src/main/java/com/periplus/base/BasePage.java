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
    private static final int DEFAULT_TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
        waitForPageReady();
    }

    private void waitForPageReady() {
        WaitUtility.waitForPreloaderInvisible(driver);
    }

    protected WebElement find(By locator) {
        waitForPageReady();
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void set(By locator, String text) {
        waitForPageReady();
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {
        waitForPageReady();
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }

    protected void hover(By locator) {
        waitForPageReady();
        WebElement element = find(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    protected void waitForElementVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected WebElement findWithin(WebElement parent, By childLocator) {
        waitForPageReady();
        WebElement child = parent.findElement(childLocator);
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(child));
    }

    protected void clickWithin(WebElement parent, By childLocator) {
        waitForPageReady();
        WebElement child = parent.findElement(childLocator);
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(child))
                .click();
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
