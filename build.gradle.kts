group = "lucasalfare.fltimer"
version = "1.0-SNAPSHOT"

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("jvm") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
    }

//    dependencies {
//        implementation("com.lucasalfare.fllistening:FLListening") {
//            version {
//                branch = "master"
//            }
//        }
//    }
}

//subprojects {
//    apply(plugin = "org.jetbrains.kotlin.jvm")
//
//    dependencies {
//        implementation("com.lucasalfare.fllistening:FLListening") {
//            version {
//                branch = "master"
//            }
//        }
//    }
//}