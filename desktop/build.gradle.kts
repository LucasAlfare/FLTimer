plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose")
}

kotlin {
  jvm {}
  sourceSets {
    val jvmMain by getting  {
      dependencies {
        implementation(compose.desktop.currentOs)
        implementation(project(":core"))
        implementation(project(":ui"))
        implementation("com.github.LucasAlfare:FLListening:1.1")
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "com.lucasalfare.fltimer.desktop.MainKt"
  }
}