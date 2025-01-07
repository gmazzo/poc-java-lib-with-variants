plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

dependencies {
    implementation(projects.library) dep@{
        capabilities { requireCapability("${this@dep.group}:${this@dep.name}-spring6") }
    }

    testImplementation(libs.spring.starter.test)
    testImplementation(libs.junit)
}
