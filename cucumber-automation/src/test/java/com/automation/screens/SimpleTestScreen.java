package com.automation.screens;

import com.automation.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class SimpleTestScreen {

    public static final String SCREEN_NAME = "SimpleTestScreen";
    public WebDriver driver;

    public SimpleTestScreen() {
        this.driver = DriverManager.getDriver();
    }

    public SimpleTestScreen openApplication() {
        System.out.println("Step: Opening web application");
        driver.get("http://localhost:3000/"); // Replace with actual URL
        System.out.println("Web application opened successfully");
        return this;
    }

    public String checkPageTitle() {
        String pageTitle = driver.getTitle();
        return pageTitle;
    }

    public SimpleTestScreen validatePageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        System.out.println("Step: Verifying title - Expected: " + expectedTitle + ", Actual: " + actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle, "Page title should match expected value");
        System.out.println("Test passed!");
        return this;
    }
}