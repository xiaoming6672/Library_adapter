import com.android.build.gradle.internal.api.LibraryVariantOutputImpl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    id("kotlin-android")
    `maven-publish`
}

android {
    namespace = "com.zhang.library.adapter"
    compileSdk = config.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = config.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt") , "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf(
            "-module-name=$libGroupId.$libArtifactId" ,
            "-Xjvm-default=all" ,
        )
    }

    android.libraryVariants.all {
        outputs.all {
            if (this is LibraryVariantOutputImpl) {
                outputFileName = "${rootProject.name}-$name-$libVersionCode-$libVersionName.aar"
            }
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = libGroupId
            artifactId = libArtifactId
            version = libVersionName

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

val libGroupId = config.versions.libGroupId.get()
val libArtifactId = config.versions.libArtifactId.get()
val libVersionCode = config.versions.libVersionCode.get()
val libVersionName = config.versions.libVersionName.get()


dependencies {
    implementation(fileTree(mapOf("dir" to "libs" , "include" to listOf("*.aar"))))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.recyclerview)

    implementation(libs.library.utils)
}