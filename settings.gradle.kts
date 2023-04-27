rootProject.name = "FLTimer"

pluginManagement {
  repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  plugins {
    val kotlinVersion = extra["kotlin.version"] as String
    val composeVersion = extra["compose.version"] as String

    kotlin("jvm") version kotlinVersion
    id("org.jetbrains.compose") version composeVersion
  }
}

include(":core")
include(":ui")