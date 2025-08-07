package com.automation.screens;

import com.automation.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;

public class DashboardScreen {

    public static final String SCREEN_NAME = "DashboardScreen";
    public WebDriver driver;
    public WebDriverWait wait;

    // Page Elements - Navigation
    private static final String NAVIGATION_TAB_XPATH = "//button[contains(@class,'nav-tab') and text()='@tab']";
    private static final String DASHBOARD_HEADER_XPATH = "//div[contains(@class,'tab-content')]//div[contains(@class, 'analytics')]//h2";
    private static final String TAB_CONTENT_HEADER_XPATH = "//div[contains(@class,'tab-content')]//div[contains(@class, '@tab')]//h2";

    public DashboardScreen() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isCurrentTabPresent(String tab) {
        String xpath = NAVIGATION_TAB_XPATH.replace("@tab", tab);
        return driver.findElement(By.xpath(xpath)).isDisplayed();
    }


    public DashboardScreen userClicksOnNavigationTab(String tab) {
        String xpath = NAVIGATION_TAB_XPATH.replace("@tab", tab);
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }

    public boolean userVerifyActiveState(String tab) {
        String xpath = NAVIGATION_TAB_XPATH.replace("@tab", tab);
        WebElement currentTab = driver.findElement(By.xpath(xpath));
        return currentTab.getAttribute("class").contains("active");
    }

    public boolean userVerifyContentChanges(String tab) {
        String actualXpath = tab.equals("Dashboard") ? DASHBOARD_HEADER_XPATH : TAB_CONTENT_HEADER_XPATH;
        String xpath = actualXpath.replace("@tab", tab.toLowerCase());
        WebElement currentTab = driver.findElement(By.xpath(xpath));
        return currentTab.isDisplayed() && currentTab.getText().contains(tab);
    }

    public boolean isBackendStatusSectionDisplayed() {
        return driver.findElement(By.xpath("//h2[text()='Backend Status']//parent::div[@class='status-section']")).isDisplayed();
    }

    public boolean isBackendStatusDisplayed() {
        boolean isBackendStatusCodeDisplayed = driver.findElement(By.xpath("//div[@class='status-section']//p[1]")).isDisplayed();
        return isBackendStatusCodeDisplayed;
    }

    public boolean isHealthStatusOK() {
        return driver.findElement(By.cssSelector("span.status-ok")).isDisplayed();
    }

    public String getBackendStatusSectionHeading() {
        return driver.findElement(By.xpath("//h2[text()='Backend Status']//parent::div[@class='status-section']//p[@class='message']")).getText();
    }

    public boolean isUptimeStatusDisplayed() {
        boolean isUptimeMessageDisplayed = driver.findElement(By.xpath("//div[@class='status-section']//p[2]")).isDisplayed();
        return isUptimeMessageDisplayed;
    }

    public boolean isUptimeDisplayedInSeconds() {
        String uptimeText = driver.findElement(By.xpath("//div[@class='status-section']//p[2]")).getText();
        return uptimeText.contains("seconds");
    }

    public DashboardScreen scrollToAnalyticsDashboard() {
        System.out.println("Step: Scrolling to Analytics Dashboard section");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.analytics-section")));
        WebElement analyticsSection = driver.findElement(By.cssSelector("div.analytics-section"));
        js.executeScript("arguments[0].scrollIntoView(true);", analyticsSection);
        return this;
    }

    public int userFetchesTotalCountInUsers() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Users')]//parent::div//p[1]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesAdminCountInUsers() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Users')]//parent::div//p[2]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesRegularCountInUsers() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Users')]//parent::div//p[3]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesTotalCountInProducts() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Products')]//parent::div//p[1]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesCategoriesCountInProducts() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Products')]//parent::div//p[2]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesLowStockCountInProducts() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Products')]//parent::div//p[3]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesTotalCountInTasks() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Tasks')]//parent::div//p[1]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesCompletedCountInTasks() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Tasks')]//parent::div//p[2]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesHighPriorityCountInTasks() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Tasks')]//parent::div//p[3]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesTotalCountInOrders() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Orders')]//parent::div//p[1]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesPendingCountInOrders() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Orders')]//parent::div//p[2]")).getText();
        String[] items = count.split(": ");
        return Integer.parseInt(items[1]);
    }

    public int userFetchesRevenueInOrders() {
        String count = driver.findElement(By.xpath("//h3[contains(text(),'Orders')]//parent::div//p[3]")).getText();
        String[] items = count.split(": ");
        double revenue = Double.parseDouble(items[1].substring(1)); // Remove currency symbol if present

        return (int) revenue;
    }

//
//    /**
//     * Verify Tasks Analytics are displayed
//     */
//    public DashboardScreen verifyTasksAnalytics() {
//        System.out.println("Step: Verifying Tasks analytics display");
//
//        try {
//            List<WebElement> taskElements = driver.findElements(
//                By.xpath("//*[contains(text(),'Task') or contains(text(),'task') or contains(text(),'completed') or contains(text(),'pending') or contains(text(),'priority')]"));
//
//            Assert.assertFalse(taskElements.isEmpty(), "Should find tasks analytics elements");
//            System.out.println("Tasks analytics verification completed");
//
//        } catch (Exception e) {
//            System.out.println("Tasks analytics verification failed, but continuing test");
//        }
//
//        return this;
//    }
//
//    /**
//     * Verify Orders Analytics are displayed (if orders exist)
//     */
//    public DashboardScreen verifyOrdersAnalytics() {
//        System.out.println("Step: Verifying Orders analytics display");
//
//        try {
//            List<WebElement> orderElements = driver.findElements(
//                By.xpath("//*[contains(text(),'Order') or contains(text(),'order')]"));
//
//            if (!orderElements.isEmpty()) {
//                System.out.println("Orders analytics found and verified");
//            } else {
//                System.out.println("No orders analytics found - this is acceptable if no orders exist");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Orders analytics verification - no orders may exist yet");
//        }
//
//        return this;
//    }
//
//    /**
//     * Verify that content changes when switching tabs
//     */
//    public DashboardScreen verifyContentChanges() {
//        System.out.println("Step: Verifying content changes when switching tabs");
//
//        // Get initial page source or specific content element
//        String initialContent = driver.findElement(By.tagName("main")).getText();
//
//        // Click on a different tab
//        try {
//            WebElement differentTab = driver.findElement(
//                By.xpath("//nav//button[not(contains(@class,'active'))] | //nav//a[not(contains(@class,'active'))]"));
//            differentTab.click();
//
//            Thread.sleep(1000); // Wait for content to change
//
//            // Get new content
//            String newContent = driver.findElement(By.tagName("main")).getText();
//
//            Assert.assertNotEquals(initialContent, newContent, "Content should change when switching tabs");
//            System.out.println("Content change verification passed!");
//
//        } catch (Exception e) {
//            System.out.println("Content change verification - using alternative method");
//            // Alternative verification could check URL change or specific element changes
//        }
//
//        return this;
//    }

}
