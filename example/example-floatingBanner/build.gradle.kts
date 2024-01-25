plugins {
    id(Plugins.androidApp)
    id(Plugins.kotlin)
    id(Plugins.kotlinParcelize)
    id(Plugins.androidxNavigationSafeArgs)
}

android {
    buildToolsVersion = AndroidConfig.buildToolsVersion
    namespace = "com.iadvize.conversation.sdk.demo.floatingbanner"

    defaultConfig {
        applicationId = "com.iadvize.conversation.sdk.demo.floatingbanner"
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
    implementation(project(":common"))
}
