plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  implementation(compose.runtime)
  implementation("com.github.ajalt.mordant:mordant:2.0.0-beta13")
  implementation("com.github.LucasAlfare:FLListening:v2.0")
}