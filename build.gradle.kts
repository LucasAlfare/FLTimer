group = "com.lucasalfare.fltimer"
version = "1.0"

plugins {
  alias(libs.plugins.kotlin.jvm)
  kotlin("plugin.compose") version "2.0.0" // TODO: update
  id("org.jetbrains.compose") version "1.6.11" // TODO: update
}

repositories {
  mavenCentral()
  google()
  gradlePluginPortal()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  maven("https://jitpack.io")
}

dependencies {
  implementation(libs.exposed.core)
  implementation(libs.exposed.jdbc)
  implementation(libs.h2)
  implementation(libs.sqlite)
  implementation(libs.logback)
  implementation(compose.desktop.currentOs)
  implementation("com.1stleg:jnativehook:2.0.2")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
}

compose.desktop {
  application {
    mainClass = "com.lucasalfare.fltimer.MainKt"
  }
}

kotlin {
  jvmToolchain(21)
}

/**
 * Task used to re-generate wrappers, if needed.
 */
tasks.withType<Wrapper> {
  gradleVersion = "8.5"
}