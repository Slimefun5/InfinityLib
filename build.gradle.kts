plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow")
    id("io.github.intisy.github-gradle")
}

group = "com.github.Riley31415"
version = "1.3.10"
description = "Library for Slimefun addons"
github {
    accessToken = System.getenv("GITHUB_TOKEN") ?: ""
    publish {
        tag = System.getenv("GITHUB_REF_NAME")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    "githubCompileOnly"("Slimefun5:Slimefun5:v5.1.1")
    compileOnly("io.papermc.paper:paper-api:${property("paperApiVersion")}")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    compileOnly("com.github.Slimefun.dough:dough-api:cb22e71335")

        implementation("org.bstats:bstats-bukkit:3.0.2")

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.15.2")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:4.107.0") {
        exclude(group = "org.jetbrains", module = "annotations")
    }
}

configurations.testImplementation {
    extendsFrom(configurations.compileOnly.get())
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    jar {
        enabled = false
    }
    shadowJar {
        archiveFileName.set("InfinityLib-v${project.version}.jar")
        archiveClassifier.set("")
        relocate("org.bstats", "io.github.mooy1.infinitylib.bstats")
    }
        build {
        dependsOn(shadowJar)
    }
    test {
        enabled = false
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.Riley31415"
            artifactId = "InfinityLib"
            version = project.version.toString()
            artifact(tasks.shadowJar)
        }
    }
}



