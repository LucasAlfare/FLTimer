group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("multiplatform")
}

kotlin {
  jvm {}
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation(project(":core"))
      }
    }
  }
}