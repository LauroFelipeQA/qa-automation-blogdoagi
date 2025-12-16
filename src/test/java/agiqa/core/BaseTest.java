
package agiqa.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = System.getProperty("baseUrl", "https://blogdoagi.com.br/");
        driver = DriverFactory.create();
        driver.manage().window().setSize(new Dimension(1366, 900));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
