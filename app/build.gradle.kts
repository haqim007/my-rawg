plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.navigation.safeargs.kotlin)
}

android {
    namespace = "dev.haqim.myrawg"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.haqim.myrawg"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("BASE_URL", "https://api.rawg.io/api")
            buildConfigField("API_KEY", "760b14f2351e4438913b40b2ce56a546")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            buildConfigField("BASE_URL", "https://api.rawg.io/api")
            buildConfigField("API_KEY", "760b14f2351e4438913b40b2ce56a546")
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
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

//dependencies {
//
//    implementation(libs.androidx.ktx)
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.9.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
//    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//    implementation("androidx.activity:activity-ktx:1.7.2")
//
//    //retrofit
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    //noinspection GradleDependency
//    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
//    //noinspection GradleDependency
//    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
//
//    //paging
//    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
//
//    //glide
//    implementation("com.github.bumptech.glide:glide:4.15.0")
//    annotationProcessor("com.github.bumptech.glide:compiler:4.15.0")
//
//    //hilt
//    implementation("com.google.dagger:hilt-android:2.47")
//    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
//
//    // room
//    implementation("androidx.room:room-ktx:2.5.2")
//    ksp("androidx.room:room-compiler:2.5.2")
//    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
//    implementation("androidx.room:room-paging:2.5.2")
//}

dependencies {
    implementation(libs.androidx.ktx)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.activity.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.glide)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.room.paging)
}


inline fun <reified ValueT> com.android.build.api.dsl.VariantDimension.buildConfigField(
    name: String,
    value: ValueT
) {
    val resolvedValue = when (value) {
        is String -> "\"$value\""
        else -> value
    }.toString()
    buildConfigField(ValueT::class.java.simpleName, name, resolvedValue)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}