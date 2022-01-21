import org.gradle.api.JavaVersion

object AndroidConfig {
    const val buildToolsVersion = "31.0.0"
    const val compileSdkVersion = 31
    const val minSdkVersion = 19
    const val targetSdkVersion = 31
    val javaVersion = JavaVersion.VERSION_11
}
