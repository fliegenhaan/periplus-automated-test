package com.periplus.base;

import com.periplus.pages.CartPage;
import com.periplus.pages.HomePage;
import com.periplus.pages.LoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilities.ConfigManager;
import utilities.DriverUtility;

import java.time.Duration;
import java.util.logging.Logger;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;
    protected Logger logger = Logger.getLogger(this.getClass().getName());

    protected String baseUrl = ConfigManager.getProperty("app.url", "https://www.periplus.com/");
    protected String testEmail = ConfigManager.getProperty("test.email");
    protected String testPassword = ConfigManager.getProperty("test.password");

    private By closeIcon = By.xpath("//i[@class='ti-close']");

    @BeforeClass
    public void setUp() {
        driver = DriverUtility.createDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        logger.info("WebDriver initialized successfully");
    }

    @BeforeMethod
    public void loadApplication() {
        driver.get(baseUrl);
        homePage = new HomePage(driver);
        logger.info("Navigated to: " + baseUrl);
    }

    @BeforeMethod(dependsOnMethods = "loadApplication")
    public void ensureLoggedIn() {
        closeModalIfPresent();

        if (homePage.isLoggedIn()) {
            logger.info("User already logged in, skipping login");
            return;
        }

        LoginPage loginPage = homePage.goToLoginPage();
        loginPage.login(testEmail, testPassword);
        logger.info("Login completed");
    }

    private void closeModalIfPresent() {
        try {
            driver.findElement(closeIcon).click();
        } catch (Exception e) {
        }
    }

    @AfterMethod
    public void cleanupAfterTest() {
        try {
            driver.get(baseUrl + "checkout/cart");
            CartPage cartPage = new CartPage(driver);

            if (!cartPage.hasItems()) {
                logger.info("Cart already empty, no cleanup needed");
                return;
            }

            int maxAttempts = 3;
            int attempts = 0;

            while (cartPage.hasItems() && attempts < maxAttempts) {
                cartPage.clearCart();
                driver.navigate().refresh();
                cartPage = new CartPage(driver);
                attempts++;
            }

            if (cartPage.hasItems()) {
                logger.warning("Cart cleanup may not be complete after " + maxAttempts + " attempts");
            } else {
                logger.info("Cart cleared successfully after test");
            }
        } catch (Exception e) {
            logger.warning("Cleanup issue: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed successfully");
        }
    }
}
