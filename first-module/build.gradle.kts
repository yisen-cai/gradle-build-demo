plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("java")
    id("java-library")
    id("maven-publish")
    id("signing")
}

repositories {
    mavenLocal()
    mavenCentral()
}


dependencies {

}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "ossrh"
            val releaseRepoUrl: String by rootProject.extra
            val snapshotRepoUrl: String by rootProject.extra
            url = uri(if (version.toString().endsWith("SNAPSHOT"))
                snapshotRepoUrl else releaseRepoUrl)
            credentials {
                username = properties["ossrh.username"] as String?
                password = properties["ossrh.password"] as String?
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                name.set("First Module")
                description.set("First Module description.")
                url.set("https://github.com/yisen-cai/gradle-build-demo/tree/main/first-module")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0'")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("yisen-cai")
                        name.set("YISHEN CAI")
                        email.set("yisen614@gmail.com")
                        organization.set("Glancebar")
                        organizationUrl.set("https://glancebar.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/yisen-cai/gradle-build-demo.git'")
                    developerConnection.set("scm:git:git@github.com:yisen-cai/gradle-build-demo.git")
                    url.set("https://github.com/yisen-cai/gradle-build-demo")
                }
            }
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


signing {
    sign(publishing.publications["maven"])
}