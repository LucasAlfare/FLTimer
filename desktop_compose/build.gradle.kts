plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
}

dependencies {
  implementation(project(":core"))
  implementation(compose.desktop.currentOs)
  implementation("com.github.LucasAlfare:FLListening:v1.1")
}

//compose.desktop {
//  application {
//
//  }
//}
