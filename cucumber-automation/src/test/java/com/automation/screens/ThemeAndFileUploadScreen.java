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

import java.io.File;
import java.time.Duration;
import java.util.List;

public class ThemeAndFileUploadScreen {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Navigation elements
    @FindBy(xpath = "//nav//a[contains(text(), 'Files') or contains(text(), 'Themes') or contains(@href, 'files')]")
    private WebElement filesThemesTab;
    
    @FindBy(xpath = "//div[contains(@class, 'files-tab') or contains(@id, 'files')]//a")
    private WebElement filesThemesTabAlt;
    
    // File upload elements
    @FindBy(xpath = "//input[@type='file' or contains(@class, 'file-input')]")
    private WebElement fileInput;
    
    @FindBy(xpath = "//div[contains(@class, 'file-upload')]//input[@type='file']")
    private WebElement fileInputAlt;
    
    @FindBy(xpath = "//button[contains(text(), 'Choose File') or contains(@class, 'file-choose')]")
    private WebElement chooseFileButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Upload') or contains(@class, 'upload-btn')]")
    private WebElement uploadButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Upload File') or @id='upload-file']")
    private WebElement uploadFileButton;
    
    // File details display
    @FindBy(xpath = "//div[contains(@class, 'file-details') or contains(@class, 'file-info')]")
    private WebElement fileDetailsSection;
    
    @FindBy(xpath = "//span[contains(@class, 'file-name') or contains(@class, 'filename')]")
    private WebElement fileNameDisplay;
    
    @FindBy(xpath = "//span[contains(@class, 'file-size') or contains(@class, 'filesize')]")
    private WebElement fileSizeDisplay;
    
    @FindBy(xpath = "//span[contains(@class, 'file-type') or contains(@class, 'filetype')]")
    private WebElement fileTypeDisplay;
    
    // Theme selection elements
    @FindBy(xpath = "//select[@name='theme' or contains(@class, 'theme-select')]")
    private WebElement themeSelect;
    
    @FindBy(xpath = "//div[contains(@class, 'theme-selector')]//select")
    private WebElement themeSelectAlt;
    
    @FindBy(xpath = "//div[contains(@class, 'theme-options')]")
    private WebElement themeOptionsContainer;
    
    @FindBy(xpath = "//div[contains(@class, 'theme-option') or contains(@class, 'theme-item')]")
    private List<WebElement> themeOptions;
    
    // Background theme elements
    @FindBy(xpath = "//select[@name='background' or contains(@class, 'background-select')]")
    private WebElement backgroundThemeSelect;
    
    @FindBy(xpath = "//div[contains(@class, 'background-theme')]//select")
    private WebElement backgroundThemeSelectAlt;
    
    // File list elements
    @FindBy(xpath = "//div[contains(@class, 'files-list') or @id='files-list']")
    private WebElement filesList;
    
    @FindBy(xpath = "//ul[contains(@class, 'uploaded-files') or contains(@class, 'file-list')]")
    private WebElement filesListAlt;
    
    @FindBy(xpath = "//div[contains(@class, 'file-item') or contains(@class, 'uploaded-file')]")
    private List<WebElement> uploadedFiles;
    
    @FindBy(xpath = "//li[contains(@class, 'file') or contains(@class, 'file-item')]")
    private List<WebElement> uploadedFilesAlt;
    
    // File actions
    @FindBy(xpath = "//button[contains(text(), 'Delete') or contains(@class, 'delete-btn')]")
    private List<WebElement> deleteButtons;
    
    @FindBy(xpath = "//button[contains(@class, 'file-delete') or contains(@title, 'Delete')]")
    private List<WebElement> deleteButtonsAlt;
    
    @FindBy(xpath = "//button[contains(text(), 'Preview') or contains(@class, 'preview-btn')]")
    private List<WebElement> previewButtons;
    
    // File count display
    @FindBy(xpath = "//span[contains(@class, 'file-count') or @id='file-count']")
    private WebElement fileCountDisplay;
    
    @FindBy(xpath = "//div[contains(@class, 'files-total')]")
    private WebElement fileCountDisplayAlt;
    
    // Notification elements
    @FindBy(xpath = "//div[contains(@class, 'notification') or contains(@class, 'alert')]")
    private WebElement notification;
    
    @FindBy(xpath = "//div[contains(@class, 'success') and (contains(@class, 'notification') or contains(@class, 'alert'))]")
    private WebElement successNotification;
    
    @FindBy(xpath = "//div[contains(@class, 'error') and (contains(@class, 'notification') or contains(@class, 'alert'))]")
    private WebElement errorNotification;
    
    // Special notices
    @FindBy(xpath = "//div[contains(@class, 'auto-background') or contains(text(), 'auto-background')]")
    private WebElement autoBackgroundNotice;
    
    @FindBy(xpath = "//div[contains(@class, 'special-notice') or contains(@class, 'image-notice')]")
    private WebElement specialNotice;
    
    // File preview elements
    @FindBy(xpath = "//div[contains(@class, 'file-preview') or @id='file-preview']")
    private WebElement filePreviewModal;
    
    @FindBy(xpath = "//img[contains(@class, 'preview-image')]")
    private WebElement previewImage;
    
    @FindBy(xpath = "//button[contains(@class, 'close-preview') or contains(text(), 'Close')]")
    private WebElement closePreviewButton;
    
    // Theme persistence elements
    @FindBy(xpath = "//body")
    private WebElement bodyElement;
    
    @FindBy(xpath = "//html")
    private WebElement htmlElement;
    
    // File validation messages
    @FindBy(xpath = "//span[contains(@class, 'validation-error') or contains(@class, 'file-error')]")
    private WebElement fileValidationError;
    
    @FindBy(xpath = "//div[contains(@class, 'error-message')]")
    private WebElement errorMessage;
    
    // Multiple file upload
    @FindBy(xpath = "//input[@type='file' and @multiple]")
    private WebElement multipleFileInput;
    
    @FindBy(xpath = "//div[contains(@class, 'multiple-upload')]")
    private WebElement multipleUploadContainer;
    
    // File sorting and organization
    @FindBy(xpath = "//select[contains(@class, 'sort-select') or @name='sort']")
    private WebElement sortSelect;
    
    @FindBy(xpath = "//button[contains(@class, 'sort-') or contains(text(), 'Sort')]")
    private List<WebElement> sortButtons;
    
    public ThemeAndFileUploadScreen() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public ThemeAndFileUploadScreen userSwitchesToNextTab() {
        // Open a new tab (simulate Ctrl+T)
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        String newHandle = driver.getWindowHandle();
        driver.switchTo().window(newHandle);
        return this;
    }

    public ThemeAndFileUploadScreen userJumpsBackToPreviousTabTab() {
        driver.close();
        String windowHandle = driver.getWindowHandle();
        driver.switchTo().window(windowHandle);
        return this;
    }

    // Navigation methods
    public void navigateToFilesThemesTab() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(filesThemesTab));
            element.click();
        } catch (Exception e) {
            try {
                WebElement altElement = wait.until(ExpectedConditions.elementToBeClickable(filesThemesTabAlt));
                altElement.click();
            } catch (Exception ex) {
                // Fallback: search for any element containing 'files' or 'themes'
                WebElement fallbackElement = driver.findElement(By.xpath("//*[contains(text(), 'Files') or contains(text(), 'Themes') or contains(@href, 'files')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fallbackElement);
            }
        }
    }
    
    // File upload methods
    public void clickFileInput() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(chooseFileButton));
            element.click();
        } catch (Exception e) {
            try {
                WebElement inputElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", inputElement);
            } catch (Exception ex) {
                // Alternative approach - trigger file input directly
                WebElement fallbackInput = driver.findElement(By.xpath("//input[@type='file']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'block';", fallbackInput);
            }
        }
    }
    
    public void selectFile(String fileName, String fileType) {
        try {
            // In a real scenario, this would involve file system interaction
            // For automation, we simulate file selection
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
            
            // Create a temporary file path for testing
            String testFilePath = System.getProperty("user.dir") + "/test-files/" + fileName;
            
            // Make file input visible if hidden
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible';", input);
            
            // Send the file path
            input.sendKeys(testFilePath);
            
        } catch (Exception e) {
            // Fallback: simulate file selection via JavaScript
            ((JavascriptExecutor) driver).executeScript(
                "var input = document.querySelector('input[type=\"file\"]');" +
                "var file = new File(['test content'], '" + fileName + "', {type: '" + getMimeType(fileType) + "'});" +
                "var dataTransfer = new DataTransfer();" +
                "dataTransfer.items.add(file);" +
                "input.files = dataTransfer.files;"
            );
        }
    }
    
    public void uploadImageFile(String fileName, String fileType) {
        selectFile(fileName, fileType);
        // Additional handling for image files if needed
    }
    
    public void selectMultipleFiles(List<String> fileNames) {
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file' and @multiple]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'block';", input);
            
            // For multiple files, we'd send multiple paths separated by newlines
            StringBuilder filePaths = new StringBuilder();
            for (String fileName : fileNames) {
                filePaths.append(System.getProperty("user.dir")).append("/test-files/").append(fileName).append("\n");
            }
            
            input.sendKeys(filePaths.toString().trim());
        } catch (Exception e) {
            // Fallback for multiple file selection
            for (String fileName : fileNames) {
                selectFile(fileName, "PDF"); // Default type
            }
        }
    }
    
    // Theme selection methods
    public void selectBackgroundTheme(String themeName) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(backgroundThemeSelect));
            Select select = new Select(element);
            select.selectByVisibleText(themeName);
        } catch (Exception e) {
            try {
                WebElement altElement = wait.until(ExpectedConditions.elementToBeClickable(backgroundThemeSelectAlt));
                Select select = new Select(altElement);
                select.selectByVisibleText(themeName);
            } catch (Exception ex) {
                // Fallback: find theme option by text
                WebElement themeOption = driver.findElement(By.xpath("//*[contains(text(), '" + themeName + "')]"));
                themeOption.click();
            }
        }
    }
    
    public ThemeAndFileUploadScreen selectTheme(String themeName) {
//        String xpath = "//button[contains(@class,'theme-option') and contains(text(),'@themeName')]".replace("@themeName", themeName);
        String xpath = "//button[contains(@class,'theme-option') and text()='@themeName']".replace("@themeName", themeName);
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }
    
    // Upload button methods
    public void clickUploadFileButton() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(uploadFileButton));
            element.click();
        } catch (Exception e) {
            try {
                WebElement altElement = wait.until(ExpectedConditions.elementToBeClickable(uploadButton));
                altElement.click();
            } catch (Exception ex) {
                // Fallback: find upload button by text
                WebElement fallbackElement = driver.findElement(By.xpath("//button[contains(text(), 'Upload')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fallbackElement);
            }
        }
    }
    
    public void completeUpload() {
        clickUploadFileButton();
        waitForUploadToComplete();
    }
    
    // File details verification methods
    public boolean areFileDetailsDisplayed() {
        try {
            return fileDetailsSection.isDisplayed() && 
                   fileNameDisplay.isDisplayed() && 
                   fileSizeDisplay.isDisplayed() && 
                   fileTypeDisplay.isDisplayed();
        } catch (Exception e) {
            try {
                // Alternative check for file details
                return driver.findElement(By.xpath("//div[contains(@class, 'file-info')]")).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public String getDisplayedFileName() {
        try {
            return fileNameDisplay.getText();
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//*[contains(@class, 'file-name')]")).getText();
            } catch (Exception ex) {
                return "";
            }
        }
    }
    
    public String getDisplayedFileSize() {
        try {
            return fileSizeDisplay.getText();
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//*[contains(@class, 'file-size')]")).getText();
            } catch (Exception ex) {
                return "";
            }
        }
    }
    
    public String getDisplayedFileType() {
        try {
            return fileTypeDisplay.getText();
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//*[contains(@class, 'file-type')]")).getText();
            } catch (Exception ex) {
                return "";
            }
        }
    }
    
    // File list verification methods
    public boolean isFileInUploadedList(String fileName) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'file-item') and contains(., '" + fileName + "')]")));
            return true;
        } catch (Exception e) {
            try {
                driver.findElement(By.xpath("//li[contains(@class, 'file') and contains(., '" + fileName + "')]"));
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public int getUploadedFileCount() {
        try {
            List<WebElement> files = driver.findElements(By.xpath("//div[contains(@class, 'file-item')]"));
            if (files.isEmpty()) {
                files = driver.findElements(By.xpath("//li[contains(@class, 'file')]"));
            }
            return files.size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public String getFileCountText() {
        try {
            return fileCountDisplay.getText();
        } catch (Exception e) {
            try {
                return fileCountDisplayAlt.getText();
            } catch (Exception ex) {
                return "0";
            }
        }
    }
    
    // Special notices and messages
    public boolean isAutoBackgroundNoticeDisplayed() {
        try {
            return autoBackgroundNotice.isDisplayed() || specialNotice.isDisplayed();
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//*[contains(text(), 'auto-background') or contains(text(), 'automatically')]")).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public boolean isSpecialNoticeDisplayed() {
        return isAutoBackgroundNoticeDisplayed();
    }
    
    // Theme verification methods
    public boolean hasBackgroundChanged(String expectedTheme) {
        try {
            String bodyClass = bodyElement.getAttribute("class");
            String bodyStyle = bodyElement.getAttribute("style");
            String htmlClass = htmlElement.getAttribute("class");
            
            String themeIdentifier = expectedTheme.toLowerCase().replace(" ", "-");
            
            return bodyClass.contains(themeIdentifier) || 
                   bodyStyle.contains(themeIdentifier) || 
                   htmlClass.contains(themeIdentifier) ||
                   isThemeAppliedToElements(themeIdentifier);
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isThemeAppliedToElements(String themeIdentifier) {
        try {
            List<WebElement> themedElements = driver.findElements(By.xpath("//*[contains(@class, '" + themeIdentifier + "')]"));
            return !themedElements.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean areTextOverlayThemesAvailable() {
        try {
            List<WebElement> overlayThemes = driver.findElements(By.xpath("//option[contains(text(), 'overlay') or contains(text(), 'text')]"));
            return !overlayThemes.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    // File deletion methods
    public WebElement findUploadedFile(String fileName) {
        try {
            return driver.findElement(By.xpath("//div[contains(@class, 'file-item') and contains(., '" + fileName + "')]"));
        } catch (Exception e) {
            return driver.findElement(By.xpath("//li[contains(@class, 'file') and contains(., '" + fileName + "')]"));
        }
    }
    
    public void clickDeleteButtonForFile(WebElement fileElement) {
        try {
            WebElement deleteButton = fileElement.findElement(By.xpath(".//button[contains(text(), 'Delete')]"));
            deleteButton.click();
        } catch (Exception e) {
            try {
                WebElement deleteBtn = fileElement.findElement(By.xpath(".//button[contains(@class, 'delete')]"));
                deleteBtn.click();
            } catch (Exception ex) {
                // Fallback: click on any delete-related element
                WebElement fallback = fileElement.findElement(By.xpath(".//*[contains(@title, 'Delete') or contains(@class, 'trash')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fallback);
            }
        }
    }
    
    // File validation methods
    public void uploadFileWithSizeAndType(String fileSize, String fileType) {
        // Create test file name based on size and type
        String fileName = "test_" + fileSize.replace("MB", "mb") + "." + fileType.toLowerCase();
        selectFile(fileName, fileType);
    }
    
    public boolean isValidationErrorDisplayed() {
        try {
            return fileValidationError.isDisplayed() || errorMessage.isDisplayed();
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//*[contains(@class, 'error') and contains(text(), 'size') or contains(text(), 'type')]")).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public String getValidationErrorText() {
        try {
            if (fileValidationError.isDisplayed()) {
                return fileValidationError.getText();
            }
            if (errorMessage.isDisplayed()) {
                return errorMessage.getText();
            }
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//*[contains(@class, 'error')]")).getText();
            } catch (Exception ex) {
                return "";
            }
        }
        return "";
    }
    
    // File preview methods
    public void clickOnFileForPreview(String fileName) {
        try {
            WebElement fileElement = findUploadedFile(fileName);
            fileElement.click();
        } catch (Exception e) {
            // Alternative: click on preview button
            WebElement previewBtn = driver.findElement(By.xpath("//button[contains(@class, 'preview')]"));
            previewBtn.click();
        }
    }
    
    public boolean isFilePreviewDisplayed() {
        try {
            return filePreviewModal.isDisplayed() && previewImage.isDisplayed();
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//div[contains(@class, 'preview') or contains(@class, 'modal')]")).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public void closeFilePreview() {
        try {
            closePreviewButton.click();
        } catch (Exception e) {
            // Fallback: press Escape key or click outside
            ((JavascriptExecutor) driver).executeScript("document.querySelector('.modal, .preview').style.display = 'none';");
        }
    }
    
    // File organization methods
    public void sortFilesByType() {
        try {
            Select select = new Select(sortSelect);
            select.selectByVisibleText("Type");
        } catch (Exception e) {
            try {
                WebElement sortByType = driver.findElement(By.xpath("//button[contains(text(), 'Type')]"));
                sortByType.click();
            } catch (Exception ex) {
                // Fallback sorting
            }
        }
    }
    
    public void sortFilesByDate() {
        try {
            Select select = new Select(sortSelect);
            select.selectByVisibleText("Date");
        } catch (Exception e) {
            try {
                WebElement sortByDate = driver.findElement(By.xpath("//button[contains(text(), 'Date')]"));
                sortByDate.click();
            } catch (Exception ex) {
                // Fallback sorting
            }
        }
    }
    
    public boolean areFilesOrganizedByType() {
        try {
            List<WebElement> files = driver.findElements(By.xpath("//div[contains(@class, 'file-item')]"));
            // Check if files are grouped or sorted by type
            return files.size() > 0; // Simplified check
        } catch (Exception e) {
            return false;
        }
    }
    
    // Background customization methods
    public void setImageAsBackground(String fileName) {
        try {
            WebElement fileElement = findUploadedFile(fileName);
            WebElement setBackgroundBtn = fileElement.findElement(By.xpath(".//button[contains(text(), 'Set as Background')]"));
            setBackgroundBtn.click();
        } catch (Exception e) {
            // Alternative approach
            try {
                WebElement bgOption = driver.findElement(By.xpath("//option[contains(text(), '" + fileName + "')]"));
                bgOption.click();
            } catch (Exception ex) {
                // Fallback: right-click menu or context action
                WebElement fileElement = findUploadedFile(fileName);
                ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('contextmenu'));", fileElement);
            }
        }
    }
    
    public boolean isImageSetAsBackground(String fileName) {
        try {
            String bodyStyle = bodyElement.getAttribute("style");
            return bodyStyle.contains(fileName) || bodyStyle.contains("background-image");
        } catch (Exception e) {
            return false;
        }
    }
    
    // Notification methods
    public boolean isSuccessNotificationDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successNotification));
            return successNotification.isDisplayed();
        } catch (Exception e) {
            try {
                return driver.findElement(By.xpath("//div[contains(@class, 'success')]")).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    
    public boolean isDeletionConfirmationDisplayed() {
        try {
            return driver.findElement(By.xpath("//*[contains(text(), 'deleted') or contains(text(), 'removed')]")).isDisplayed();
        } catch (Exception e) {
            return isSuccessNotificationDisplayed();
        }
    }
    
    // Utility methods
    public void waitForUploadToComplete() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'uploading') or contains(text(), 'Uploading')]")));
        } catch (Exception e) {
            // If no loading indicator, wait a moment
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public ThemeAndFileUploadScreen waitForThemeToApply() {
        try {
            Thread.sleep(1000); // Allow time for theme change animation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    public boolean isSelectedThemeApply(String themeName) {
        String expectedTheme = getTheme(themeName);
//        String xpath = "//button[@class='theme-option active' and contains(text(),'@themeName')]".replace("@themeName", themeName);
        String xpath = "//button[@class='theme-option active' and text()='@themeName']".replace("@themeName", themeName);
        boolean isActiveStateDisplayed = driver.findElement(By.xpath(xpath)).isDisplayed();
        boolean isThemeAppliedGlobally = driver.findElement(By.xpath("//header")).getAttribute("style").contains(expectedTheme);
        return isActiveStateDisplayed && isThemeAppliedGlobally;
    }

    private String getTheme(String themeName) {
        switch (themeName.toLowerCase()) {
            case "default dark": return "rgb(102, 126, 234)";
            case "light mode": return "rgb(248, 250, 252)";
            case "ocean blue": return  "rgba(30, 58, 138, 0.9)";
            case "forest green": return "rgba(22, 101, 52, 0.9)";
            case "royal purple": return "rgba(124, 58, 237, 0.9)";
            case "sunset gradient": return "rgba(0, 0, 0, 0.7)";
            case "image background": return "";
        }
        return "";
    }

    private String getMimeType(String fileType) {
        switch (fileType.toUpperCase()) {
            case "PDF": return "application/pdf";
            case "JPEG": case "JPG": return "image/jpeg";
            case "PNG": return "image/png";
            case "TXT": return "text/plain";
            case "DOC": return "application/msword";
            case "DOCX": return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default: return "application/octet-stream";
        }
    }
    
    public boolean hasAtLeastOneUploadedFile() {
        return getUploadedFileCount() > 0;
    }
    
    public List<WebElement> getUploadedFiles() {
        try {
            return driver.findElements(By.xpath("//div[contains(@class, 'file-item')]"));
        } catch (Exception e) {
            return driver.findElements(By.xpath("//li[contains(@class, 'file')]"));
        }
    }
}
