@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelightPlugin)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.sqldelight.runtime)
                api(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.androidDriver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.sqldelight.nativeDriver)
            }
        }
    }
}

android {
    namespace = "com.techullurgy.tuitiontracker"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}

//sqldelight {
//    databases {
//        create("TestDatabase") {
//            packageName.set("com.techullurgy.tuitiontracker.sqldelight")
//            srcDirs.setFrom("src/commonMain/kotlin")
//        }
//    }
//}