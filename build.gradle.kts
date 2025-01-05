plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

allprojects {
    group = "org.sample"
    version = "0.1.0"

    plugins.withType<JvmTestSuitePlugin> {

        the<TestingExtension>().suites {
            withType<JvmTestSuite> { useJUnit() }
        }

    }
}
