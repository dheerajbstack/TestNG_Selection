package com.automation.screens;

import com.automation.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class TaskManagementScreen {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Navigation elements
    @FindBy(xpath = "//nav//a[contains(text(), 'Tasks') or contains(@href, 'tasks')]")
    private WebElement tasksTab;
    
    @FindBy(xpath = "//div[contains(@class, 'tasks-tab') or contains(@id, 'tasks')]//a[contains(text(), 'Tasks')]")
    private WebElement tasksTabAlt;
    
    // Task creation form elements
    @FindBy(xpath = "//input[@placeholder='Task title' or @name='title' or @id='task-title']")
    private WebElement taskTitleInput;
    
    @FindBy(xpath = "//input[@placeholder='Enter task title' or contains(@class, 'task-title')]")
    private WebElement taskTitleInputAlt;
    
    @FindBy(xpath = "//select[@name='priority' or @id='priority' or contains(@class, 'priority')]")
    private WebElement prioritySelect;
    
    @FindBy(xpath = "//div[contains(@class, 'priority-select')]//select")
    private WebElement prioritySelectAlt;
    
    @FindBy(xpath = "//select[@name='assignee' or @id='assignee' or contains(@class, 'user-select')]")
    private WebElement userAssignSelect;
    
    @FindBy(xpath = "//div[contains(@class, 'user-assignment')]//select")
    private WebElement userAssignSelectAlt;
    
    @FindBy(xpath = "//button[contains(text(), 'Add Task') or @id='add-task' or contains(@class, 'add-task')]")
    private WebElement addTaskButton;
    
    @FindBy(xpath = "//button[contains(@class, 'btn-add-task') or contains(@class, 'task-submit')]")
    private WebElement addTaskButtonAlt;
    
    // Task list elements
    @FindBy(xpath = "//div[contains(@class, 'tasks-list') or @id='tasks-list']")
    private WebElement tasksList;
    
    @FindBy(xpath = "//ul[contains(@class, 'task-list') or contains(@class, 'tasks')]")
    private WebElement tasksListAlt;
    
    @FindBy(xpath = "//div[contains(@class, 'task-item') or contains(@class, 'task')]")
    private List<WebElement> taskItems;
    
    @FindBy(xpath = "//li[contains(@class, 'task') or contains(@class, 'task-item')]")
    private List<WebElement> taskItemsAlt;
    
    // Task status toggle elements
    @FindBy(xpath = "//button[contains(@class, 'toggle') or contains(@class, 'status-toggle')]")
    private List<WebElement> statusToggleButtons;
    
    @FindBy(xpath = "//input[@type='checkbox' and contains(@class, 'task-status')]")
    private List<WebElement> statusCheckboxes;
    
    // Task priority badges
    @FindBy(xpath = "//span[contains(@class, 'priority') or contains(@class, 'badge')]")
    private List<WebElement> priorityBadges;
    
    // Filter elements
    @FindBy(xpath = "//select[@name='status-filter' or contains(@class, 'status-filter')]")
    private WebElement statusFilter;
    
    @FindBy(xpath = "//select[@name='priority-filter' or contains(@class, 'priority-filter')]")
    private WebElement priorityFilter;
    
    @FindBy(xpath = "//input[@type='search' or @placeholder='Search tasks' or contains(@class, 'search')]")
    private WebElement searchInput;
    
    // Bulk operations
    @FindBy(xpath = "//input[@type='checkbox' and contains(@class, 'select-all')]")
    private WebElement selectAllCheckbox;
    
    @FindBy(xpath = "//input[@type='checkbox' and contains(@class, 'task-select')]")
    private List<WebElement> taskSelectionCheckboxes;
    
    @FindBy(xpath = "//button[contains(text(), 'Bulk') or contains(@class, 'bulk-action')]")
    private WebElement bulkActionButton;
    
    @FindBy(xpath = "//select[contains(@class, 'bulk-status') or @name='bulk-status']")
    private WebElement bulkStatusSelect;
    
    // Notification elements
    @FindBy(xpath = "//div[contains(@class, 'notification') or contains(@class, 'alert') or contains(@class, 'message')]")
    private WebElement notification;
    
    @FindBy(xpath = "//div[contains(@class, 'success') and (contains(@class, 'notification') or contains(@class, 'alert'))]")
    private WebElement successNotification;
    
    @FindBy(xpath = "//div[contains(@class, 'error') and (contains(@class, 'notification') or contains(@class, 'alert'))]")
    private WebElement errorNotification;
    
    // Validation message elements
    @FindBy(xpath = "//span[contains(@class, 'error') or contains(@class, 'validation')]")
    private WebElement validationMessage;
    
    @FindBy(xpath = "//div[contains(@class, 'error-message') or contains(@class, 'field-error')]")
    private WebElement fieldErrorMessage;
    
    public TaskManagementScreen() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public TaskManagementScreen userSubmitsTheTask( String title) {
        String xpath = "//span[contains(@class,'assigned-to')]//parent::span//preceding-sibling::h3[text()='@title']//ancestor::div[contains(@class, 'task-item')]//button"
                .replace("@title", title);
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }

    public String getColourOfPriroityBadge(String title) {
        String xpath = "//div[contains(@class, 'task')]//h3[text()='@title']//following-sibling::span[contains(@class,'priority')]"
                .replace("@title", title);
        String val = driver.findElement(By.xpath(xpath)).getCssValue("color");
        return val;
    }
    
    // Navigation methods
    public String getPriorityBadgeForTask(String title) {
        String xpath = "//div[contains(@class, 'task')]//h3[text()='@title']//following-sibling::span[contains(@class,'priority')]"
                .replace("@title", title);
        return  driver.findElement(By.xpath(xpath)).getText();
    }
    
    // Task creation methods
    public TaskManagementScreen enterTaskTitle(String title) {
        driver.findElement(By.cssSelector("input[placeholder='Task Title']")).click();
        driver.findElement(By.cssSelector("input[placeholder='Task Title']")).sendKeys(title);
        return this;
    }
    
    public TaskManagementScreen selectPriorityLevel(String priority) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form//select[1]")));
        Select select = new Select(element);
        select.selectByVisibleText(priority);
        return this;
    }
    
    public void assignTaskToUser(String userName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form//select[2]")));
        Select select = new Select(element);
        select.selectByVisibleText(userName);
    }
    
    public void clickAddTaskButton() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    public void verifiesTaskAddedInList(String taskTitle) {
        String xpath = "//div[contains(@class, 'task')]//h3[contains(text(), '@taskTitle')]".replace("@taskTitle", taskTitle);
        driver.findElement(By.xpath(xpath)).isDisplayed();
    }
    
    // Task list verification methods
    public boolean isTaskInList(String taskTitle) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'task') and contains(text(), '" + taskTitle + "')]")));
            return true;
        } catch (Exception e) {
            try {
                driver.findElement(By.xpath("//li[contains(@class, 'task') and contains(., '" + taskTitle + "')]"));
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public int getTaskCount() {
        try {
            List<WebElement> tasks = driver.findElements(By.xpath("//div[contains(@class, 'task-item') or contains(@class, 'task')]"));
            if (tasks.isEmpty()) {
                tasks = driver.findElements(By.xpath("//li[contains(@class, 'task')]"));
            }
            return tasks.size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    // Task status methods
    public WebElement findIncompleteTask() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'task') and not(contains(@class, 'completed'))]")));
        } catch (Exception e) {
            return driver.findElement(By.xpath("//li[contains(@class, 'task') and not(contains(@class, 'completed'))]"));
        }
    }
    
    public void clickTaskToggle(WebElement taskElement) {
        try {
            WebElement toggleButton = taskElement.findElement(By.xpath(".//button[contains(@class, 'toggle')]"));
            toggleButton.click();
        } catch (Exception e) {
            try {
                WebElement checkbox = taskElement.findElement(By.xpath(".//input[@type='checkbox']"));
                checkbox.click();
            } catch (Exception ex) {
                // Fallback: click on any clickable element that might toggle status
                WebElement fallback = taskElement.findElement(By.xpath(".//*[contains(@class, 'status') or contains(text(), '○') or contains(text(), '✓')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fallback);
            }
        }
    }
    
    public boolean isTaskCompleted(String title) {
        String xpath = "//h3[text()='@title']//ancestor::div[contains(@class,'task-item')]".replace("@title", title);
        WebElement element = driver.findElement(By.xpath(xpath));
        try {
            return element.findElement(By.xpath("//button[contains(text(), '✓')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
//        try {
//            return taskElement.findElement(By.xpath(".//span[contains(text(), '✓')]")).isDisplayed();
//        } catch (Exception e) {
//            try {
//                return taskElement.getAttribute("class").contains("completed");
//            } catch (Exception ex) {
//                return false;
//            }
//        }
    }
    
    public boolean isTaskIncomplete(WebElement taskElement) {
        try {
            return taskElement.findElement(By.xpath(".//span[contains(text(), '○')]")).isDisplayed();
        } catch (Exception e) {
            try {
                return !taskElement.getAttribute("class").contains("completed");
            } catch (Exception ex) {
                return true;
            }
        }
    }
    
    // Priority verification methods
    public List<WebElement> getPriorityBadges() {
        try {
            return driver.findElements(By.xpath("//span[contains(@class, 'priority') or contains(@class, 'badge')]"));
        } catch (Exception e) {
            return driver.findElements(By.xpath("//div[contains(@class, 'priority')]"));
        }
    }
    
    public boolean verifyPriorityBadgeColor(String priority, WebElement badge) {
        String badgeClass = badge.getAttribute("class");
        String badgeText = badge.getText();
        
        switch (priority.toLowerCase()) {
            case "high":
                return badgeClass.contains("high") || badgeClass.contains("red") || badgeText.contains("High");
            case "medium":
                return badgeClass.contains("medium") || badgeClass.contains("yellow") || badgeText.contains("Medium");
            case "low":
                return badgeClass.contains("low") || badgeClass.contains("green") || badgeText.contains("Low");
            default:
                return false;
        }
    }
    
    // Filter methods
    public void filterByStatus(String status) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(statusFilter));
            Select select = new Select(element);
            select.selectByVisibleText(status);
        } catch (Exception e) {
            // Fallback: find filter by attributes
            WebElement fallbackElement = driver.findElement(By.xpath("//select[contains(@name, 'status') or contains(@class, 'status')]"));
            Select select = new Select(fallbackElement);
            select.selectByVisibleText(status);
        }
    }
    
    public void filterByPriority(String priority) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(priorityFilter));
            Select select = new Select(element);
            select.selectByVisibleText(priority);
        } catch (Exception e) {
            // Fallback: find filter by attributes
            WebElement fallbackElement = driver.findElement(By.xpath("//select[contains(@name, 'priority') and contains(@class, 'filter')]"));
            Select select = new Select(fallbackElement);
            select.selectByVisibleText(priority);
        }
    }
    
    public List<WebElement> getFilteredTasks() {
        try {
            return driver.findElements(By.xpath("//div[contains(@class, 'task-item') and not(contains(@style, 'display: none'))]"));
        } catch (Exception e) {
            return driver.findElements(By.xpath("//li[contains(@class, 'task') and not(contains(@style, 'display: none'))]"));
        }
    }
    
    // Bulk operations methods
    public void selectMultipleTasks(int count) {
        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type='checkbox' and contains(@class, 'task-select')]"));
        for (int i = 0; i < Math.min(count, checkboxes.size()); i++) {
            if (!checkboxes.get(i).isSelected()) {
                checkboxes.get(i).click();
            }
        }
    }
    
    public void performBulkStatusChange(String status) {
        try {
            WebElement bulkButton = wait.until(ExpectedConditions.elementToBeClickable(bulkActionButton));
            bulkButton.click();
            
            WebElement statusSelect = wait.until(ExpectedConditions.elementToBeClickable(bulkStatusSelect));
            Select select = new Select(statusSelect);
            select.selectByVisibleText(status);
            
            // Click apply or confirm button
            WebElement applyButton = driver.findElement(By.xpath("//button[contains(text(), 'Apply') or contains(text(), 'Confirm')]"));
            applyButton.click();
        } catch (Exception e) {
            // Fallback approach for bulk operations
            WebElement fallbackButton = driver.findElement(By.xpath("//button[contains(@class, 'bulk') or contains(text(), 'Bulk')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fallbackButton);
        }
    }
    
    // Validation and notification methods
    public boolean isSuccessNotificationDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successNotification));
            return successNotification.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement genericNotification = driver.findElement(By.xpath("//div[contains(@class, 'success') or contains(text(), 'success')]"));
                return genericNotification.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public boolean isValidationErrorDisplayed() {
        try {
            return validationMessage.isDisplayed() || fieldErrorMessage.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement errorElement = driver.findElement(By.xpath("//span[contains(@class, 'error') or contains(text(), 'required')]"));
                return errorElement.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public String getValidationErrorText() {
        try {
            if (validationMessage.isDisplayed()) {
                return validationMessage.getText();
            }
            if (fieldErrorMessage.isDisplayed()) {
                return fieldErrorMessage.getText();
            }
        } catch (Exception e) {
            // Fallback to find any error message
            try {
                WebElement errorElement = driver.findElement(By.xpath("//span[contains(@class, 'error')] | //div[contains(@class, 'error')]"));
                return errorElement.getText();
            } catch (Exception ex) {
                return "";
            }
        }
        return "";
    }
    
    // Task assignment verification
    public boolean isTaskAssignedToUser(String taskTitle, String userName) {
        try {
            WebElement taskElement = driver.findElement(By.xpath("//div[contains(., '" + taskTitle + "')]"));
            WebElement assignmentElement = taskElement.findElement(By.xpath(".//span[contains(@class, 'assignee') and contains(text(), '" + userName + "')]"));
            return assignmentElement.isDisplayed();
        } catch (Exception e) {
            try {
                // Alternative search approach
                WebElement assignmentElement = driver.findElement(By.xpath("//div[contains(., '" + taskTitle + "') and contains(., '" + userName + "')]"));
                return assignmentElement.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    // Search functionality
    public void searchTasks(String searchTerm) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
            element.clear();
            element.sendKeys(searchTerm);
        } catch (Exception e) {
            // Fallback: find search input
            WebElement fallbackElement = driver.findElement(By.xpath("//input[contains(@placeholder, 'search') or contains(@type, 'search')]"));
            fallbackElement.clear();
            fallbackElement.sendKeys(searchTerm);
        }
    }
    
    // Utility methods
    public void waitForTasksToLoad() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'tasks-list') or contains(@class, 'task')]")));
        } catch (Exception e) {
            // If tasks list doesn't load, wait a moment
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public boolean hasUsersInSystem() {
        try {
            WebElement userSelect = driver.findElement(By.xpath("//select[contains(@name, 'assignee') or contains(@class, 'user')]"));
            Select select = new Select(userSelect);
            return select.getOptions().size() > 1; // More than just the default option
        } catch (Exception e) {
            return false;
        }
    }
}
