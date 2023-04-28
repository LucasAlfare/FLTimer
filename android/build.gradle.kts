plugins {
  kotlin("android")
  id("com.android.application")
}

dependencies {
  implementation(project(":core"))
  implementation(project(":ui"))
}

android {
  namespace = "com.lucasalfare.fltimer.android"
  compileSdk = 33
  defaultConfig {
    applicationId = "com.lucasalfare.fltimer"
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