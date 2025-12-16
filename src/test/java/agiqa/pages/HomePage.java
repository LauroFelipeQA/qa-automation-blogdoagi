package agiqa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Seletor do cabeçalho da página
    private final By header = By.tagName("header");

    // Seletor do formulário de busca
    private final By searchForm = By.cssSelector("form.search-form");

    // Seletor do campo de input da busca
    private final By searchInput = By.cssSelector("form.search-form input[name='s']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * Abre a página inicial e aguarda o carregamento do cabeçalho.
     */
    public HomePage open(String baseUrl) {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
        return this;
    }

    /**
     * Aguarda o formulário de busca estar presente na página.
     */
    public HomePage openSearch() {
        wait.until(ExpectedConditions.presenceOfElementLocated(searchForm));
        return this;
    }

    /**
     * Realiza a busca pelo termo informado, preenchendo o campo e submetendo o formulário via JavaScript.
     */
    public void search(String term) {
        WebElement input = wait.until(
                ExpectedConditions.presenceOfElementLocated(searchInput)
        );
        //usei JS para submeter o form, pois o submit() do Selenium não funcionou estavel
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].value = arguments[1]; arguments[0].form.submit();",
                input,
                term
        );
    }
}
