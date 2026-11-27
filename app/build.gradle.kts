plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "org.insbaixcamp.appfitnessa"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "org.insbaixcamp.appfitnessa"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    //Exercicis
    // Retrofit: peticions HTTP
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson: conversió de JSON a Kotlin
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Coil: carregar imatges amb Compose
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Coil: mostrar GIF animats
    implementation("io.coil-kt:coil-gif:2.7.0")

    // ViewModel per a Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
}