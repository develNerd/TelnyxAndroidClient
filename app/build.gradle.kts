plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.8.10"

}

android {
    namespace = "org.telnyx.androidclient"
    compileSdk = 33

    defaultConfig {
        applicationId = "org.telnyx.androidclient"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    //activity extensions
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    with(Deps.Compose){
        val composeBom = platform(composePlatformBom)
        implementation(composeBom)
        androidTestImplementation(composeBom)

        implementation(composeBom)
        androidTestImplementation(composeBom)
        implementation(material3)
        implementation(foundation)
        implementation(ui)
        implementation(preview)
        debugImplementation(previewDebug)
        // UI Tests
        androidTestImplementation(uiJUnitTest)
        debugImplementation(uiManifestTest)
        // Integration with activities
        implementation(activities)
    }

    with(Deps.Navigation){
        implementation(navFragment)
        implementation(ktx)
    }

    with(Deps.Koin){
        implementation(android)
    }

    with(Deps.KotlinX){
        implementation(serialization)
    }

    implementation("com.github.team-telnyx:telnyx-webrtc-android:1.2.20-alpha")
    implementation("androidx.preference:preference-ktx:1.1.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-beta01")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}