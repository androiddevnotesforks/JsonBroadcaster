import java.io.FileInputStream
import java.util.*

plugins {
    id("buildlogic.plugins.conventions.library")
    id("kotlinx-serialization")
    id("com.vanniktech.maven.publish")
    id("signing")
}

android {
    namespace = "com.broadcast.handler"
}

dependencies {
    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.serialization)
}

group = "com.github.guilhe"
version = "1.0.1"

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.DEFAULT, true)
    signAllPublications()

    pom {
        description.set("Update your apps Ui State at runtime.")
        inceptionYear.set("2022")
        url.set("https://github.com/GuilhE/JsonBroadcaster")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("GuilhE")
                name.set("Guilherme Delgado")
                email.set("gdelgado@bliss.pt")
                url.set("https://github.com/GuilhE")
            }
        }
        scm {
            url.set("https://github.com/GuilhE/JsonBroadcaster")
            connection.set("scm:git:github.com/GuilhE/JsonBroadcaster.git")
            developerConnection.set("scm:git:ssh://github.com/GuilhE/JsonBroadcaster.git")
        }
    }
}

signing {
    val signingInMemoryKeyId: String
    val signingInMemoryKey: String
    val signingInMemoryKeyPassword: String

    if (project.rootProject.file("local.properties").exists()) {
        with(Properties().apply { load(FileInputStream(File(rootProject.rootDir, "local.properties"))) }) {
            signingInMemoryKeyId = getProperty("signingInMemoryKeyId")
            signingInMemoryKey = getProperty("signingInMemoryKey")
            signingInMemoryKeyPassword = getProperty("signingInMemoryKeyPassword")
        }
    } else {
        signingInMemoryKeyId = System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKeyId")
        signingInMemoryKey = System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKeyId")
        signingInMemoryKeyPassword = System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKeyPassword")
    }
    useInMemoryPgpKeys(signingInMemoryKeyId, signingInMemoryKey, signingInMemoryKeyPassword)
}