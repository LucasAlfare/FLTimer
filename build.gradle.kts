group = "com.lucasalfare.fltimer"
version = "1.0"

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
  implementation("com.github.ajalt.clikt:clikt:4.2.2")
  implementation("com.1stleg:jnativehook:2.0.2")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
}

application {
  mainClass.set("com.lucasalfare.fltimer.MainKt")
}

kotlin {
  jvmToolchain(17)
}

tasks.withType<Jar> {
  manifest {
    attributes["Main-Class"] = "com.lucasalfare.fltimer.MainKt"
  }

  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  from(configurations.compileClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
}

/**
 * Task used to re-generate wrappers, if needed.
 */
tasks.withType<Wrapper> {
  gradleVersion = "7.6.1"
}