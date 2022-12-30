import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.4.0"
    id("xyz.jpenilla.run-paper") version "2.0.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.night0721.nv"
version = "1.0.12"
description = "Multipurpose plugin"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.2") {
        exclude(group = "club.minnced", module = "opus-java")
    }
    implementation("org.mongodb:mongo-java-driver:3.12.11")
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    // paperweightDevBundle("com.example.paperfork", "1.19.3-R0.1-SNAPSHOT")

    // You will need to manually specify the full dependency if using the groovy gradle dsl
    // (paperDevBundle and paperweightDevBundle functions do not work in groovy)
    // paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.19.3-R0.1-SNAPSHOT")
}

tasks {
    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    reobfJar {
        outputJar.set(layout.buildDirectory.file("E:\\Files\\NV\\plugins\\NullValkyrie.jar"))
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
