plugins {
  kotlin("multiplatform")
  id("com.android.application")
  id("org.jetbrains.compose")
}

kotlin {
  android()
  sourceSets {
    val androidMain by getting {
      dependencies {
        implementation(project(":core"))
        implementation(project(":ui"))
      }
    }
  }
}

android {
  namespace = "lucasalfare.fltimer.android"
  compileSdk = 33
  defaultConfig {
    applicationId = "lucasalfare.fltimer.android"
    minSdk = 26
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}