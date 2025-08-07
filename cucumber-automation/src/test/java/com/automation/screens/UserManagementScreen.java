package com.automation.screens;

import com.automation.utils.DriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class UserManagementScreen {

    public WebDriver driver;
    public WebDriverWait wait;

    // Navigation Elements

    // User Form Elements
    @FindBy(css = "input[name='name'], input[placeholder*='name'], #name, .name-input")
    private WebElement nameField;

    @FindBy(css = "input[name='email'], input[placeholder*='email'], #email, .email-input")
    private WebElement emailField;

    @FindBy(css = "select[name='role'], select#role, .role-select")
    private WebElement roleSelect;

    @FindBy(css = "button:contains('Add User'), .add-user-btn, [data-testid='add-user-btn']")
    private WebElement addUserButton;

    @FindBy(css = "button:contains('Refresh'), .refresh-btn, [data-testid='refresh-users-btn']")
    private WebElement refreshUsersButton;

    @FindBy(css = ".user-count, .total-users, [data-testid='user-count']")
    private WebElement userCount;

    // Toast/Notification Elements
    @FindBy(css = ".toast, .notification, .alert-success, [data-testid='toast']")
    private WebElement toastNotification;

    // Validation Message Elements
    @FindBy(css = ".error-message, .validation-error, .field-error")
    private List<WebElement> validationMessages;

    @FindBy(css = ".loading, .spinner, [data-testid='loading']")
    private WebElement loadingIndicator;

    // Alternative locators for fallback
    private final By USERS_TAB_ALT = By.xpath("//button[contains(text(), 'Users')] | //a[contains(text(), 'Users')]");
    private final By NAME_FIELD_ALT = By.xpath("//input[contains(@placeholder, 'name') or contains(@placeholder, 'Name')]");
    private final By EMAIL_FIELD_ALT = By.xpath("//input[contains(@placeholder, 'email') or contains(@placeholder, 'Email')]");
    private final By ROLE_SELECT_ALT = By.xpath("//select[contains(@name, 'role')] | //select[contains(@id, 'role')]");
    private final By ADD_USER_BTN_ALT = By.xpath("//button[contains(text(), 'Add User') or contains(text(), 'Add')]");

    public UserManagementScreen() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public UserManagementScreen userEntersDetailsInForm(String name, String email, String role) {
        driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(name);
        driver.findElement(By.cssSelector("input[placeholder='Email']")).sendKeys(email);
        driver.findElement(By.cssSelector("form > select")).click();
        if (role.equals("Admin")) {
            driver.findElement(By.cssSelector("select > option[value='admin']")).click();
        } else if (role.equals("User")) {
            driver.findElement(By.cssSelector("select > option[value='user']")).click();
        }
        return this;
    }

    public UserManagementScreen clicksAddUserButton() {
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        return this;
    }

    public String getSuccessToastNotification() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='alert']")));
        return driver.findElement(By.cssSelector("div[role='alert']")).getText();
    }



    public boolean verifyUserInList(String name, String email) {
        String xpath = "//div[@class='user-card']//h3[text()='@name']//following-sibling::p[text()='@email']".replace("@name", name).replace("@email", email);
        return driver.findElement(By.xpath(xpath)).isDisplayed();
    }

    public void fillNameField(String name) {
        try {
            WebElement nameInput = wait.until(ExpectedConditions.visibilityOf(nameField));
            nameInput.clear();
            nameInput.sendKeys(name);
            System.out.println("Filled name field with: " + name);
        } catch (Exception e) {
            System.out.println("Using alternative locator for name field");
            WebElement nameInputAlt = wait.until(ExpectedConditions.visibilityOfElementLocated(NAME_FIELD_ALT));
            nameInputAlt.clear();
            nameInputAlt.sendKeys(name);
        }
    }

    public void fillEmailField(String email) {
        try {
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOf(emailField));
            emailInput.clear();
            emailInput.sendKeys(email);
            System.out.println("Filled email field with: " + email);
        } catch (Exception e) {
            System.out.println("Using alternative locator for email field");
            WebElement emailInputAlt = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD_ALT));
            emailInputAlt.clear();
            emailInputAlt.sendKeys(email);
        }
    }

    public void selectRole(String role) {
        try {
            WebElement roleSelectElement = wait.until(ExpectedConditions.visibilityOf(roleSelect));
            Select select = new Select(roleSelectElement);
            select.selectByVisibleText(role);
            System.out.println("Selected role: " + role);
        } catch (Exception e) {
            System.out.println("Using alternative locator for role select");
            WebElement roleSelectAlt = wait.until(ExpectedConditions.visibilityOfElementLocated(ROLE_SELECT_ALT));
            Select select = new Select(roleSelectAlt);
            select.selectByVisibleText(role);
        }
    }

    public void clickAddUserButton() {
        try {
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addUserButton));
            addButton.click();
            System.out.println("Clicked Add User button");
        } catch (Exception e) {
            System.out.println("Using alternative locator for Add User button");
            WebElement addButtonAlt = wait.until(ExpectedConditions.elementToBeClickable(ADD_USER_BTN_ALT));
            addButtonAlt.click();
        }
        
        // Wait for action to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isSuccessToastVisible() {
        try {
            WebElement toast = wait.until(ExpectedConditions.visibilityOf(toastNotification));
            String toastText = toast.getText().toLowerCase();
            return toastText.contains("success") || toastText.contains("added") || toastText.contains("created");
        } catch (Exception e) {
            System.out.println("Toast notification not found with primary locator, trying alternatives");
            try {
                WebElement toastAlt = driver.findElement(By.xpath("//div[contains(@class, 'toast') or contains(@class, 'notification')][contains(text(), 'success') or contains(text(), 'added')]"));
                return toastAlt.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public int getUserCount() {
        try {
            WebElement countElement = wait.until(ExpectedConditions.visibilityOf(userCount));
            String countText = countElement.getText().replaceAll("[^0-9]", "");
            return Integer.parseInt(countText);
        } catch (Exception e) {
            System.out.println("Using alternative method to count users");
            List<WebElement> users = driver.findElements(By.cssSelector(".user-card, .user-item, [data-testid='user-card']"));
            return users.size();
        }
    }

    public void submitUserFormWithData(String name, String email, String role) {
        if (name != null && !name.isEmpty()) {
            fillNameField(name);
        }
        if (email != null && !email.isEmpty()) {
            fillEmailField(email);
        }
        if (role != null && !role.isEmpty()) {
            selectRole(role);
        }
        clickAddUserButton();
    }

    public boolean areValidationMessagesVisible() {
        try {
            return !validationMessages.isEmpty() && validationMessages.get(0).isDisplayed();
        } catch (Exception e) {
            // Check for common validation message patterns
            List<WebElement> errorMsgs = driver.findElements(By.xpath("//div[contains(@class, 'error') or contains(@class, 'invalid')]//text()[contains(., 'required') or contains(., 'invalid') or contains(., 'error')]"));
            return !errorMsgs.isEmpty();
        }
    }

    public void clickDeleteButtonForUser(int index) {
        String xpath = "//div[@class='user-card'][@index]//button".replace("@index", String.valueOf(index));
        driver.findElement(By.xpath(xpath)).click();
    }

    public void clickRefreshUsersButton() {
        try {
            WebElement refreshBtn = wait.until(ExpectedConditions.elementToBeClickable(refreshUsersButton));
            refreshBtn.click();
            System.out.println("Clicked Refresh Users button");
        } catch (Exception e) {
            System.out.println("Using alternative locator for Refresh button");
            WebElement refreshBtnAlt = driver.findElement(By.xpath("//button[contains(text(), 'Refresh')]"));
            refreshBtnAlt.click();
        }
        
        // Wait for refresh to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isLoadingStateVisible() {
        try {
            return loadingIndicator.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement loading = driver.findElement(By.xpath("//div[contains(@class, 'loading') or contains(@class, 'spinner')]"));
                return loading.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public void enterTextInField(String input, String fieldName) {
        WebElement field = null;
        
        switch (fieldName.toLowerCase()) {
            case "name":
                try {
                    field = nameField;
                } catch (Exception e) {
                    field = driver.findElement(NAME_FIELD_ALT);
                }
                break;
            case "email":
                try {
                    field = emailField;
                } catch (Exception e) {
                    field = driver.findElement(EMAIL_FIELD_ALT);
                }
                break;
        }
        
        if (field != null) {
            field.clear();
            field.sendKeys(input);
            System.out.println("Entered '" + input + "' in " + fieldName + " field");
        }
    }

    public boolean isUserExistsByEmail(String email) {
        try {
            String xpath = String.format("//div[contains(@class, 'user') and contains(text(), '%s')]", email);
            WebElement user = driver.findElement(By.xpath(xpath));
            return user.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void addUserWithEmail(String name, String email, String role) {
        fillNameField(name);
        fillEmailField(email);
        selectRole(role);
        clickAddUserButton();
    }

    public UserManagementScreen userAcceptsAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return this;
    }

    public String getAlertText() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public String getUserName(int index) {
        String xpath = "//div[@class='user-card'][@index]//h3".replace("@index", String.valueOf(index));
        return driver.findElement(By.xpath(xpath)).getText();
    }
    public String getUserEmail(int index) {
        String xpath = "//div[@class='user-card'][@index]//p".replace("@index", String.valueOf(index));
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public String getUserRole(int index) {
        String xpath = "//div[@class='user-card'][@index]//span[contains(@class, 'role')]".replace("@index", String.valueOf(index));
        return driver.findElement(By.xpath(xpath)).getText();
    }
}
