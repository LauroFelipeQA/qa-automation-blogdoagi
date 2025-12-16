package agiqa.tests;

import agiqa.core.BaseTest;
import agiqa.pages.HomePage;
import agiqa.pages.SearchResultsPage;
import agiqa.utils.Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTests extends BaseTest {

    @Test
    @DisplayName("Cenário 1: Busca com resultados e abertura do primeiro artigo")
    void searchWithResultsAndOpenFirst() throws InterruptedException {
        // Preparação: define o termo de busca existente
        String searchTerm = Config.get("search.term.existing", "atestado");

        // Execução: realiza a busca no site
        new HomePage(driver)
                .open(baseUrl)
                .openSearch()
                .search(searchTerm);

        SearchResultsPage results = new SearchResultsPage(driver);
        results.waitForResults();

        // Verificação: garante que há resultados e o termo está presente no título
        assertThat(results.getResultsCount())
                .as("Deve retornar ao menos um resultado")
                .isGreaterThan(0);

        assertThat(results.getFirstResultTitleText().toLowerCase())
                .as("O título do primeiro resultado deve conter o termo buscado")
                .contains(searchTerm.toLowerCase());

        // Ação extra: abre o primeiro resultado
        results.openFirstResult();
    }

    @Test
    @DisplayName("Cenário 2: Busca sem resultados exibe mensagem clara")
    void searchWithoutResultsShowsMessage() {
        // Preparação: define o termo de busca inexistente
        String searchTerm = Config.get("search.term.nonexistent", "laurotesteqa");

        // Execução: realiza a busca no site
        new HomePage(driver)
                .open(baseUrl)
                .openSearch()
                .search(searchTerm);

        SearchResultsPage results = new SearchResultsPage(driver);
        String expectedMsg = "Lamentamos, mas nada foi encontrado para sua pesquisa, tente novamente com outras palavras.";
        String actualMsg = results.hasNoResultsMessage();

        // Verificação: garante que a mensagem de nenhum resultado é exibida corretamente
        assertThat(actualMsg)
                .as("Mensagem de nenhum resultado deve ser exibida corretamente")
                .isEqualTo(expectedMsg);
    }
}
