plugins {
    id(Plugins.androidLib)
    id(Plugins.kotlin)
    id(Plugins.kotlinParcelize)
}

android {
    buildToolsVersion = AndroidConfig.buildToolsVersion
    namespace = "com.iadvize.conversation.sdk.demo.common"

    defaultConfig {
        minSdk = AndroidConfig.minSdkVersion
        targetSdk = AndroidConfig.targetSdkVersion
        compileSdk = AndroidConfig.compileSdkVersion

        multiDexEnabled = true
        consumerProguardFiles("consumer-rules.pro")
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
    api("com.iadvize:iadvize-sdk:2.12.4")

    api(Dependencies.App.androidxAppCompat)
    api(Dependencies.App.androidxCardView)
    api(Dependencies.App.androidxConstraintLayout)
    api(Dependencies.App.androidxCoreKtx)
    api(Dependencies.App.androidxFragment)
    api(Dependencies.App.androidxNavigationFragment)
    api(Dependencies.App.androidxNavigationUi)
    api(Dependencies.App.androidxMultiDex)
    api(Dependencies.App.androidxRecyclerView)
    api(Dependencies.App.kotlinStdlib)
    api(Dependencies.App.kotlinCoroutines)
    api(Dependencies.App.material)
}