plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
    `maven-publish`
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
    withSourcesJar()
}

dependencies {
    api(platform(libs.spring.framework5))
    api(projects.libraryCommon)

    testImplementation(testFixtures(projects.libraryCommon))
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}
