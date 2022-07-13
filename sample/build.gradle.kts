buildscript {
    repositories {
        google()
        maven(url = uri("https://plugins.gradle.org/m2/"))
        maven(url = uri("https://jitpack.io"))
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(Dependencies.Build.gms)
        classpath(Dependencies.Build.gradle)
        classpath(Dependencies.Build.kotlin)
    }
}

allprojects {
    repositories {
        google()
        gradlePluginPortal()
        maven(url = uri("https://raw.github.com/iadvize/iadvize-android-sdk/master"))
        maven(url = uri("https://plugins.gradle.org/m2/"))
        maven(url = uri("https://jitpack.io"))
        mavenCentral()
        mavenLocal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
