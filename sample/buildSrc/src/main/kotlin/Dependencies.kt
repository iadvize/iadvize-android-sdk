object Dependencies {
    object Build {
        const val gms = "com.google.gms:google-services:${Versions.gms}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object App {
        const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
        const val androidxCardView = "androidx.cardview:cardview:${Versions.androidxCardView}"
        const val androidxConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
        const val androidxMultiDex = "androidx.multidex:multidex:${Versions.androidxMultiDex}"
        const val androidxRecyclerView =
            "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}"
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
        const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val material = "com.google.android.material:material:${Versions.material}"
    }
}
