import java.util.Properties

plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.seleniumhq.selenium:selenium-java:4.24.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("org.apache.logging.log4j:log4j-api:2.23.1")
    testImplementation("org.apache.logging.log4j:log4j-core:2.23.1")
}

tasks.test {
    useJUnitPlatform()

    // Carrega config.properties para fallback
    val cfg = Properties().apply {
        val res = file("src/test/resources/config.properties")
        if (res.exists()) res.inputStream().use { load(it) }
    }

    // Função de precedência: -D > gradle.properties > config.properties > default
    fun prop(name: String, default: String): String =
        (System.getProperty(name)
            ?: (project.findProperty(name) as String?)
            ?: cfg.getProperty(name)
            ?: default).toString()

    systemProperty("baseUrl", prop("baseUrl", "https://blogdoagi.com.br/"))
    systemProperty("headless", prop("headless", "true"))
    systemProperty("browser", prop("browser", "chrome"))
    systemProperty("search.term.existing", prop("search.term.existing", "atestado"))
    systemProperty("search.term.emptyAllowed", prop("search.emptyAllowed", "false"))
}
