
package agiqa.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver create() {
        final String browser = System.getProperty("browser", "chrome");
        final boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        switch (browser.toLowerCase()) {
            case "chrome":
            default:
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                options.addArguments("--window-size=1366,900");
                options.addArguments("--lang=pt-BR");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(options);
        }
    }
}