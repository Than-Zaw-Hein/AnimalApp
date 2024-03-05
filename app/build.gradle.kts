plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")//For dagger
    id("com.google.devtools.ksp")//For using ksp
}

android {
    namespace = "com.tzh.animal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tzh.animal"
        minSdk = 26
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    // retrofit and okhttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // paging
    implementation("androidx.paging:paging-runtime:2.1.0")
    implementation("androidx.paging:paging-rxjava2:2.1.0")

    // misc
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.jakewharton.rxbinding2:rxbinding:2.1.1")
    implementation("com.squareup.okhttp3:logging-interceptor:3.11.0")

    implementation("androidx.paging:paging-compose:1.0.0-alpha12")
    implementation("com.google.accompanist:accompanist-pager:0.19.0")

    // lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")

    //For Dagger Hilt
    var hilt_version = "2.47"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    ksp("com.google.dagger:dagger-compiler:2.47") // Dagger compiler
    ksp("com.google.dagger:hilt-compiler:2.47")   // Hilt compiler
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("io.coil-kt:coil:1.4.0")
    implementation("com.google.code.gson:gson:2.8.9")
}