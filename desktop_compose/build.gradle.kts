plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""

  implementation(project(":core"))
  implementation(compose.desktop.currentOs)
  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
}
