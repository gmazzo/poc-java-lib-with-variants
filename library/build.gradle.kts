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
val spring6Test by testing.suites.creating(JvmTestSuite::class)

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

dependencies {
    val commonFeature = project(path)
        .capabilities { requireCapability("${project.group}:${project.name}-common") }

    val spring6Feature = project(path)
        .capabilities { requireCapability("${project.group}:${project.name}-spring6") }

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

// configures JDK 21 for spring6
val jdk21 = JavaLanguageVersion.of(21)
sequenceOf(spring6, spring6Test.sources).forEach {
    tasks.named<JavaCompile>(it.compileJavaTaskName) {
        javaCompiler = javaToolchains.compilerFor {
            languageVersion = jdk21
        }
    }
    tasks.named<KotlinCompile>(it.getCompileTaskName("kotlin")) {
        compilerOptions.jvmTarget = JvmTarget.fromTarget(jdk21.toString())
    }
}
spring6Test.targets.configureEach {
    testTask {
        javaLauncher = javaToolchains.launcherFor {
            languageVersion = jdk21
        }
    }
}

tasks.check {
    dependsOn(spring6Test)
}