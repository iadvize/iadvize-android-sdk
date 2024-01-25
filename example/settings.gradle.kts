pluginManagement {
    repositories {
        google()
        maven(url = uri("https://plugins.gradle.org/m2/"))
        maven(url = uri("https://jitpack.io"))
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":common")
include(":example-defaultFloatingButton")
include(":example-discreetButton")
include(":example-floatingBanner")
include(":example-integratedBanner")
include(":example-navigationItem")
