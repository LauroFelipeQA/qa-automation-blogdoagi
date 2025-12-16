# QA Automation – Blog do Agi

Automação de testes da funcionalidade de **busca do Blog do Agi**, desenvolvida em **Java** com **Selenium**, **JUnit 5** e **Gradle**.

O projeto cobre:

-   Busca com resultados

-   Busca sem resultados

-   Validação opcional para submissão vazia

-   Execução local

-   Integração contínua via **GitHub Actions**


----------

## 🧰 Requisitos

-   Java **17+**

-   Gradle **8+**

-   Google Chrome instalado (para execução local)

-   Git


----------

## 📁 Estrutura do Projeto

    `src/
    ├─ test/
    │  ├─ java/
    │  │  └─ agiqa/
    │  │     ├─ core/     # Base de testes e configuração do driver
    │  │     ├─ pages/    # Page Objects (HomePage, SearchResultsPage)
    │  │     ├─ tests/    # Testes automatizados (JUnit)
    │  │     └─ utils/    # Utilitários (Config)
    │  └─ resources/
    │     └─ config.properties # Configurações de ambiente` 

----------

## 🧩 Como funciona cada classe

### `agiqa.core.BaseTest`

Classe base para os testes.

Responsabilidades:

-   Configuração e finalização do **WebDriver**

-   Leitura de configurações como:

    -   URL base

    -   Modo headless


----------

### `agiqa.pages.HomePage`

Representa a página inicial do blog.

Métodos principais:

-   `open(String baseUrl)`  
    Abre a página e aguarda o carregamento do cabeçalho.

-   `openSearch()`  
    Aguarda a exibição do formulário de busca.

-   `search(String term)`  
    Preenche e submete o campo de busca via JavaScript.


----------

### `agiqa.pages.SearchResultsPage`

Representa a página de resultados da busca.

Métodos principais:

-   `waitForResults()`  
    Aguarda a exibição dos resultados ou da mensagem de nenhum resultado.

-   `getResultsCount()`  
    Retorna a quantidade de resultados encontrados.

-   `getFirstResultTitleText()`  
    Retorna o título do primeiro resultado.

-   `openFirstResult()`  
    Abre o primeiro resultado da lista.

-   `hasNoResultsMessage()`  
    Retorna a mensagem exibida quando não há resultados.


----------

### `agiqa.utils.Config`

Classe utilitária para leitura de configurações do arquivo `config.properties`  
ou de propriedades do sistema.

-   `get(String key, String defaultValue)`  
    Retorna o valor da configuração, priorizando propriedades do sistema.


----------

### `agiqa.tests.SearchTests`

Classe que contém os testes automatizados.

Cenários:

-   `searchWithResultsAndOpenFirst()`  
    Busca um termo existente, valida os resultados e abre o primeiro artigo.

-   `searchWithoutResultsShowsMessage()`  
    Busca um termo inexistente e valida a mensagem de nenhum resultado.


----------

## ⚙️ Configuração

### 1️⃣ Clonar o repositório

`git clone https://github.com/seu-usuario/qa-automation-blogdoagi.git cd qa-automation-blogdoagi`

----------

### 2️⃣ Ajustar o arquivo de configuração

Arquivo: `src/test/resources/config.properties`

Exemplos de propriedades:

-   `baseUrl` → URL do blog a ser testado

-   `headless` → `true` para rodar sem interface gráfica

-   `browser` → navegador (atualmente apenas `chrome`)


----------

## ▶️ Executando os Testes Localmente

`./gradlew test`

### Sobrescrevendo configurações via linha de comando

`./gradlew test -Dheadless=false -Dsearch.term.existing=idoso`

----------

## 🔁 Integração Contínua (GitHub Actions)

O projeto inclui o workflow:

`.github/workflows/gradle.yml`

Os testes são executados automaticamente a cada:

-   `push`

-   `pull request`


na branch **main**.

----------

## ✅ Principais Cenários de Teste

-   **Busca com resultados**  
    Verifica se, ao buscar um termo existente, os resultados são exibidos e o primeiro artigo pode ser aberto.

-   **Busca sem resultados**  
    Garante que uma mensagem clara é exibida quando não há resultados.

-   **Configuração flexível**  
    Termos de busca e modo headless podem ser alterados via propriedades do sistema.