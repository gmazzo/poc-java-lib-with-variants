plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

dependencies {
    implementation(projects.library) {
        capabilities { requireCapability("${project.group}:library-with-spring6") }
    }

    testImplementation(libs.spring.starter.test)
    testImplementation(libs.junit)
}
