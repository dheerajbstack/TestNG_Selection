package com.automation.businessLayer;

import org.testng.Assert;
import com.automation.screens.SimpleTestScreen;

public class SimpleTestBL {

    private SimpleTestScreen screen;
    
    public SimpleTestBL() {
        this.screen = new SimpleTestScreen();
    }

    public void openApplication() {
        screen.openApplication();
        System.out.println("Step: Opening web application");
    }

    public void checkPageTitle() {
        System.out.println("Step: Checking page title");
        String pageTitle = screen.checkPageTitle();
        System.out.println("Current page title: " + pageTitle);
    }

    public void validatePageTitle(String expectedTitle) {
        System.out.println("Step: Verifying title: " + expectedTitle);
        String actualTitle = screen.checkPageTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title should match");
        System.out.println("Test passed! Title matches expected value.");
    }
}