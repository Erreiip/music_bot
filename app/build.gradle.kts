/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.6.4/userguide/building_java_projects.html
 */

group = "discord_bot"
version = "1.0-SNAPSHOT"

plugins {
    id("java")
    id("application")
}

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    implementation("com.google.guava:guava:32.1.3-jre")
    
    implementation("org.json:json:20090211")

    // jda
    implementation("net.dv8tion:JDA:5.0.0-beta.20")

    // google
    implementation("com.google.api-client:google-api-client:1.32.1")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.23.0")
    implementation("com.google.apis:google-api-services-youtube:v3-rev222-1.25.0")
    implementation("com.google.api-client:google-api-client-jackson2:1.28.1")

    // lava player
    implementation("com.github.natanbc:lavadsp:0.7.7")
    implementation("dev.arbjerg:lavaplayer:727959e9f621fc457b3a5adafcfffb55fdeaa538-SNAPSHOT")

    // jdbc
    implementation("org.postgresql:postgresql:42.3.3")
}

application {
    // Define the main class for the application.
    mainClass.set("discord_bot.Main")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    destinationDirectory = file("$rootDir/docker/java")
    archiveBaseName.set("discord_bot")
    manifest {
        attributes["Main-Class"] = "discord_bot.Main"
    }
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) }) {
        exclude("META-INF/*.MF")
    }
}
