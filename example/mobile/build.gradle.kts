plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.gms)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
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
        sourceCompatibility =
            JavaVersion.toVersion(providers.gradleProperty("demo.java.target").get())
        targetCompatibility =
            JavaVersion.toVersion(providers.gradleProperty("demo.java.target").get())
    }

    kotlin {
        jvmToolchain(providers.gradleProperty("demo.java.target").get().toInt())
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

configurations {
    all {
        exclude(group = "xpp3", module = "xpp3")
        exclude(group = "xpp3", module = "xpp3_min")
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.multidex)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.androidx.recyclerview)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase.tools)
    implementation(libs.iadvize.sdk)
    implementation(libs.insetter)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.material)
}
