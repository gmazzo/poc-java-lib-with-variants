import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-library`
    `maven-publish`
}

val spring6 by sourceSets.creating

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
    withSourcesJar()

    registerFeature(spring6.name) {
        capability("$group", "${name}-with-spring6", "$version")
        usingSourceSet(spring6)
    }
}

dependencies {
    api(projects.librarySpring5)
    "spring6Api"(projects.librarySpring6)
}

tasks.named(spring6.jarTaskName) {
    enabled = false
}
tasks.named<JavaCompile>(spring6.compileJavaTaskName) {
    javaCompiler = javaToolchains.compilerFor {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
tasks.named<KotlinCompile>(spring6.getCompileTaskName("kotlin")) {
    compilerOptions.jvmTarget = JvmTarget.JVM_21
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}
