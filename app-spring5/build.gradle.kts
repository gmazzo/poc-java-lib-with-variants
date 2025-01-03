plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(17)

dependencies {
    implementation(projects.library)

    testImplementation(libs.spring.starter.test)
    testImplementation(libs.junit)
}
