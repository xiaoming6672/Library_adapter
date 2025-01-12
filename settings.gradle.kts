pluginManagement {
    repositories {
        /** 阿里云效 私有maven */
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven(url = "https://maven.aliyun.com/repository/apache-snapshots")

        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        maven(url = "https://repo1.maven.org/maven2/")

        maven(url = "https://artifact.bytedance.com/repository/Volcengine/")
        maven(url = "https://artifact.bytedance.com/repository/pangle/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        /** 阿里云效 私有maven */
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
        maven(url = "https://maven.aliyun.com/repository/apache-snapshots")

        maven {
            setUrl("https://packages.aliyun.com/maven/repository/2495277-release-LwpiHW")
            credentials {
                username = "66f6606383d62ab44fa5d54f"
                password = "s4BvVpAib[-I"
            }
        }

        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://repo1.maven.org/maven2/")

        maven(url = "https://artifact.bytedance.com/repository/Volcengine/")
        maven(url = "https://artifact.bytedance.com/repository/pangle/")
    }
    versionCatalogs {
        create("config") {
            from(files("gradle/config.versions.toml"))
        }
    }
    //    versionCatalogs {
//        create("config") {
//            from(files("gradle/config.versions.toml"))
//        }
//    }
}

rootProject.name = "Library_adapter"
include(":lib")
include(":app")
