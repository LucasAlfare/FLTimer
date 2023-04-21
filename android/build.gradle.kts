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
        implementation("androidx.activity:activity-compose:1.7.1")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("androidx.core:core-ktx:1.10.0")
        implementation("androidx.compose.animation:animation:1.4.2")
        implementation("androidx.compose.material:material:1.4.2")

        implementation("com.github.LucasAlfare:FLListening:1.1")
        implementation(project(":ui"))
        implementation(project(":core"))
      }
    }
  }
}

android {
  compileSdk = 33
  namespace = "com.lucasalfare.fltimer.android"
  defaultConfig {
    applicationId = "com.lucasalfare.fltimer.android"
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