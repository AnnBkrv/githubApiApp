// specifies how we structure the whole code
// specify paths for plugins

buildscript {
    repositories {
        // specifies where to look for plugins during building process
        gradlePluginPortal()
        google()
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://jetbrains.bintray.com/intellij-third-party-dependencies")
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
        classpath("com.android.tools.build:gradle:7.0.0-rc01")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.1")
    }
}

allprojects {
    // allprojects = all modules
    repositories {
        // specifies where to look for LIBRARIES
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}