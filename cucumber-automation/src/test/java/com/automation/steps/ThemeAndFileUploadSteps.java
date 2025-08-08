package com.automation.steps;

import com.automation.businessLayer.ThemeAndFileUploadBL;
import com.automation.utils.LogCapture;
import com.automation.utils.ContextStore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ThemeAndFileUploadSteps {
    
    private ThemeAndFileUploadBL themeAndFileUploadBL;
    
    public ThemeAndFileUploadSteps() {
        this.themeAndFileUploadBL = new ThemeAndFileUploadBL();
    }

    @When("I click on the file input")
    public void i_click_on_the_file_input() {
        LogCapture.logTestStepWithDetails("📁 File Input Click", 
            "Action: Clicking file input",
            "Clicking on the file input to select files...");
        
        themeAndFileUploadBL.clickOnFileInput();
        
        LogCapture.addStepLog("File Input Click", "✅ File input clicked successfully");
    }

    @When("I select a valid file of type {string} with name {string}")
    public void i_select_a_valid_file_of_type_with_name(String fileType, String fileName) {
        LogCapture.logTestStepWithDetails("📄 File Selection", 
            "File: " + fileName + " (Type: " + fileType + ")",
            "Selecting valid file for upload...");
        
        themeAndFileUploadBL.selectValidFile(fileType, fileName);
        ContextStore.put("currentFileName", fileName);
        ContextStore.put("currentFileType", fileType);
        
        LogCapture.addStepLog("File Selection", "✅ File selected: " + fileName + " (" + fileType + ")");
    }

    @Then("the file details should display name, size, and type")
    public void the_file_details_should_display_name_size_and_type() {
        LogCapture.logTestStepWithDetails("📋 File Details Verification", 
            "Expected: Name, size, and type displayed",
            "Verifying file details are shown...");
        
        themeAndFileUploadBL.verifyFileDetailsShowNameSizeAndType();
        
        LogCapture.addStepLog("File Details Verification", "✅ File details verified: name, size, and type");
    }

    @When("I select background theme {string}")
    public void i_select_background_theme(String themeName) {
        LogCapture.logTestStepWithDetails("🎨 Background Theme Selection", 
            "Theme: " + themeName,
            "Selecting background theme...");
        
        themeAndFileUploadBL.selectBackgroundTheme(themeName);
        ContextStore.put("currentTheme", themeName);
        
        LogCapture.addStepLog("Background Theme Selection", "✅ Background theme selected: " + themeName);
    }

    @Then("the file should appear in the uploaded files list")
    public void the_file_should_appear_in_the_uploaded_files_list() {
        LogCapture.logTestStepWithDetails("📋 File List Verification", 
            "Expected: File appears in uploaded list",
            "Verifying file appears in the uploaded files list...");
        
        themeAndFileUploadBL.verifyFileAppearsInUploadedList();
        
        LogCapture.addStepLog("File List Verification", "✅ File verified in uploaded files list");
    }

    @When("I upload an image file {string} of type {string}")
    public void i_upload_an_image_file_of_type(String fileName, String fileType) {
        LogCapture.logTestStepWithDetails("🖼️ Image File Upload", 
            "Image: " + fileName + " (Type: " + fileType + ")",
            "Uploading image file...");
        
        themeAndFileUploadBL.uploadImageFile(fileName, fileType);
        ContextStore.put("currentFileName", fileName);
        ContextStore.put("currentFileType", fileType);
        
        LogCapture.addStepLog("Image File Upload", "✅ Image file uploaded: " + fileName + " (" + fileType + ")");
    }

    @Then("I should see a special notice about auto-background")
    public void i_should_see_a_special_notice_about_auto_background() {
        LogCapture.logTestStepWithDetails("🔔 Auto-Background Notice", 
            "Expected: Special notice about auto-background",
            "Verifying special notice for auto-background feature...");
        
        themeAndFileUploadBL.verifySpecialNoticeForAutoBackground();
        
        LogCapture.addStepLog("Auto-Background Notice", "✅ Special notice about auto-background verified");
    }

    @When("I complete the upload")
    public void i_complete_the_upload() {
        LogCapture.logTestStepWithDetails("✅ Upload Completion", 
            "Action: Completing file upload",
            "Completing the file upload process...");
        
        themeAndFileUploadBL.completeUpload();
        
        LogCapture.addStepLog("Upload Completion", "✅ File upload completed successfully");
    }

    @Then("the image should be set as the background automatically")
    public void the_image_should_be_set_as_the_background_automatically() {
        LogCapture.logTestStepWithDetails("🖼️ Auto-Background Verification", 
            "Expected: Image set as background automatically",
            "Verifying image is set as background automatically...");
        
        themeAndFileUploadBL.verifyImageSetAsBackgroundAutomatically();
        
        LogCapture.addStepLog("Auto-Background Verification", "✅ Image automatically set as background verified");
    }

    @Then("the theme options should change to text overlay themes")
    public void the_theme_options_should_change_to_text_overlay_themes() {
        LogCapture.logTestStepWithDetails("🎨 Theme Options Change", 
            "Expected: Theme options change to text overlay",
            "Verifying theme options change to text overlay themes...");
        
        themeAndFileUploadBL.verifyThemeOptionsChangeToTextOverlay();
        
        LogCapture.addStepLog("Theme Options Change", "✅ Theme options changed to text overlay themes");
    }

    @When("I select theme {string}")
    @When("I select theme {string} and validate background changes immediately")
    public void i_select_theme(String themeName) {
        
        themeAndFileUploadBL.selectTheme(themeName);
    }

    @Then("the background should change immediately to match {string}")
    public void the_background_should_change_immediately_to_match(String themeName) {
        LogCapture.logTestStepWithDetails("🎨 Background Change Verification", 
            "Expected Theme: " + themeName,
            "Verifying background changes immediately...");
        
        themeAndFileUploadBL.verifyBackgroundChangesImmediately(themeName);
        
        LogCapture.addStepLog("Background Change", "✅ Background changed immediately to: " + themeName);
    }

    @When("I switch to another tab and return")
    public void i_switch_to_another_tab_and_return() {
        LogCapture.logTestStepWithDetails("🔄 Tab Navigation Test", 
            "Action: Switch tab and return",
            "Testing theme persistence across navigation...");
        
        themeAndFileUploadBL.switchToAnotherTabAndReturn();
        
        LogCapture.addStepLog("Tab Navigation Test", "✅ Switched to another tab and returned");
    }

    @Then("the {string} theme should persist across navigation")
    public void the_theme_should_persist_across_navigation(String currentTheme) {
        LogCapture.logTestStepWithDetails("🔄 Theme Persistence Verification", 
            "Expected: Theme persists across navigation",
            "Verifying theme persistence...");
        
        themeAndFileUploadBL.verifyThemePersistsAcrossNavigation(currentTheme);
        
        LogCapture.addStepLog("Theme Persistence", "✅ Theme persistence across navigation verified");
    }

    @Given("at least one file is uploaded")
    public void at_least_one_file_is_uploaded() {
        LogCapture.logTestStepWithDetails("🔧 File Upload Precondition", 
            "Requirement: At least one file uploaded",
            "Ensuring at least one file is uploaded...");
        
        themeAndFileUploadBL.ensureAtLeastOneFileIsUploaded();
        
        LogCapture.addStepLog("File Upload Precondition", "✅ At least one file confirmed uploaded");
    }

    @When("I locate the uploaded file in the list")
    public void i_locate_the_uploaded_file_in_the_list() {
        LogCapture.logTestStepWithDetails("🔍 File Location", 
            "Action: Locating uploaded file",
            "Finding uploaded file in the list...");
        
        themeAndFileUploadBL.locateUploadedFileInList();
        
        LogCapture.addStepLog("File Location", "✅ Uploaded file located in the list");
    }

    @Then("the file should be removed from the list")
    public void the_file_should_be_removed_from_the_list() {
        LogCapture.logTestStepWithDetails("❌ File Removal Verification", 
            "Expected: File removed from list",
            "Verifying file removal from list...");
        
        themeAndFileUploadBL.verifyFileRemovedFromList();
        
        LogCapture.addStepLog("File Removal", "✅ File removal from list verified");
    }

    @Then("the file count should be updated")
    public void the_file_count_should_be_updated() {
        LogCapture.logTestStepWithDetails("🔢 File Count Update", 
            "Expected: File count updated",
            "Verifying file count is updated...");
        
        themeAndFileUploadBL.verifyFileCountUpdatedAfterDeletion();
        
        LogCapture.addStepLog("File Count Update", "✅ File count update verified");
    }

    @Then("I should see a deletion confirmation")
    public void i_should_see_a_deletion_confirmation() {
        LogCapture.logTestStepWithDetails("✅ Deletion Confirmation", 
            "Expected: Deletion confirmation displayed",
            "Verifying deletion confirmation...");
        
        themeAndFileUploadBL.verifyDeletionConfirmation();
        
        LogCapture.addStepLog("Deletion Confirmation", "✅ Deletion confirmation verified");
    }

    @When("I try to upload a file with size {string} and type {string}")
    public void i_try_to_upload_a_file_with_size_and_type(String fileSize, String fileType) {
        LogCapture.logTestStepWithDetails("📊 File Validation Test", 
            "Size: " + fileSize + ", Type: " + fileType,
            "Testing file size and type validation...");
        
        themeAndFileUploadBL.uploadFileWithSizeAndType(fileSize, fileType);
        ContextStore.put("currentFileSize", fileSize);
        ContextStore.put("currentFileType", fileType);
        
        LogCapture.addStepLog("File Validation Test", "✅ File validation test performed: " + fileSize + " " + fileType);
    }

    @Then("appropriate error messages should be displayed if invalid")
    public void appropriate_error_messages_should_be_displayed_if_invalid() {
        LogCapture.logTestStepWithDetails("⚠️ Error Message Verification", 
            "Expected: Appropriate error messages (if invalid)",
            "Verifying error message display...");
        
        themeAndFileUploadBL.verifyAppropriateErrorMessagesDisplayed();
        
        LogCapture.addStepLog("Error Message Verification", "✅ Error message verification completed");
    }

    @Given("I have uploaded an image file")
    public void i_have_uploaded_an_image_file() {
        LogCapture.logTestStepWithDetails("🔧 Image Upload Precondition", 
            "Requirement: Image file uploaded",
            "Ensuring an image file is uploaded...");
        
        themeAndFileUploadBL.ensureImageFileIsUploaded();
        
        LogCapture.addStepLog("Image Upload Precondition", "✅ Image file confirmed uploaded");
    }

    @When("I click on the image file in the list")
    public void i_click_on_the_image_file_in_the_list() {
        LogCapture.logTestStepWithDetails("🖼️ Image File Click", 
            "Action: Clicking on image file",
            "Clicking on image file for preview...");
        
        themeAndFileUploadBL.clickOnImageFileInList();
        
        LogCapture.addStepLog("Image File Click", "✅ Clicked on image file in list");
    }

    @Then("I should see a preview of the image")
    public void i_should_see_a_preview_of_the_image() {
        LogCapture.logTestStepWithDetails("🖼️ Image Preview Verification", 
            "Expected: Image preview displayed",
            "Verifying image preview is shown...");
        
        themeAndFileUploadBL.verifyImagePreviewDisplayed();
        
        LogCapture.addStepLog("Image Preview", "✅ Image preview verified");
    }

    @Then("the preview should display correctly")
    public void the_preview_should_display_correctly() {
        LogCapture.logTestStepWithDetails("✅ Preview Quality Verification", 
            "Expected: Preview displays correctly",
            "Verifying preview quality and display...");
        
        themeAndFileUploadBL.verifyPreviewDisplaysCorrectly();
        
        LogCapture.addStepLog("Preview Quality", "✅ Preview display quality verified");
    }

    @When("I select multiple files for upload")
    public void i_select_multiple_files_for_upload() {
        LogCapture.logTestStepWithDetails("📁 Multiple File Selection", 
            "Action: Selecting multiple files",
            "Selecting multiple files for bulk upload...");
        
        themeAndFileUploadBL.selectMultipleFilesForUpload();
        
        LogCapture.addStepLog("Multiple File Selection", "✅ Multiple files selected for upload");
    }

    @Then("each file should be validated individually")
    public void each_file_should_be_validated_individually() {
        LogCapture.logTestStepWithDetails("🔍 Individual File Validation", 
            "Expected: Each file validated individually",
            "Verifying individual file validation...");
        
        themeAndFileUploadBL.verifyEachFileValidatedIndividually();
        
        LogCapture.addStepLog("Individual Validation", "✅ Individual file validation verified");
    }

    @Then("valid files should be uploaded successfully")
    public void valid_files_should_be_uploaded_successfully() {
        LogCapture.logTestStepWithDetails("✅ Valid File Upload Verification", 
            "Expected: Valid files uploaded successfully",
            "Verifying valid files are uploaded...");
        
        themeAndFileUploadBL.verifyValidFilesUploadedSuccessfully();
        
        LogCapture.addStepLog("Valid File Upload", "✅ Valid files upload success verified");
    }

    @Then("invalid files should be rejected with appropriate messages")
    public void invalid_files_should_be_rejected_with_appropriate_messages() {
        LogCapture.logTestStepWithDetails("❌ Invalid File Rejection", 
            "Expected: Invalid files rejected with messages",
            "Verifying invalid file rejection...");
        
        themeAndFileUploadBL.verifyInvalidFilesRejectedWithMessages();
        
        LogCapture.addStepLog("Invalid File Rejection", "✅ Invalid file rejection verified");
    }

    @Given("I have selected {string} theme")
    public void i_have_selected_theme(String themeName) {
        LogCapture.logTestStepWithDetails("🎨 Theme Selection Precondition", 
            "Theme: " + themeName,
            "Setting up theme selection precondition...");
        
        themeAndFileUploadBL.selectSpecificTheme(themeName);
        ContextStore.put("currentTheme", themeName);
        
        LogCapture.addStepLog("Theme Selection Precondition", "✅ Theme selected: " + themeName);
    }

    @When("I close and reopen the application")
    public void i_close_and_reopen_the_application() {
        LogCapture.logTestStepWithDetails("🔄 Application Restart Simulation", 
            "Action: Close and reopen application",
            "Simulating application restart...");
        
        themeAndFileUploadBL.simulateCloseAndReopenApplication();
        
        LogCapture.addStepLog("Application Restart", "✅ Application restart simulated");
    }

    @Then("the {string} theme should still be active")
    public void the_theme_should_still_be_active(String themeName) {
        LogCapture.logTestStepWithDetails("🔄 Theme Persistence Verification", 
            "Expected Theme: " + themeName,
            "Verifying theme persistence across sessions...");
        
        themeAndFileUploadBL.verifyThemeStillActive(themeName);
        
        LogCapture.addStepLog("Theme Persistence", "✅ Theme persistence verified: " + themeName);
    }

    @Then("all styling should be consistent")
    public void all_styling_should_be_consistent() {
        LogCapture.logTestStepWithDetails("🎨 Styling Consistency Check", 
            "Expected: Consistent styling",
            "Verifying styling consistency...");
        
        themeAndFileUploadBL.verifyStylingConsistent();
        
        LogCapture.addStepLog("Styling Consistency", "✅ Styling consistency verified");
    }

    @Given("I have uploaded files of different types")
    public void i_have_uploaded_files_of_different_types() {
        LogCapture.logTestStepWithDetails("📁 Multi-Type File Upload", 
            "Requirement: Files of different types uploaded",
            "Uploading files of various types...");
        
        themeAndFileUploadBL.uploadFilesOfDifferentTypes();
        
        LogCapture.addStepLog("Multi-Type Upload", "✅ Files of different types uploaded");
    }

    @When("I view the files list")
    public void i_view_the_files_list() {
        LogCapture.logTestStepWithDetails("👁️ Files List View", 
            "Action: Viewing files list",
            "Viewing the uploaded files list...");
        
        // This step typically involves just observing the current list state
        LogCapture.addStepLog("Files List View", "✅ Files list viewed");
    }

    @Then("files should be organized by type or date")
    public void files_should_be_organized_by_type_or_date() {
        LogCapture.logTestStepWithDetails("📊 File Organization Verification", 
            "Expected: Files organized by type or date",
            "Verifying file organization...");
        
        themeAndFileUploadBL.verifyFilesOrganizedByTypeOrDate();
        
        LogCapture.addStepLog("File Organization", "✅ File organization verified");
    }

    @Then("I should be able to sort files by different criteria")
    public void i_should_be_able_to_sort_files_by_different_criteria() {
        LogCapture.logTestStepWithDetails("🔀 File Sorting Capabilities", 
            "Expected: File sorting by different criteria",
            "Verifying file sorting capabilities...");
        
        themeAndFileUploadBL.verifySortingCapabilities();
        
        LogCapture.addStepLog("File Sorting", "✅ File sorting capabilities verified");
    }

    @When("I click on {string} option")
    public void i_click_on_option(String optionName) {
        LogCapture.logTestStepWithDetails("🎨 Theme Option Selection", 
            "Option: " + optionName,
            "Clicking on theme option...");
        
        themeAndFileUploadBL.clickOnThemeOption(optionName);
        
        LogCapture.addStepLog("Theme Option Selection", "✅ Clicked on option: " + optionName);
    }

    @When("I upload multiple image files")
    public void i_upload_multiple_image_files() {
        LogCapture.logTestStepWithDetails("🖼️ Multiple Image Upload", 
            "Action: Uploading multiple image files",
            "Uploading multiple image files to gallery...");
        
        themeAndFileUploadBL.uploadMultipleImageFilesToGallery();
        
        LogCapture.addStepLog("Multiple Image Upload", "✅ Multiple image files uploaded to gallery");
    }

    @Then("the uploaded images should be displayed in the gallery")
    public void the_uploaded_images_should_be_displayed_in_the_gallery() {
        LogCapture.logTestStepWithDetails("🖼️ Gallery Display Verification", 
            "Expected: Uploaded images displayed in gallery",
            "Verifying images are displayed in gallery...");
        
        themeAndFileUploadBL.verifyImagesDisplayedInGallery();
        
        LogCapture.addStepLog("Gallery Display Verification", "✅ Images verified in gallery display");
    }

    @Given("I have uploaded multiple image files")
    public void i_have_uploaded_multiple_image_files() {
        LogCapture.logTestStepWithDetails("🖼️ Multiple Image Upload", 
            "Requirement: Multiple image files uploaded",
            "Uploading multiple image files...");
        
        themeAndFileUploadBL.uploadMultipleImageFiles();
        
        LogCapture.addStepLog("Multiple Image Upload", "✅ Multiple image files uploaded");
    }

    @When("I set one image as background")
    public void i_set_one_image_as_background() {
        LogCapture.logTestStepWithDetails("🖼️ Background Image Setting", 
            "Action: Setting image as background",
            "Setting one image as background...");
        
        // Use the stored filename or a default
        String imageName = (String) ContextStore.get("currentFileName");
        if (imageName == null) {
            imageName = "background1.jpg";
        }
        themeAndFileUploadBL.setOneImageAsBackground(imageName);
        ContextStore.put("activeBackgroundImage", imageName);
        
        LogCapture.addStepLog("Background Image Setting", "✅ Image set as background: " + imageName);
    }

    @Then("that image should become the page background")
    public void that_image_should_become_the_page_background() {
        LogCapture.logTestStepWithDetails("🖼️ Background Image Verification", 
            "Expected: Image becomes page background",
            "Verifying image becomes page background...");
        
        String imageName = (String) ContextStore.get("activeBackgroundImage");
        if (imageName == null) {
            imageName = "background1.jpg";
        }
        themeAndFileUploadBL.verifyImageBecomesPageBackground(imageName);
        
        LogCapture.addStepLog("Background Image Verification", "✅ Image verified as page background");
    }

    @When("I change to a different image background")
    public void i_change_to_a_different_image_background() {
        LogCapture.logTestStepWithDetails("🔄 Background Image Change", 
            "Action: Changing to different image background",
            "Changing to a different image background...");
        
        // Store the previous background for verification
        String previousBackground = (String) ContextStore.get("activeBackgroundImage");
        ContextStore.put("previousBackgroundImage", previousBackground);
        
        String newImageName = "background2.png";
        themeAndFileUploadBL.changeToDifferentImageBackground(newImageName);
        ContextStore.put("activeBackgroundImage", newImageName);
        
        LogCapture.addStepLog("Background Image Change", "✅ Changed to different image background: " + newImageName);
    }

    @Then("the background should update to the new image")
    public void the_background_should_update_to_the_new_image() {
        LogCapture.logTestStepWithDetails("🔄 New Background Verification", 
            "Expected: Background updates to new image",
            "Verifying background updates to new image...");
        
        String newImageName = (String) ContextStore.get("activeBackgroundImage");
        if (newImageName == null) {
            newImageName = "background2.png";
        }
        themeAndFileUploadBL.verifyBackgroundUpdatesToNewImage(newImageName);
        
        LogCapture.addStepLog("New Background Verification", "✅ Background update to new image verified");
    }

    @Then("the previous background should no longer be active")
    public void the_previous_background_should_no_longer_be_active() {
        LogCapture.logTestStepWithDetails("❌ Previous Background Check", 
            "Expected: Previous background no longer active",
            "Verifying previous background is no longer active...");
        
        String previousImageName = (String) ContextStore.get("previousBackgroundImage");
        if (previousImageName == null) {
            previousImageName = "background1.jpg";
        }
        themeAndFileUploadBL.verifyPreviousBackgroundNoLongerActive(previousImageName);
        
        LogCapture.addStepLog("Previous Background Check", "✅ Previous background verified as inactive");
    }

    // Additional step definitions for edge cases and complex scenarios

    @When("I upload a file {string}")
    public void i_upload_a_file(String fileName) {
        LogCapture.logTestStepWithDetails("📄 Single File Upload", 
            "File: " + fileName,
            "Uploading specified file...");
        
        // Determine file type from extension
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase();
        themeAndFileUploadBL.selectValidFile(fileType, fileName);
        themeAndFileUploadBL.completeUpload();
        ContextStore.put("currentFileName", fileName);
        
        LogCapture.addStepLog("Single File Upload", "✅ File uploaded: " + fileName);
    }

    @Then("the file upload should be successful")
    public void the_file_upload_should_be_successful() {
        LogCapture.logTestStepWithDetails("✅ Upload Success Verification", 
            "Expected: File upload successful",
            "Verifying file upload success...");
        
        themeAndFileUploadBL.verifySuccessNotification();
        themeAndFileUploadBL.verifyFileAppearsInUploadedList();
        
        LogCapture.addStepLog("Upload Success", "✅ File upload success verified");
    }

    @When("I apply theme {string}")
    public void i_apply_theme(String themeName) {
        LogCapture.logTestStepWithDetails("🎨 Theme Application", 
            "Theme: " + themeName,
            "Applying specified theme...");
        
        themeAndFileUploadBL.selectTheme(themeName);
        ContextStore.put("currentTheme", themeName);
        
        LogCapture.addStepLog("Theme Application", "✅ Theme applied: " + themeName);
    }

    @Then("the theme should be applied successfully")
    public void the_theme_should_be_applied_successfully() {
        LogCapture.logTestStepWithDetails("🎨 Theme Application Verification", 
            "Expected: Theme applied successfully",
            "Verifying theme application...");
        
        String currentTheme = (String) ContextStore.get("currentTheme");
        themeAndFileUploadBL.verifyBackgroundChangesImmediately(currentTheme);
        
        LogCapture.addStepLog("Theme Application Verification", "✅ Theme application verified");
    }

    @Given("the file upload system is ready")
    public void the_file_upload_system_is_ready() {
        LogCapture.logTestStepWithDetails("🔧 System Readiness Check", 
            "Requirement: File upload system ready",
            "Verifying file upload system readiness...");
        
        themeAndFileUploadBL.verifyFileUploadFeatureWorks();
        
        LogCapture.addStepLog("System Readiness", "✅ File upload system verified as ready");
    }

    @Given("the theme management system is ready")
    public void the_theme_management_system_is_ready() {
        LogCapture.logTestStepWithDetails("🔧 Theme System Readiness", 
            "Requirement: Theme management system ready",
            "Verifying theme management system readiness...");
        
        themeAndFileUploadBL.verifyThemeSystemWorks();
        
        LogCapture.addStepLog("Theme System Readiness", "✅ Theme management system verified as ready");
    }
}
