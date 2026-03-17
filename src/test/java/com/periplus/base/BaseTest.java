package com.periplus.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.DriverUtility;

import java.time.Duration;

public class BaseTest {
    private WebDriver driver;
    protected BasePage basePage;
    protected String url = "https://www.periplus.com/";

    @BeforeClass
    public void setUp() {
        driver = DriverUtility.createDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @BeforeMethod
    public void loadApplication() {
        driver.get(url);
        basePage = new BasePage(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
