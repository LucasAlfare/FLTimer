rootProject.name = "FLTimer"

pluginManagement {
  repositories {
    google()
    mavenCentral()
  }

  plugins {
    kotlin("jvm") version "1.8.20"
  }
}

include(":core")
include(":swing")