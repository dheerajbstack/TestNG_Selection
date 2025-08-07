package com.automation.screens;

import com.automation.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BackgroundScreen {

    public WebDriver driver;
    public WebDriverWait wait;


    public BackgroundScreen() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public BackgroundScreen openApplication(String url) {
        System.out.println("Step: Opening application at " + url);
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        System.out.println("Application opened successfully");
        return this;
    }
}
