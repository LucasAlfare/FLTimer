group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("jvm") version "1.9.21"
  application
}

repositories {
  mavenCentral()
  maven("https://jitpack.io")
}

dependencies {
  implementation("com.github.LucasAlfare:FLListening:2.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
}

kotlin {
  jvmToolchain(17)
}

/**
 * Task used to re-generate wrappers, if needed.
 */
tasks.withType<Wrapper> {
  gradleVersion = "7.6.1"
}