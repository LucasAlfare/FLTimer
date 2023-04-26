rootProject.name = "FLTimer"

pluginManagement {
  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }

  plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.compose") version ("1.4.0")
  }
}

include(":core")
include(":swing")
include(":desktop_compose")