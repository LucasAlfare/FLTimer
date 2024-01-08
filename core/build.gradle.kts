group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  implementation(compose.runtime)
  implementation("com.github.LucasAlfare:FLListening:v2.0")
  implementation(project(":data"))
}
