rootProject.name = "FLTimer"

pluginManagement {
  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  plugins {
    val kotlinVersion = extra["kotlin.version"] as String
    val composeVersion = extra["compose.version"] as String
    val agpVersion = extra["agp.version"] as String

    kotlin("jvm") version kotlinVersion

    kotlin("android").version(kotlinVersion)
    id("com.android.application").version(agpVersion)
    id("com.android.library").version(agpVersion)

    id("org.jetbrains.compose") version composeVersion
  }
}

include(":core")
include(":android")
include(":ui")
