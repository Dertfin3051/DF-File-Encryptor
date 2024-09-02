import java.nio.charset.StandardCharsets

plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "ru.dfhub.dfe"
version = "1.1"

application {
    mainClass = "ru.dfhub.dfe.Main"
}

javafx {
    version = "19.0.2"
    modules = listOf("javafx.controls")
}

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.dfhub.dfe.Main"
    }
}

tasks.compileJava {
    charset(StandardCharsets.UTF_8.name())
}