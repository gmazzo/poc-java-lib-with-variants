import org.gradle.api.attributes.java.TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
    `maven-publish`
}

group = "org.sample"
version = "0.1.0"

val common by sourceSets.creating
val spring6 by sourceSets.creating
val spring6Test by testing.suites.registering(JvmTestSuite::class)

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
    withSourcesJar()

    registerFeature(common.name) {
        usingSourceSet(common)
        withSourcesJar()
    }
    registerFeature(spring6.name) {
        usingSourceSet(spring6)
        withSourcesJar()
    }
}

testing.suites {
    withType<JvmTestSuite> { useJUnit() }
}

dependencies {
    val commonFeature = dependencies.project(path)
        .capabilities { requireCapability("${project.group}:${project.name}-common") }

    val spring6Feature = dependencies.project(path)
        .capabilities { requireCapability("${project.group}:${project.name}-spring6") }
        .attributes { attribute(TARGET_JVM_VERSION_ATTRIBUTE, 21) }

    "commonApi"(platform(libs.spring.framework5)) // we take Spring 5 as base API
    "commonApi"(libs.spring.starter.web)
    api(commonFeature)
    "spring6Api"(platform(libs.spring.framework6))
    "spring6Api"(commonFeature)

    testImplementation(libs.spring.starter.test)
    testImplementation(libs.junit)

    "spring6TestImplementation"(spring6Feature)
    "spring6TestImplementation"(libs.spring.starter.test)
    "spring6TestImplementation"(libs.junit)
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
    suppressAllPomMetadataWarnings()
}

tasks.named<JavaCompile>(spring6.compileJavaTaskName) {
    javaCompiler = javaToolchains.compilerFor {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<KotlinCompile>(spring6.getCompileTaskName("kotlin")) {
    compilerOptions.jvmTarget = JvmTarget.JVM_21
}


tasks.check {
    dependsOn(spring6Test)
}