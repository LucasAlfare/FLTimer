plugins {
  kotlin("android")
  id("com.android.application")
  id("org.jetbrains.compose")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""

  val flBinary =
    findProperty("lucasalfare.flbinary.version") ?: ""

  implementation("androidx.activity:activity-compose:1.7.2")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("androidx.core:core-ktx:1.10.1")
  implementation("androidx.compose.animation:animation:1.4.3")
  implementation("androidx.compose.material:material:1.4.3")

  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation("com.github.LucasAlfare:FLBinary:$flBinary")

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

  sourceSets.getByName("main") {
    val allNeededDirectories = mutableListOf<String>()

    allNeededDirectories.addAll(assets.srcDirs.map { it.toString() })

    allNeededDirectories += project
      .layout
      .projectDirectory
      .dir("../fltimer_resources")
      .toString()

    assets.setSrcDirs(allNeededDirectories)
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}