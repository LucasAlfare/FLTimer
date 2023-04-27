plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  val flListeningVersion =
    findProperty("lucasalfare.fllistening.version") ?: ""

  implementation(project(":core"))
  implementation(compose.desktop.currentOs)

  runtimeOnly("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

  implementation("com.github.LucasAlfare:FLListening:$flListeningVersion")
}
