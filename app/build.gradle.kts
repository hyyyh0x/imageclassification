plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.imageclassification"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.imageclassification"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        mlModelBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /*// To recognize Latin script
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")

    // To recognize Chinese script
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition-chinese:16.0.0")

    // To recognize Devanagari script
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition-devanagari:16.0.0")

    // To recognize Japanese script
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition-japanese:16.0.0")

    // To recognize Korean script
    implementation("com.google.android.gms:play-services-mlkit-text-recognition-korean:16.0.0")
    // To recognize Latin script
    implementation ("com.google.mlkit:text-recognition:16.0.0")

    // To recognize Chinese script
    implementation ("com.google.mlkit:text-recognition-chinese:16.0.0")

    // To recognize Devanagari script
    implementation ("com.google.mlkit:text-recognition-devanagari:16.0.0")

    // To recognize Japanese script
    implementation ("com.google.mlkit:text-recognition-japanese:16.0.0")

    // To recognize Korean script
    implementation ("com.google.mlkit:text-recognition-korean:16.0.0")*/
    implementation("org.tensorflow:tensorflow-lite:2.7.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.3.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.3.0")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation(platform("com.google.firebase:firebase-bom:30.5.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-ml-vision:24.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.google.android.gms:play-services-vision:20.1.1")
    implementation("com.google.android.gms:play-services-vision-common:19.1.1")
}