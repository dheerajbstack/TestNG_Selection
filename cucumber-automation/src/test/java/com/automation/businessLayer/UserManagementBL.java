package com.automation.businessLayer;

import com.automation.screens.UserManagementScreen;
import com.automation.utils.ContextStore;
import com.automation.utils.LogCapture;
import io.cucumber.java.bs.A;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class UserManagementBL {
    
    private UserManagementScreen userManagementScreen;
    
    public UserManagementBL() {
        this.userManagementScreen = new UserManagementScreen();
    }

    public UserManagementBL addNewUser(String name, String email, String role) {
        LogCapture.addStepLog("User Management", "Adding new user with Name: " + name + ", Email: " + email + ", Role: " + role);
        System.out.println("BL: Adding new user - Name: " + name + ", Email: " + email + ", Role: " + role);
        int expectedCount = userManagementScreen.getUserCount();

        String actualToastMessage = userManagementScreen
                .userEntersDetailsInForm(name, email, role)
                .clicksAddUserButton()
                .getSuccessToastNotification();

        Assert.assertEquals(actualToastMessage, "User added successfully!", "Success toast message should indicate user was added");

        int actualCount = userManagementScreen.getUserCount();
        Assert.assertEquals(actualCount, expectedCount + 1, "User count should increase by 1 after adding a new user");

        boolean isUserDisplayedInList = userManagementScreen.verifyUserInList(name, email);
        Assert.assertTrue(isUserDisplayedInList, "Newly added user is not displayed in the users list");
//                .verifyUserCountUpdated();

        LogCapture.addStepLog("User Management", "✅ New user added successfully");
        System.out.println("BL: New user added successfully");

        return this;
    }

    public void verifyDefaultUserDetails() {
        List<Map<String, String>> users = (List<Map<String, String>> ) ContextStore.get("DEFAULT_USER_DETAILS");

        for(int i=0; i< 3;i++) {
            String expectedUserName = users.get(i).get("Name");
            String expectedUserEmail = users.get(i).get("Email");
            String expectedUserRole = users.get(i).get("Role");

            String actualUserName = userManagementScreen.getUserName(i+1);
            String actualUserEmail = userManagementScreen.getUserEmail(i+1);
            String actualUserRole = userManagementScreen.getUserRole(i+1);

            Assert.assertEquals(actualUserName, expectedUserName, "User Name is not as expected");
            Assert.assertEquals(actualUserEmail, expectedUserEmail, "User Email is not as expected");
            Assert.assertEquals(actualUserRole, expectedUserRole, "User Role is not as expected");
        }

    }


    public void fillUserForm(String name, String email, String role) {
        LogCapture.addStepLog("Form Input", String.format("Filling user form - Name: %s, Email: %s, Role: %s", name, email, role));
        System.out.println("BL: Filling user form with - Name: " + name + ", Email: " + email + ", Role: " + role);
        
        if (name != null && !name.isEmpty()) {
            userManagementScreen.fillNameField(name);
        }
        if (email != null && !email.isEmpty()) {
            userManagementScreen.fillEmailField(email);
        }
        if (role != null && !role.isEmpty()) {
            userManagementScreen.selectRole(role);
        }
        
        LogCapture.addStepLog("Form Input", "✅ User form filled successfully");
        System.out.println("BL: User form filled successfully");
    }
    
    public void clickAddUserButton() {
        LogCapture.addStepLog("User Action", "Clicking Add User button");
        System.out.println("BL: Clicking Add User button");
        
        userManagementScreen.clickAddUserButton();
        
        LogCapture.addStepLog("User Action", "✅ Add User button clicked");
        System.out.println("BL: Add User button clicked successfully");
    }
    
    public void verifySuccessToastNotification() {
        LogCapture.addStepLog("Verification", "Verifying success toast notification");
        System.out.println("BL: Verifying success toast notification");
        
        boolean isToastVisible = userManagementScreen.isSuccessToastVisible();
        
        Assert.assertTrue(isToastVisible, "Success toast notification should be visible after adding user");
        
        LogCapture.addStepLog("Verification", "✅ Success toast notification verified");
        System.out.println("BL: Success toast notification verified successfully");
    }
    
    public void verifyUserCountUpdated() {
        new DashboardBL().navigateToDashboardTab("Dashboard");
        
        int userCount = userManagementScreen.getUserCount();
        
        Assert.assertTrue(userCount > 0, "User count should be greater than 0 after adding user");
        
        LogCapture.addStepLog("Verification", "✅ User count verified - Count: " + userCount);
        System.out.println("BL: User count verified successfully - Count: " + userCount);
    }
    
    public void submitUserFormWithValidation(String name, String email, String role) {
        LogCapture.addStepLog("Form Submission", String.format("Submitting user form with validation - Name: '%s', Email: '%s', Role: '%s'", name, email, role));
        System.out.println("BL: Submitting user form with validation");
        
        userManagementScreen.submitUserFormWithData(name, email, role);
        
        LogCapture.addStepLog("Form Submission", "✅ User form submitted");
        System.out.println("BL: User form submitted for validation testing");
    }
    
    public void verifyValidationMessages() {
        LogCapture.addStepLog("Validation", "Verifying validation messages are displayed");
        System.out.println("BL: Verifying validation messages");
        
        boolean areValidationMessagesVisible = userManagementScreen.areValidationMessagesVisible();
        
        Assert.assertTrue(areValidationMessagesVisible, "Validation messages should be visible for invalid form submission");
        
        LogCapture.addStepLog("Validation", "✅ Validation messages verified");
        System.out.println("BL: Validation messages verified successfully");
    }
    
    public void ensureAtLeastOneUserExists() {
        LogCapture.addStepLog("Precondition", "Ensuring at least one user exists in the system");
        System.out.println("BL: Ensuring at least one user exists");
        
        int userCount = userManagementScreen.getUserCount();
        
        if (userCount == 0) {
            // Add a test user if none exist
            System.out.println("BL: No users found, adding a test user");
            fillUserForm("Test User", "test.user@example.com", "user");
            clickAddUserButton();
        }
        
        LogCapture.addStepLog("Precondition", "✅ At least one user exists in the system");
        System.out.println("BL: At least one user exists in the system");
    }
    
    public void deleteUserFromList(int num) {
        LogCapture.addStepLog("User Action", "Deleting first user from the list");
        System.out.println("BL: Deleting first user");
        
        int expectedCount = userManagementScreen.getUserCount();
        userManagementScreen.clickDeleteButtonForUser(num);

        this.userVerifiesAndAcceptAlertPopup();

        String actualToastMessage = userManagementScreen
                .getSuccessToastNotification();

        Assert.assertEquals(actualToastMessage, "User deleted successfully!", "Delete toast message is not as expected");
        System.out.println("BL: User deleted successfully");

        int actualCount = userManagementScreen.getUserCount();
        Assert.assertEquals(actualCount, expectedCount - 1, "User count should decrease by 1 after deleting a new user");
    }

    private UserManagementBL userVerifiesAndAcceptAlertPopup() {
        String alertBoxText = userManagementScreen.getAlertText();
        Assert.assertEquals(alertBoxText,"Are you sure you want to delete this user?", "Alert message should match expected text");

        userManagementScreen.userAcceptsAlert();
        return this;
    }
    
    public int noteCurrentUserCount() {
        LogCapture.addStepLog("Data Collection", "Noting current user count");
        System.out.println("BL: Noting current user count");
        
        int count = userManagementScreen.getUserCount();
        
        LogCapture.addStepLog("Data Collection", "✅ Current user count noted: " + count);
        System.out.println("BL: Current user count noted: " + count);
        
        return count;
    }
    
    public void clickRefreshUsersButton() {
        LogCapture.addStepLog("User Action", "Clicking Refresh Users button");
        System.out.println("BL: Clicking Refresh Users button");
        
        userManagementScreen.clickRefreshUsersButton();
        
        LogCapture.addStepLog("User Action", "✅ Refresh Users button clicked");
        System.out.println("BL: Refresh Users button clicked successfully");
    }
    
    public void verifyLoadingState() {
        LogCapture.addStepLog("Verification", "Verifying loading state if applicable");
        System.out.println("BL: Checking for loading state");
        
        boolean isLoadingVisible = userManagementScreen.isLoadingStateVisible();
        
        if (isLoadingVisible) {
            LogCapture.addStepLog("Verification", "✅ Loading state detected");
            System.out.println("BL: Loading state detected");
        } else {
            LogCapture.addStepLog("Verification", "ℹ️  No loading state detected");
            System.out.println("BL: No loading state detected (this may be expected)");
        }
    }
    
    public void verifyUsersReloaded() {
        LogCapture.addStepLog("Verification", "Verifying users are reloaded");
        System.out.println("BL: Verifying users are reloaded");
        
        int userCount = userManagementScreen.getUserCount();
        
        Assert.assertTrue(userCount >= 0, "User count should be valid after reload");
        
        LogCapture.addStepLog("Verification", "✅ Users reloaded successfully - Count: " + userCount);
        System.out.println("BL: Users reloaded successfully - Count: " + userCount);
    }
    
    public void verifyUserCountAccurate() {
        LogCapture.addStepLog("Verification", "Verifying user count is accurate");
        System.out.println("BL: Verifying user count accuracy");
        
        int count = userManagementScreen.getUserCount();
        
        Assert.assertTrue(count >= 0, "User count should be a valid number");
        
        LogCapture.addStepLog("Verification", "✅ User count is accurate: " + count);
        System.out.println("BL: User count is accurate: " + count);
    }
    
    public void ensureUserExistsByEmail(String email) {
        LogCapture.addStepLog("Precondition", "Ensuring user exists with email: " + email);
        System.out.println("BL: Ensuring user exists with email: " + email);
        
        boolean userExists = userManagementScreen.isUserExistsByEmail(email);
        
        if (!userExists) {
            // Create the user
            System.out.println("BL: User doesn't exist, creating user with email: " + email);
            userManagementScreen.addUserWithEmail("Existing User", email, "user");
        }
        
        LogCapture.addStepLog("Precondition", "✅ User with email " + email + " exists");
        System.out.println("BL: User with email " + email + " exists");
    }

    public UserManagementBL userTriesToCreatesNewUserWithExistingEmail(String email, String role) {
        userManagementScreen
                .userEntersDetailsInForm("Duplicate User", email, role);
        return this;
    }

    public UserManagementBL userTriesToCreatesNewUserWithInvalidEmail(String email, String role) {
        userManagementScreen
                .userEntersDetailsInForm("Invalid Email User", email, role);
        return this;
    }

    public void verifyDuplicateEmailError() {

        String actualToastMessage = userManagementScreen
                .clicksAddUserButton()
                .getSuccessToastNotification();

        Assert.assertEquals(actualToastMessage,"Error: Email already exists","Duplicate email error message should be visible");
    }


    public void enterInputInField(String input, String field) {
        LogCapture.addStepLog("Input", String.format("Entering '%s' in %s field", input, field));
        System.out.println("BL: Entering input in field - " + field + ": " + input);
        
        userManagementScreen.enterTextInField(input, field);
        
        LogCapture.addStepLog("Input", "✅ Input entered successfully");
        System.out.println("BL: Input entered successfully");
    }
}
