plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow")
    id("io.github.intisy.github-gradle")
}

fun latestGitTagVersion(): String? = try {
    val out = providers.exec { workingDir = rootDir; commandLine("git","describe","--tags","--abbrev=0"); isIgnoreExitValue = true }
    if (out.result.get().exitValue == 0) out.standardOutput.asText.get().trim().removePrefix("gh-").removePrefix("v").takeIf { it.isNotBlank() } else null
} catch (e: Exception) { null }

group = "com.github.Riley31415"
version = (project.findProperty("artifact_version") as String?)?.removePrefix("v")?.takeIf { it.isNotBlank() } ?: latestGitTagVersion() ?: "1.3.10"
val versionSuffix: String = when {
    !(project.findProperty("artifact_version") as String?).isNullOrBlank() -> ""
    System.getenv("GITHUB_ACTIONS") == "true" -> "-EXPERIMENTAL"
    else -> "-UNOFFICIAL"
}
val displayVersion = "${project.version}$versionSuffix"
description = "Library for Slimefun addons"
github {
    accessToken = System.getenv("GITHUB_TOKEN") ?: ""
    publish {
        tag = System.getenv("GITHUB_REF_NAME")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
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
<<<<<<< HEAD
    implementation("com.github.Slimefun5:SlimefunMetrics:master-SNAPSHOT")
    "githubCompileOnly"("Slimefun5:Slimefun5:v5.1.1")
    compileOnly("io.papermc.paper:paper-api:${property("paperApiVersion")}")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    compileOnly("com.github.Slimefun.dough:dough-api:cb22e71335")
=======
    // Compile against our Java-8 core jar (it bundles the relocated dough) rather than the upstream
    // modern core. Spigot 1.16.5 is the floor (not 1.8.8 like the core): it is the newest API still in
    // Java-8 bytecode a JDK-8 toolchain can read, and it has the PDC + modern Bukkit APIs this addon
    // needs. The jar is Java-8 and runs on any 1.16+ server (PDC addons cannot work below 1.14 anyway).
    githubCompileOnly("Slimefun5:Slimefun5:v5.2.1")
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
>>>>>>> origin/experimental

        
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
        archiveFileName.set("InfinityLib-$displayVersion.jar")
        archiveClassifier.set("")
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



