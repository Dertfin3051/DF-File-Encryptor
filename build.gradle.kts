plugins {
    id("java")
    id("application")
}

group = "ru.dfhub.dfe"
version = "1.1"

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.dfhub.dfe.Main"
    }
}