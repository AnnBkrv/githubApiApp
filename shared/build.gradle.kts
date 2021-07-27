import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

// groovy is a scripting language, gradle is a build system. my android studio uses
// kotlin as a scripting language


// in this "build...", you specify which plugins are to be used


plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization").version("1.5.20")
}



version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:1.6.1")
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
                implementation("io.ktor:ktor-client-cio:1.6.1")
                implementation("io.ktor:ktor-client-serialization:1.6.1")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-native-mt"){ version { strictly("1.5.0-native-mt") } }
                implementation("com.squareup.sqldelight:runtime:1.5.1")
            //implementation("io.ktor:ktor-client-gson:1.6.1")


            }}
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.ktor:ktor-client-cio:1.6.1")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:1.6.1")
                implementation("com.squareup.sqldelight:android-driver:1.5.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.6.1")
                implementation("com.squareup.sqldelight:native-driver:1.5.0")
            }}
        val iosTest by getting
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(30)
        targetSdkVersion(30)
    }
}


sqldelight {
    database("GithubDatabase") {
        packageName = "com.example"
    }
}