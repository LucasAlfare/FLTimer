group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("jvm")
}

dependencies {
  implementation("org.jetbrains.exposed:exposed-core:0.45.0")
  implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")
  implementation("org.xerial:sqlite-jdbc:3.43.0.0")
  testImplementation(kotlin("test"))
}