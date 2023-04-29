group = "com.lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""

  val flBinary =
    findProperty("lucasalfare.flbinary.version") ?: ""

  implementation(project(":core"))
  implementation(compose.desktop.currentOs)

  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation("com.github.LucasAlfare:FLBinary:$flBinary")
}
