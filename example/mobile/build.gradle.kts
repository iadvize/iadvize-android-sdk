plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.gms)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = providers.gradleProperty("demo.namespace").get()
    buildToolsVersion = providers.gradleProperty("demo.android.build.tools").get()
    compileSdk = providers.gradleProperty("demo.android.target").get().toInt()

    defaultConfig {
        applicationId = providers.gradleProperty("demo.namespace").get()
        versionName = providers.gradleProperty("demo.version.name").get()
        versionCode = (System.currentTimeMillis() / 3_600_000).toInt()

        targetSdk = providers.gradleProperty("demo.android.target").get().toInt()
        minSdk = providers.gradleProperty("demo.android.min.supported").get().toInt()

        multiDexEnabled = true
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility =
            JavaVersion.toVersion(providers.gradleProperty("demo.java.target").get())
        targetCompatibility =
            JavaVersion.toVersion(providers.gradleProperty("demo.java.target").get())
    }

    kotlin {
        jvmToolchain(providers.gradleProperty("demo.java.target").get().toInt())
    }

    buildFeatures {
        compose = true
    }
}

configurations {
    all {
        exclude(group = "xpp3", module = "xpp3")
        exclude(group = "xpp3", module = "xpp3_min")
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugaring)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase.tools)
    implementation(libs.iadvize.sdk)
    implementation(libs.kotlin.stdlib)
    implementation(libs.material)
}
