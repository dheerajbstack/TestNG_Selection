package com.automation.businessLayer;

import com.automation.screens.BackgroundScreen;
import com.automation.utils.LogCapture;
import org.apache.hc.core5.util.Asserts;
import org.testng.Assert;
import com.automation.screens.DashboardScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardBL {

    private DashboardScreen dashboardScreen;

    public static final List<String> NAVIGATION_TAB_LIST = Arrays.asList("Dashboard", "Users", "Products", "Tasks", "Orders", "Search", "Files & Themes");

    public DashboardBL() {
        this.dashboardScreen = new DashboardScreen();
    }

    public DashboardBL validatePageTitle(String expectedTitle) {
        String actualTitle = dashboardScreen.getPageTitle();
        String assertionMessage = String.format("Expected page title '%s' but found '%s'", expectedTitle, actualTitle);

        Assert.assertEquals(actualTitle, expectedTitle, assertionMessage);
        return this;
    }

    public void validateNavigationTabs(String expectedTabsString) {
        String[] tabsList = expectedTabsString.split(", ");
        for (String tab : tabsList) {
            boolean isCurrentTabPresent = dashboardScreen.isCurrentTabPresent(tab);
            Asserts.check(isCurrentTabPresent, String.format("Current tab %s is not displayed", tab));
        }
    }

    public DashboardBL validateContentAndActiveStateChanges() {
        for (String tab : NAVIGATION_TAB_LIST) {
            this.navigateToDashboardTab(tab);

            if (tab.equals("Files & Themes")) {
                tab = "Theme";
            }

            boolean isContentChangeForCurrentTab = dashboardScreen.userVerifyContentChanges(tab);
            Asserts.check(isContentChangeForCurrentTab, String.format("Content changes is not displayed for %s", tab));
        }
        LogCapture.addStepLog("Content Change Validation", "✅ Content changes verified when switching tabs");
        return this;
    }

    public void navigateToDashboardTab(String tab) {
        boolean isActiveStateDisplayed = dashboardScreen
                .userClicksOnNavigationTab(tab)
                .userVerifyActiveState(tab);

        Asserts.check(isActiveStateDisplayed, String.format("Active State is not displayed for %s", tab));
    }


    public DashboardBL viewBackendStatusSection() {
        boolean isBackendStatusSectionDisplayed = dashboardScreen.isBackendStatusSectionDisplayed();
        Asserts.check(isBackendStatusSectionDisplayed, "Backend status section is not displayed");

        String sectionHeadingText = dashboardScreen.getBackendStatusSectionHeading();
        Assert.assertEquals(sectionHeadingText,"Welcome to the Enhanced Full-Stack API!", "Assertion failure message");
        return this;
    }

    public DashboardBL verifyHealthStatusOK() {
        boolean isBackendStatusDisplayed = dashboardScreen.isBackendStatusDisplayed();
        Asserts.check(isBackendStatusDisplayed, "Backend status is not displayed");

        boolean isHealthStatusOK = dashboardScreen.isHealthStatusOK();
        Asserts.check(isHealthStatusOK, "Health status is not OK");

        return this;
    }

    public void verifyUptimeDisplay() {
        boolean isUptimeDisplayed = dashboardScreen.isUptimeStatusDisplayed();
        Asserts.check(isUptimeDisplayed, "Uptime status is not displayed");

        boolean isUptimeDisplayedInSeconds = dashboardScreen.isUptimeDisplayedInSeconds();
        Asserts.check(isUptimeDisplayedInSeconds, "Uptime status is not displayed in seconds");
    }

    public void scrollToAnalyticsDashboard() {
        System.out.println("BL: Scrolling to Analytics Dashboard section");
        dashboardScreen.scrollToAnalyticsDashboard();
        System.out.println("BL: Successfully scrolled to Analytics Dashboard");
    }

    public void verifyUserAnalytics() {
        int totalCount = dashboardScreen.userFetchesTotalCountInUsers();
        Assert.assertEquals(totalCount, 3, "Default Total user count is not as expected");

        int adminCount = dashboardScreen.userFetchesAdminCountInUsers();
        Assert.assertEquals(adminCount, 1, "Default Admin user count is not as expected");

        int regularCount = dashboardScreen.userFetchesRegularCountInUsers();
        Assert.assertEquals(regularCount, 2, "Default Regular user count is not as expected");
        System.out.println("BL: User analytics verification completed");
    }

    public void verifyProductsAnalytics() {
        int totalCount = dashboardScreen.userFetchesTotalCountInProducts();
        Assert.assertEquals(totalCount, 5, "Default Total user count is not as expected");

        int categoriesCount = dashboardScreen.userFetchesCategoriesCountInProducts();
        Assert.assertEquals(categoriesCount, 3, "Default Admin user count is not as expected");

        int lowStockCount = dashboardScreen.userFetchesLowStockCountInProducts();
        Assert.assertEquals(lowStockCount, 0, "Default Regular user count is not as expected");
    }

    public void verifyTasksAnalytics() {
        int totalCount = dashboardScreen.userFetchesTotalCountInTasks();
        Assert.assertEquals(totalCount, 3, "Default Total task count is not as expected");

        int completedCount = dashboardScreen.userFetchesCompletedCountInTasks();
        Assert.assertEquals(completedCount, 1, "Default Completed task count is not as expected");

        int highPriorityCount = dashboardScreen.userFetchesHighPriorityCountInTasks();
        Assert.assertEquals(highPriorityCount, 2, "Default High Priority task count is not as expected");
    }

    public void verifyOrdersAnalytics() {
        int totalCount = dashboardScreen.userFetchesTotalCountInOrders();
        Assert.assertEquals(totalCount, 0, "Default Total order count is not as expected");

        int pendingCount = dashboardScreen.userFetchesPendingCountInOrders();
        Assert.assertEquals(pendingCount, 0, "Default Pending order count is not as expected");

        int revenue = dashboardScreen.userFetchesRevenueInOrders();
        Assert.assertEquals(revenue, 0, "Default Revenue is not as expected");
    }


//
//    public void verifyAllAnalytics() {
////        System.out.println("BL: Performing comprehensive analytics verification");
////
////        scrollToAnalyticsDashboard();
////        verifyUserAnalytics();
////        verifyProductsAnalytics();
////        verifyTasksAnalytics();
////        verifyOrdersAnalytics();
////
////        System.out.println("BL: All analytics verification completed successfully");
//    }
//
//    /**
//     * Complete dashboard health check including backend status and all components
//     */
//    public void performCompleteHealthCheck() {
//        System.out.println("BL: Performing complete dashboard health check");
//
//
//
//        System.out.println("BL: Complete health check performed successfully");
//    }
//
//    /**
//     * Verify backend API availability at specified URL
//     */
//    public void verifyBackendAPIAvailability(String apiUrl) {
//        System.out.println("BL: Verifying backend API availability at " + apiUrl);
//
//        // This could be enhanced to make actual API calls to verify backend
//        // For now, we rely on the frontend display of backend status
//        performCompleteHealthCheck();
//
//        System.out.println("BL: Backend API availability verified through frontend indicators");
//    }
//
//    /**
//     * Complete Dashboard feature validation
//     * This method combines all dashboard-related validations
//     */
//    public void performCompleteDashboardValidation(String appUrl, String apiUrl, String expectedTitle, String expectedTabs) {
//        System.out.println("BL: Starting complete Dashboard feature validation");
//
////        // TC001 - Application Loading and Navigation
////        backgroundScreen.openApplication(appUrl);
////        validatePageTitle(expectedTitle);
////        validateNavigationTabs(expectedTabs);
////        clickEachNavigationTab();
////        verifyActiveStateChanges();
////        verifyContentChanges();
////
////        // TC002 - Dashboard Health Status Display
////        navigateToDashboardTab();
////        viewBackendStatusSection();
////        verifyHealthStatusOK();
////        verifyUptimeDisplay();
////        verifyTimestampDisplay();
////
////        // TC003 - Analytics Dashboard Functionality
////        scrollToAnalyticsDashboard();
////        verifyAllAnalytics();
////
////        System.out.println("BL: Complete Dashboard feature validation completed successfully!");
//    }

}
