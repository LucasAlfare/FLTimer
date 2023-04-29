plugins {
  kotlin("android")
  id("com.android.application")
  id("org.jetbrains.compose")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""

  implementation("androidx.activity:activity-compose:1.7.1")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("androidx.core:core-ktx:1.10.0")
  implementation("androidx.compose.animation:animation:1.4.2")
  implementation("androidx.compose.material:material:1.4.2")

  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")

  implementation(project(":core"))
  implementation(project(":ui"))
}

android {
  namespace = "com.lucasalfare.fltimer"
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