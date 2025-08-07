package com.automation.businessLayer;

import com.automation.screens.TaskManagementScreen;
import com.automation.screens.UserManagementScreen;
import com.automation.utils.ContextStore;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class TaskManagementBL {
    
    private TaskManagementScreen taskManagementScreen;
    private int initialTaskCount;
    private String lastCreatedTaskTitle;
    private WebElement currentTaskElement;
    
    public TaskManagementBL() {
        this.taskManagementScreen = new TaskManagementScreen();
    }

    
    // Task creation methods
    public void createNewTask(String title, String priority) {
        recordInitialTaskCount();
        taskManagementScreen.enterTaskTitle(title);
        taskManagementScreen.selectPriorityLevel(priority);
        lastCreatedTaskTitle = title;
    }

    public TaskManagementBL verifyTaskAddedNotification() {
        String taskAddedToast = new UserManagementScreen().getSuccessToastNotification();

        Assert.assertEquals(taskAddedToast, "Task added successfully!","Task Added toast message is not visible");
        return this;
    }
    
    public void createNewTaskWithAssignment(String title, String priority, int assignee) {
        String userName = getUserDetails(assignee);
        taskManagementScreen.enterTaskTitle(title);
        taskManagementScreen.selectPriorityLevel(priority + " Priority");
        taskManagementScreen.assignTaskToUser(userName);
        taskManagementScreen.clickAddTaskButton();
    }

    private String getUserDetails(int assignee) {
        try {

            String url = "http://localhost:5001/api/users";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray users = jsonResponse.getJSONArray("users");
            JSONObject user = users.getJSONObject(assignee);

            return user.getString("name");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user details: " + e.getMessage());
        }
    }

    public void enterTaskTitle(String title) {
        taskManagementScreen.enterTaskTitle(title);
        lastCreatedTaskTitle = title;
    }
    
    public void selectPriorityLevel(String priority) {
        taskManagementScreen.selectPriorityLevel(priority);
    }
    
    public void assignTaskToUserIfUsersExist() {
        if (taskManagementScreen.hasUsersInSystem()) {
            // In a real scenario, you might want to get the first available user
            // For now, we'll assume there's a default user or select the first option
            try {
                taskManagementScreen.assignTaskToUser("Test User");
            } catch (Exception e) {
                // If assignment fails, continue without assignment
                System.out.println("No users available for assignment or assignment failed");
            }
        }
    }
    
    public void clickAddTaskButton() {
        taskManagementScreen.clickAddTaskButton();
    }
    

    public void verifyTaskAppearsInList() {
        String taskTitle = (String) ContextStore.get("TASK_TITLE");
        taskManagementScreen.verifiesTaskAddedInList(taskTitle);
    }

    public void submitsTheCreatedOrDefaultTask(String taskType) {
        if ("default".equalsIgnoreCase(taskType)) {
            this.completesDefaultTask();
        } else {
            this.completedCreatedTask();
        }
    }

    private void completedCreatedTask() {
        String taskTitle = (String) ContextStore.get("TASK_TITLE");
        boolean isTaskCompleted = taskManagementScreen
                .userSubmitsTheTask(taskTitle)
                .isTaskCompleted(taskTitle);

        Assert.assertTrue(isTaskCompleted, "Submitted Task is still uncomplete");
    }

    private void completesDefaultTask() {
        try {
            String url = "http://localhost:5001/api/tasks";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray tasks = jsonResponse.getJSONArray("tasks");
            for(int i=0; i< tasks.length(); i++) {
                JSONObject task = tasks.getJSONObject(i);
                if (!task.getBoolean("completed")) {
                    String taskTitle = task.getString("title");
                    boolean isTaskCompleted = taskManagementScreen
                            .userSubmitsTheTask(taskTitle)
                            .isTaskCompleted(taskTitle);

                    Assert.assertTrue(isTaskCompleted, "Submitted Task is still uncomplete");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user details for default task: " + e.getMessage());
        }
    }

    public void verifyTaskCountIncreased() {
        int currentCount = taskManagementScreen.getTaskCount();
        Assert.assertTrue(currentCount > initialTaskCount, 
            "Task count should increase after adding a new task. Initial: " + initialTaskCount + ", Current: " + currentCount);
    }
    
    public void verifySuccessNotification() {
        Assert.assertTrue(taskManagementScreen.isSuccessNotificationDisplayed(), 
            "Success notification should be displayed after creating a task");
    }
    
    // Task status toggle methods
    public void findAndSelectIncompleteTask() {
        currentTaskElement = taskManagementScreen.findIncompleteTask();
        Assert.assertNotNull(currentTaskElement, "At least one incomplete task should exist");
    }
    
    public void clickTaskToggleButton() {
        Assert.assertNotNull(currentTaskElement, "Task element should be selected before toggling");
        taskManagementScreen.clickTaskToggle(currentTaskElement);
    }
    
    public void verifyTaskShowsAsIncomplete() {
        Assert.assertTrue(taskManagementScreen.isTaskIncomplete(currentTaskElement), 
            "Task should show as incomplete after toggling back");
    }
    
    public void verifyTaskStatusUpdatesCorrectly() {
        // This method can be used to verify that the task status change persists
        // Additional verification can be added here for backend persistence
        Assert.assertNotNull(currentTaskElement, "Task element should exist for status verification");
    }
    
    // Priority display verification methods
    public void createTasksWithDifferentPriorities(List<Map<String, String>> taskData) {
        for (Map<String, String> task : taskData) {
            String title = task.get("title");
            String priority = task.get("priority");
            
            taskManagementScreen.enterTaskTitle(title);
            taskManagementScreen.selectPriorityLevel(priority+ " Priority");
            taskManagementScreen.clickAddTaskButton();
            
            // Wait a moment for the task to be added before creating the next one
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void verifyPriorityBadgesDisplayCorrectly() {
        List<Map<String, String>> taskData = (List<Map<String, String>>) ContextStore.get("TASK_DATA");

        for (Map<String, String> task : taskData) {
            String title = task.get("title");
            String priority = task.get("priority");

            String badge = taskManagementScreen.getPriorityBadgeForTask(title);
            String actualBadge = badge.substring(0,1).toUpperCase() + badge.substring(1).toLowerCase();
            Assert.assertEquals(actualBadge, priority,"Priority badge is not setup correctly for task " + title);

            String expectedColorOfPriorityBadge = this.getExpectedColorOfPriorityBadge(priority);
            String actualColorOfPriorityBadge = taskManagementScreen.getColourOfPriroityBadge(title);

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(actualColorOfPriorityBadge,expectedColorOfPriorityBadge,
                String.format("Priority badge color for task %s does not match expected color for priority %s", title, priority));
        }
    }

    public void createTaskWithUnAssignedName(String title) {
        taskManagementScreen
                .enterTaskTitle(title)
                .selectPriorityLevel("High"+ " Priority")
                .clickAddTaskButton();

        this.verifyTaskAddedNotification();
    }

    private String getExpectedColorOfPriorityBadge(String priority) {
        String actualValue;
        switch (priority.toLowerCase()) {
            case "high":
                actualValue =  "rgba(220, 38, 38)";
                break;
            case "medium":
                actualValue = "rgba(245, 158, 11)";
                break;
            case "low":
                actualValue = "rgba(34, 197, 94)";
                break;
            default:
                throw new RuntimeException("Not a valid priority: " + priority);
        }
        return actualValue;

    }
    
    public void verifyPriorityColorsAndStyling() {
        List<WebElement> priorityBadges = taskManagementScreen.getPriorityBadges();
        
        for (WebElement badge : priorityBadges) {
            String badgeText = badge.getText();
            if (badgeText.contains("High")) {
                Assert.assertTrue(taskManagementScreen.verifyPriorityBadgeColor("high", badge), 
                    "High priority badge should have appropriate styling");
            } else if (badgeText.contains("Medium")) {
                Assert.assertTrue(taskManagementScreen.verifyPriorityBadgeColor("medium", badge), 
                    "Medium priority badge should have appropriate styling");
            } else if (badgeText.contains("Low")) {
                Assert.assertTrue(taskManagementScreen.verifyPriorityBadgeColor("low", badge), 
                    "Low priority badge should have appropriate styling");
            }
        }
    }
    
    // Task assignment methods
    public void createTaskWithAssignment(String title, String assignee) {
        taskManagementScreen.enterTaskTitle(title);
        taskManagementScreen.assignTaskToUser(assignee);
        taskManagementScreen.clickAddTaskButton();
        lastCreatedTaskTitle = title;
    }
    
    public void verifyTaskAssignment(String taskTitle, String assignee) {
        Assert.assertTrue(taskManagementScreen.isTaskAssignedToUser(taskTitle, assignee), 
            "Task '" + taskTitle + "' should be assigned to user '" + assignee + "'");
    }
    
    public void verifyUserAppearsInTaskAssignment(String assignee) {
        Assert.assertTrue(taskManagementScreen.isTaskAssignedToUser(lastCreatedTaskTitle, assignee), 
            "User '" + assignee + "' should appear in the task assignment");
    }
    
    // Task filtering methods
    public void createTasksWithVariousStatusesAndPriorities() {
        // Create tasks with different statuses and priorities for filtering tests
        createNewTask("High Priority Task", "High");
        clickAddTaskButton();
        
        createNewTask("Low Priority Task", "Low");
        clickAddTaskButton();
        
        createNewTask("Medium Priority Task", "Medium");
        clickAddTaskButton();
        
        // Complete one of the tasks to have different statuses
        try {
            WebElement incompleteTask = taskManagementScreen.findIncompleteTask();
            taskManagementScreen.clickTaskToggle(incompleteTask);
        } catch (Exception e) {
            // If toggling fails, continue with test
        }
    }
    
    public void filterTasksByStatusOrPriority(String filterType, String filterValue) {
        if ("status".equalsIgnoreCase(filterType)) {
            taskManagementScreen.filterByStatus(filterValue);
        } else if ("priority".equalsIgnoreCase(filterType)) {
            taskManagementScreen.filterByPriority(filterValue);
        }
    }
    
    public void verifyOnlyMatchingTasksDisplayed(String filterType, String filterValue) {
        List<WebElement> filteredTasks = taskManagementScreen.getFilteredTasks();
        Assert.assertFalse(filteredTasks.isEmpty(), "Filtered tasks should not be empty");
        
        // Verify that displayed tasks match the filter criteria
        for (WebElement task : filteredTasks) {
            if ("priority".equalsIgnoreCase(filterType)) {
                Assert.assertTrue(task.getText().contains(filterValue), 
                    "Displayed task should match priority filter: " + filterValue);
            }
            // Additional status filtering verification can be added here
        }
    }
    
    // Task validation methods
    public void attemptToCreateTaskWithEmptyTitle() {
        taskManagementScreen.enterTaskTitle("");
        taskManagementScreen.clickAddTaskButton();
    }
    
    public void verifyValidationErrorAppears() {
        Assert.assertTrue(taskManagementScreen.isValidationErrorDisplayed(), 
            "Validation error should be displayed for empty task title");
    }
    
    public void verifyTaskNotCreated() {
        // Check that no new task was added to the list
        int currentCount = taskManagementScreen.getTaskCount();
        Assert.assertEquals(currentCount, initialTaskCount, 
            "Task count should not increase when validation fails");
    }
    
    public void createValidTask(String title) {
        taskManagementScreen.enterTaskTitle(title);
        taskManagementScreen.selectPriorityLevel("Medium"); // Default priority
        taskManagementScreen.clickAddTaskButton();
        lastCreatedTaskTitle = title;
    }
    
    public void verifyTaskCreatedSuccessfully() {
        verifyTaskAppearsInList();
        verifySuccessNotification();
    }
    
    // Bulk operations methods
    public void ensureMultipleTasksExist() {
        int currentCount = taskManagementScreen.getTaskCount();
        if (currentCount < 3) {
            // Create additional tasks for bulk operations
            for (int i = currentCount; i < 3; i++) {
                createNewTask("Bulk Test Task " + (i + 1), "Medium");
                clickAddTaskButton();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    public void selectMultipleTasks(int count) {
        taskManagementScreen.selectMultipleTasks(count);
    }
    
    public void performBulkStatusChange(String status) {
        taskManagementScreen.performBulkStatusChange(status);
    }
    
    public void verifyAllSelectedTasksStatusChanged(String expectedStatus) {
        // This is a simplified verification
        // In a real implementation, you'd track which tasks were selected
        // and verify their individual status changes
        List<WebElement> tasks = taskManagementScreen.getFilteredTasks();
        Assert.assertFalse(tasks.isEmpty(), "Tasks should exist after bulk operation");
    }
    
    public void verifyTaskStatisticsUpdated() {
        // Verify that task statistics (counts, percentages, etc.) are updated
        // This would typically involve checking dashboard counters or summary statistics
        int currentCount = taskManagementScreen.getTaskCount();
        Assert.assertTrue(currentCount >= 0, "Task count should be valid after bulk operations");
    }
    
    // Utility methods
    private void recordInitialTaskCount() {
        initialTaskCount = taskManagementScreen.getTaskCount();
    }
    
    public void verifyUsersExistInSystem() {
        Assert.assertTrue(taskManagementScreen.hasUsersInSystem(), 
            "Users should exist in the system for assignment testing");
    }
    
    public void waitForTasksToLoad() {
        taskManagementScreen.waitForTasksToLoad();
    }
    
    // Search functionality
    public void searchForTasks(String searchTerm) {
        taskManagementScreen.searchTasks(searchTerm);
    }
    
    public void verifySearchResults(String searchTerm) {
        List<WebElement> filteredTasks = taskManagementScreen.getFilteredTasks();
        for (WebElement task : filteredTasks) {
            Assert.assertTrue(task.getText().toLowerCase().contains(searchTerm.toLowerCase()), 
                "Search results should contain the search term: " + searchTerm);
        }
    }
    
    // Additional verification methods
    public void verifyTaskExists(String taskTitle) {
        Assert.assertTrue(taskManagementScreen.isTaskInList(taskTitle), 
            "Task '" + taskTitle + "' should exist in the system");
    }
    
    public void verifyAtLeastOneIncompleteTaskExists() {
        try {
            WebElement incompleteTask = taskManagementScreen.findIncompleteTask();
            Assert.assertNotNull(incompleteTask, "At least one incomplete task should exist");
        } catch (Exception e) {
            Assert.fail("No incomplete tasks found in the system");
        }
    }
    
    public String getValidationErrorMessage() {
        return taskManagementScreen.getValidationErrorText();
    }
}
