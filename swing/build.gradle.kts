plugins {
  kotlin("jvm")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""

  implementation("org.jetbrains.compose.runtime:runtime-desktop:1.4.0")
  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
  implementation(project(":core"))
}