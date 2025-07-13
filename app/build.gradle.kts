plugins {
    id("com.android.application")
}

android {
    namespace = "com.s23010667.pasalhawula"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.s23010667.pasalhawula"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation ("androidx.viewpager2:viewpager2:1.1.0")
    implementation ("androidx.fragment:fragment:1.8.8")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.9.1")
    implementation ("androidx.startup:startup-runtime:1.2.0")
    implementation ("com.google.android.gms:play-services-maps:19.2.0")
    implementation ("com.google.android.gms:play-services-location:21.3.0")
    implementation ("com.google.android.libraries.places:places:4.3.1")
}