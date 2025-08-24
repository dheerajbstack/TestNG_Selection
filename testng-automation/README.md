# TestNG Automation Framework

A comprehensive TestNG-based automation framework with Selenium WebDriver for web application testing.

## Features

- ✅ **Multi-Browser Support**: Chrome, Firefox, Edge, Safari
- ✅ **BrowserStack Integration**: Cloud testing on 3000+ browser/OS combinations
- ✅ **Headless Mode**: Run tests in headless mode for CI/CD
- ✅ **Page Object Model**: Well-structured page objects for maintainable tests
- ✅ **TestNG Integration**: Powerful testing framework with annotations and listeners
- ✅ **ExtentReports**: Beautiful HTML test reports
- ✅ **WebDriverManager**: Automatic driver management
- ✅ **Parallel Execution**: Run tests in parallel for faster execution
- ✅ **Configurable**: Environment-specific configurations
- ✅ **Logging**: Comprehensive logging with Logback
- ✅ **Maven Build**: Modern build system with Maven

## Project Structure

```
testng-automation/
├── src/
│   ├── main/java/com/automation/
│   │   ├── base/
│   │   │   └── BaseTest.java          # Base test class
│   │   ├── driver/
│   │   │   └── WebDriverFactory.java  # WebDriver factory
│   │   ├── listeners/
│   │   │   ├── TestListener.java      # TestNG listener
│   │   │   └── ExtentReportListener.java # ExtentReports listener
│   │   ├── pages/
│   │   │   ├── BasePage.java          # Base page object
│   │   │   ├── HomePage.java          # Home page object
│   │   │   └── ...                    # Other page objects
│   │   └── utils/
│   │       ├── ConfigReader.java      # Configuration reader
│   │       └── SeleniumUtils.java     # Selenium utilities
│   └── test/
│       ├── java/com/automation/tests/
│       │   ├── HomePageTest.java      # Home page tests
│       │   └── ProductsPageTest.java  # Products page tests
│       └── resources/
│           ├── config.properties      # Configuration file
│           ├── logback-test.xml       # Logging configuration
│           ├── testng.xml             # Main TestNG suite
│           ├── smoke-tests.xml        # Smoke test suite
│           └── regression-tests.xml   # Regression test suite
├── pom.xml                            # Maven build file
├── browserstack.yml                   # BrowserStack configuration
└── README.md                          # This file
```

## Prerequisites

- Java 11 or higher
- Google Chrome, Firefox, or Edge browser
- Internet connection (for downloading drivers automatically)

## Quick Start

### 1. Clone or Download the Project

```bash
# Navigate to the testng-automation directory
cd testng-automation
```

### 2. Run Tests

#### Run All Tests
```bash
./gradlew test
```

#### Run Smoke Tests Only
```bash
./gradlew test -Dsuite=src/test/resources/smoke-tests.xml
```

#### Run Regression Tests Only
```bash
./gradlew test -Dsuite=src/test/resources/regression-tests.xml
```

#### Run Tests with Different Browser
```bash
# Chrome (default)
./gradlew test -Dbrowser=chrome

# Firefox
./gradlew test -Dbrowser=firefox

# Edge
./gradlew test -Dbrowser=edge

# Safari (macOS only)
./gradlew test -Dbrowser=safari
```

#### Run Tests in Headless Mode
```bash
./gradlew test -Dheadless=true
```

#### Run Tests with Custom Base URL
```bash
./gradlew test -DbaseUrl=https://your-app-url.com
```

### 3. Gradle Tasks

```bash
# Clean build directory
./gradlew clean

# Compile source code
./gradlew compileJava

# Compile test code
./gradlew compileTestJava

# Run tests
./gradlew test

# Run Chrome tests specifically
./gradlew chromeTests

# Run Firefox tests specifically
./gradlew firefoxTests

# Run headless tests
./gradlew headlessTests

# Build project
./gradlew build
```

## BrowserStack Integration

This framework supports running tests on BrowserStack cloud platform for cross-browser testing across multiple operating systems and browser versions.

### Setup BrowserStack

1. **Get BrowserStack Account**: Sign up at [BrowserStack](https://www.browserstack.com/)

2. **Configure Credentials**: Update `browserstack.yml` with your credentials:
```yaml
userName: YOUR_USERNAME
accessKey: YOUR_ACCESS_KEY
```

3. **Alternative Credential Setup**: Set environment variables:
```bash
export BROWSERSTACK_USERNAME=your_username
export BROWSERSTACK_ACCESS_KEY=your_access_key
```

### BrowserStack Configuration

The `browserstack.yml` file contains:
- **Platforms**: Define OS/browser combinations
- **Capabilities**: Set screen resolution, debugging options
- **Local Testing**: Configure for internal applications
- **Parallel Execution**: Control concurrent test sessions

### Running BrowserStack Tests

#### Run on BrowserStack Cloud
```bash
# Run cross-browser tests on BrowserStack cloud
mvn test -P browserstack-test

# Run with specific browser/platform
mvn test -P browserstack-test -Dbrowser=chrome -Dplatform="Windows 11"
```

#### Run Local Tests on BrowserStack
```bash
# Run tests against local/internal applications
mvn test -P browserstack-local-test

# Enable BrowserStack Local tunnel
mvn test -P browserstack-local-test -Dlocal=true
```

#### BrowserStack with Custom Parameters
```bash
# Run with specific BrowserStack capabilities
mvn test -P browserstack-test \
  -Dbrowser=firefox \
  -Dplatform="OS X Monterey" \
  -DbrowserVersion=latest \
  -Dresolution=1920x1080
```

### BrowserStack Test Suites

- **browserstack-test.xml**: Cross-platform tests on BrowserStack cloud
- **browserstack-local-test.xml**: Local testing with BrowserStack tunnel

### Viewing BrowserStack Results

- **BrowserStack Dashboard**: View test results at [BrowserStack Automate](https://automate.browserstack.com/)
- **Video Recordings**: Automatic video capture of test sessions
- **Logs**: Browser console logs and network logs
- **Screenshots**: Automatic screenshots on test failures

### BrowserStack Features

- ✅ **Cross-Browser Testing**: 3000+ browser/OS combinations
- ✅ **Real Devices**: Test on real mobile devices
- ✅ **Local Testing**: Test internal/staging applications
- ✅ **Parallel Execution**: Run multiple tests simultaneously
- ✅ **Debugging Tools**: Screenshots, videos, console logs
- ✅ **CI/CD Integration**: Jenkins, GitHub Actions, etc.

## Configuration

### Environment Configuration

Update `src/test/resources/config.properties` for environment-specific settings:

```properties
# Application Configuration
base.url=http://localhost:3000
app.title=Test Application

# Browser Configuration
browser=chrome
headless=false

# Timeouts (in seconds)
implicit.wait=10
explicit.wait=10
page.load.timeout=30
```

### TestNG Suite Configuration

Modify TestNG XML files for different test configurations:

- `testng.xml` - Main test suite with parallel execution
- `smoke-tests.xml` - Quick smoke tests
- `regression-tests.xml` - Full regression suite

## Test Reports

After running tests, reports are generated in:

- **TestNG Reports**: `build/reports/tests/test/index.html`
- **ExtentReports**: `build/reports/extent-report-[timestamp].html`

## Logging

Logs are written to:
- **Console**: Real-time log output
- **File**: `build/logs/automation.log`
- **Test Execution**: `build/logs/test-execution.log`

## Writing New Tests

### 1. Create Page Object

```java
package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewPage extends BasePage {
    
    private final By pageElement = By.id("page-element");
    
    public NewPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isPageLoaded() {
        return seleniumUtils.isElementDisplayed(pageElement);
    }
}
```

### 2. Create Test Class

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.NewPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewPageTest extends BaseTest {
    
    @Test(description = "Test new page functionality")
    public void testNewPageLoad() {
        NewPage newPage = new NewPage(driver);
        Assert.assertTrue(newPage.isPageLoaded(), "New page should be loaded");
    }
}
```

### 3. Add to TestNG Suite

Update `testng.xml` to include your new test class:

```xml
<classes>
    <class name="com.automation.tests.NewPageTest"/>
</classes>
```

## Best Practices

1. **Use Page Object Model**: Create page objects for better maintainability
2. **Descriptive Test Names**: Use clear, descriptive test method names
3. **Assertions**: Always include meaningful assertions
4. **Logging**: Add appropriate logging for debugging
5. **Wait Strategies**: Use explicit waits instead of Thread.sleep()
6. **Data-Driven Tests**: Use TestNG data providers for data-driven testing
7. **Test Independence**: Ensure tests can run independently

## Troubleshooting

### Common Issues

1. **WebDriver Issues**: 
   - Ensure browsers are installed
   - WebDriverManager handles drivers automatically

2. **Java Issues**:
   - Verify Java 11+ is installed
   - Check JAVA_HOME environment variable

3. **Port Issues**:
   - Ensure application is running on configured port
   - Update base.url in config.properties

4. **Permission Issues**:
   - Make gradlew executable: `chmod +x gradlew`

### Debug Mode

Run tests with debug logging:

```bash
./gradlew test --debug
```

## CI/CD Integration

For CI/CD pipelines, use headless mode:

```bash
./gradlew test -Dheadless=true -Dbrowser=chrome
```

## Contributing

1. Follow existing code structure and naming conventions
2. Add appropriate tests for new functionality
3. Update documentation as needed
4. Use proper logging and error handling

## License

This project is for educational and testing purposes.

---

For more information or issues, please check the logs in `build/logs/` directory.
