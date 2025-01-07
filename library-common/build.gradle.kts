plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
    `java-test-fixtures`
    `maven-publish`
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
    withSourcesJar()
}

dependencies {
    compileOnly(platform(libs.spring.framework5)) // we take Spring 5 as base API
    api(libs.spring.starter.web)

    testFixturesCompileOnly(platform(libs.spring.framework5)) // we take Spring 5 as base API
    testFixturesApi(libs.junit)
    testFixturesApi(libs.spring.starter.test)
}

publishing.publications.register<MavenPublication>("mavenJava") {
    val component = components["java"] as AdhocComponentWithVariants

    // Exclude test fixtures from publication, as we use it only internally
    configurations.matching { it.name.matches("testFixtures.*Elements".toRegex()) }.configureEach {
        component.withVariantsFromConfiguration(this) { skip() }
    }

    from(component)
}
