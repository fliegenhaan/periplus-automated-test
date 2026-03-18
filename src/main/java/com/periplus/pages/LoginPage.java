package com.periplus.pages;

import com.periplus.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By emailField = By.name("email");
    private By passwordField = By.name("password");
    private By loginButton = By.id("button-login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setEmail(String email) {
        set(emailField, email);
    }

    public void setPassword(String password) {
        set(passwordField, password);
    }

    public void clickLoginButton() {
        find(loginButton).click();
    }

    public void login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }
}
