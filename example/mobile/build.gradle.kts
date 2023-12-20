plugins {
    id(Plugins.androidApp)
    id(Plugins.kotlin)
    id(Plugins.kotlinParcelize)
    id(Plugins.androidxNavigationSafeArgs)
    id(Plugins.gms)
}

android {
    buildToolsVersion = AndroidConfig.buildToolsVersion
    namespace = "com.iadvize.conversation.sdk.demo"

    defaultConfig {
        applicationId = "com.iadvize.conversation.sdk.demo"
        versionName = "1.0"
        versionCode = 1

        minSdk = AndroidConfig.minSdkVersion
        targetSdk = AndroidConfig.targetSdkVersion
        compileSdk = AndroidConfig.compileSdkVersion

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
        sourceCompatibility = AndroidConfig.javaVersion
        targetCompatibility = AndroidConfig.javaVersion
    }

    kotlinOptions {
        jvmTarget = AndroidConfig.javaVersion.toString()
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

configurations {
    all {
        exclude(group = "xpp3", module = "xpp3")
    }
}

dependencies {
    implementation("com.iadvize:iadvize-sdk:2.12.3")

    implementation(Dependencies.App.androidxAppCompat)
    implementation(Dependencies.App.androidxCardView)
    implementation(Dependencies.App.androidxConstraintLayout)
    implementation(Dependencies.App.androidxCoreKtx)
    implementation(Dependencies.App.androidxFragment)
    implementation(Dependencies.App.androidxNavigationFragment)
    implementation(Dependencies.App.androidxNavigationUi)
    implementation(Dependencies.App.androidxMultiDex)
    implementation(Dependencies.App.androidxRecyclerView)
    implementation(platform(Dependencies.App.firebaseBom))
    implementation(Dependencies.App.firebaseAnalytics)
    implementation(Dependencies.App.firebaseMessaging)
    implementation(Dependencies.App.kotlinStdlib)
    implementation(Dependencies.App.kotlinCoroutines)
    implementation(Dependencies.App.material)
}
