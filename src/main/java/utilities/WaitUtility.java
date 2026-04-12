package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import org.openqa.selenium.TimeoutException;
import java.util.logging.Logger;

public class WaitUtility {

    private static final Logger logger = Logger.getLogger(WaitUtility.class.getName());
    public static final int DEFAULT_TIMEOUT = 15;
    private static final int POLL_INTERVAL = 500;
    private static final By PRELOADER_LOCATOR = By.xpath("//div[@class='preloader']");

    private WaitUtility() {

    }

    public static void waitForPreloaderInvisible(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT), Duration.ofMillis(POLL_INTERVAL));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(PRELOADER_LOCATOR));
        } catch (TimeoutException e) {
            // preloader may not exist or already gone
        } catch (Exception e) {
            logger.warning("Unexpected error waiting for preloader: " + e.getMessage());
        }
    }

    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT), Duration.ofMillis(POLL_INTERVAL));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitAndClick(WebDriver driver, By locator) {
        waitForPreloaderInvisible(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT), Duration.ofMillis(POLL_INTERVAL));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public static void waitForPageReady(WebDriver driver) {
        waitForPreloaderInvisible(driver);
    }
}
