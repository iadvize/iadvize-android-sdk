import org.gradle.api.JavaVersion

object AndroidConfig {
    const val buildToolsVersion = "34.0.0"
    const val compileSdkVersion = 34
    const val minSdkVersion = 21
    const val targetSdkVersion = 34
    val javaVersion = JavaVersion.VERSION_11
}
