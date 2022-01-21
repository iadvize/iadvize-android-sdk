buildscript {
    repositories {
        google()
        maven(url = uri("https://plugins.gradle.org/m2/"))
        maven(url = uri("https://jitpack.io"))
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(BuildDependencies.gms)
        classpath(BuildDependencies.gradle)
        classpath(BuildDependencies.kotlin)
    }
}

allprojects {
    repositories {
        google()
        maven(url = uri("https://raw.github.com/iadvize/iadvize-android-sdk/master"))
        maven(url = uri("https://plugins.gradle.org/m2/"))
        maven(url = uri("https://jitpack.io"))
        mavenCentral()
        gradlePluginPortal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
