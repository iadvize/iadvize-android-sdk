import org.gradle.api.JavaVersion

object AndroidConfig {
    const val buildToolsVersion = "33.0.0"
    const val compileSdkVersion = 33
    const val minSdkVersion = 21
    const val targetSdkVersion = 33
    val javaVersion = JavaVersion.VERSION_11
}
