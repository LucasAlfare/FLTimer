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
  // lib to help build decoupled code parts. those parts
  // will not to communicate directly, only fire events
  // which other will listen to.
  implementation("com.github.LucasAlfare:FLListening:2.0")

  // dependency to listen key and/or mouse without GUI
  // used when implementing fltimer in a console/terminal.
  implementation("com.1stleg:jnativehook:2.0.2")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
  implementation("com.github.ajalt.clikt:clikt:4.2.2")

  // isso aqui serve apenas para gerar os logs da engine do servidor...
  implementation("ch.qos.logback:logback-classic:1.4.12")

  // Dependencies for database manipulation
  implementation("org.jetbrains.exposed:exposed-core:0.45.0")
  implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")
  /*
  Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
  TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
   */
  implementation("org.xerial:sqlite-jdbc:3.44.1.0")

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