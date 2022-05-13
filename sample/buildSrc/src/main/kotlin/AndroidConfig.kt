import org.gradle.api.JavaVersion

object AndroidConfig {
    const val buildToolsVersion = "32.0.0"
    const val compileSdkVersion = 32
    const val minSdkVersion = 21
    const val targetSdkVersion = 32
    val javaVersion = JavaVersion.VERSION_11
}
