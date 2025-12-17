package agiqa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

/**
 * Página de resultados de busca.
 * Fornece métodos para interação e verificação dos resultados apresentados.
 */
public class SearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Seletor dos itens de resultado da busca
    private final By resultItems = By.cssSelector(
            "article.post, .search-results .post, .post-list .post, .cards .card, .posts-list article"
    );
    // Seletor do título do primeiro item
    private final By firstItemTitle = By.cssSelector(
            "article.post h2, .entry-title, h2.entry-title"
    );
    // Seletor da mensagem de nenhum resultado encontrado
    private final By noResultsMsg = By.cssSelector(".no-results, .search-no-results");
    // Seletor de mensagens exibidas na página
    private final By msgSelector = By.cssSelector("#main > section > div > p");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * Aguarda até que os resultados da busca estejam visíveis ou que a mensagem de nenhum resultado apareça.
     */
    public void waitForResults() throws InterruptedException {
        Thread.sleep(1000); // Pequena pausa para garantir que a página começou a carregar os resultados
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0, 1500);");

        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class)
                .until(d -> {
                    List<WebElement> items = d.findElements(resultItems);
                    if (!items.isEmpty() && items.get(0).isDisplayed()) return true;
                    List<WebElement> msgs = d.findElements(noResultsMsg);
                    return msgs.stream().anyMatch(e -> {
                        String t = e.getText().toLowerCase();
                        return t.contains("nenhum resultado") || t.contains("não foram encontrados");
                    });
                });
    }

    /**
     * Retorna a quantidade de resultados encontrados.
     */
    public int getResultsCount() {
        return driver.findElements(resultItems).size();
    }

    /**
     * Retorna o texto do título do primeiro resultado.
     */
    public String getFirstResultTitleText() {
        List<WebElement> titles = driver.findElements(firstItemTitle);
        if (!titles.isEmpty()) {
            return titles.get(0).getText();
        }
        return "";
    }

    /**
     * Abre o primeiro resultado da lista.
     * Aguarda o carregamento do artigo após o clique.
     */
    public void openFirstResult() {
        List<WebElement> items = driver.findElements(resultItems);
        if (!items.isEmpty()) {
            WebElement first = items.get(0);
            WebElement link = null;
            try {
                link = first.findElement(By.cssSelector("a"));
            } catch (NoSuchElementException ignored) {}
            if (link != null) {
                link.click();
            } else {
                first.click();
            }
        }
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("article h1")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".entry-title, .post-title"))
        ));
    }

    /**
     * Retorna a mensagem exibida quando não há resultados.
     */
    public String hasNoResultsMessage() {
        List<WebElement> noResults = driver.findElements(noResultsMsg);
        if (!noResults.isEmpty()) {
            List<WebElement> msgs = driver.findElements(msgSelector);
            if (!msgs.isEmpty()) {
                return msgs.get(0).getText();
            }
        }
        return "";
    }
}
