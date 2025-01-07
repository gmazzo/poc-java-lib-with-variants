enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}

rootProject.name = "java-lib-with-variants"

include(
    "library",
    "library-common",
    "library-spring5",
    "library-spring6",
    "app-spring5",
    "app-spring6",
)
