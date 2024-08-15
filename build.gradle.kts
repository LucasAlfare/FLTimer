@file:Suppress("PropertyName")

group = "com.lucasalfare.fltimer"
version = "1.0"

val exposed_version: String by project
val kotlinVersion: String by project

plugins {
  kotlin("jvm") version "2.0.0"
  kotlin("plugin.compose") version "2.0.0"
  id("org.jetbrains.compose") version "1.6.11"
//  application
}

repositories {
  mavenCentral()
  google()
  gradlePluginPortal()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  maven("https://jitpack.io")
}

dependencies {
  implementation(compose.desktop.currentOs)

  // dependency to listen key and/or mouse without GUI
  // used when implementing fltimer in a console/terminal.
  implementation("com.1stleg:jnativehook:2.0.2")

  /*
  -> Dependencies for database manipulation
  Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
  TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
   */
  implementation("org.xerial:sqlite-jdbc:3.46.0.1")
  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

//  implementation("ch.qos.logback:logback-classic")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
}

compose.desktop {
  application {
    mainClass = "com.lucasalfare.fltimer.MainKt"
  }
}

//application {
//  mainClass.set("com.lucasalfare.fltimer.MainKt")
//}

kotlin {
  jvmToolchain(17)
}

//tasks.withType<Jar> {
//  manifest {
//    attributes["Main-Class"] = application.mainClass
//  }
//
//  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//  from(configurations.compileClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
//}

/**
 * Task used to re-generate wrappers, if needed.
 */
tasks.withType<Wrapper> {
  gradleVersion = "8.5"
}