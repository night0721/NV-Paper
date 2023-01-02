import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    kotlin("jvm") version "1.8.0"
    id("io.papermc.paperweight.userdev") version "1.4.0"
    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
// KT
group = "me.night0721.nv"
version = "1.2.10"
description = "Multipurpose plugin"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven{
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.2") {
        exclude(group = "club.minnced", module = "opus-java")
    }
    implementation("org.mongodb:mongo-java-driver:3.12.11")
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(shadowJar)
        dependsOn(reobfJar) // comment out this if using runMojangMappedServer
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }
    reobfJar {
        outputJar.set(layout.buildDirectory.file("C:\\Users\\NK\\OneDrive\\Desktop\\.nky\\Coding\\Kotlin\\NVPaper\\run\\plugins\\NullValkyrie.jar"))
    }
}
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    main = "me.night0721.nv.NullValkyrie"
    apiVersion = "1.19"
    authors = listOf("NightKaly")
    website = "https://gituhub.com/night0721/NullValkyrie"
    description = "Multipurpose plugin"
}
