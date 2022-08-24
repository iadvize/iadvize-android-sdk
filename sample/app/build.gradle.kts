plugins {
    id(Plugins.androidApp)
    id(Plugins.kotlin)
    id(Plugins.kotlinParcelize)
    id(Plugins.gms)
}

android {
    buildToolsVersion = AndroidConfig.buildToolsVersion

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
    implementation("com.iadvize:iadvize-sdk:2.7.0")

    implementation(Dependencies.App.androidxAppCompat)
    implementation(Dependencies.App.androidxCardView)
    implementation(Dependencies.App.androidxConstraintLayout)
    implementation(Dependencies.App.androidxMultiDex)
    implementation(Dependencies.App.androidxRecyclerView)
    implementation(platform(Dependencies.App.firebaseBom))
    implementation(Dependencies.App.firebaseAnalytics)
    implementation(Dependencies.App.firebaseMessaging)
    implementation(Dependencies.App.kotlinStdlib)
    implementation(Dependencies.App.material)
}
