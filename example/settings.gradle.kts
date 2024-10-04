pluginManagement {
    repositories {
        google()
        maven(url = uri("https://plugins.gradle.org/m2/"))
        maven(url = uri("https://jitpack.io"))
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url = uri("https://plugins.gradle.org/m2/"))
        maven(url = uri("https://jitpack.io"))
        maven(url = uri("https://raw.github.com/iadvize/iadvize-android-sdk/master"))
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}

rootProject.name = providers.gradleProperty("demo.project.name").get()
include(":mobile")
