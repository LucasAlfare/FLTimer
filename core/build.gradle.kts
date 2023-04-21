plugins {
  kotlin("multiplatform")
}

group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

kotlin {
  jvm {}
  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("io.socket:socket.io-client:2.1.0")
      }
    }
  }
}