group = "com.example" // TODO: Change this to your group
version = "1.0-SNAPSHOT" // TODO: Change this to your addon version

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.paperweight)
    alias(libs.plugins.nova)
    id("com.gradleup.shadow") version "9.0.0-beta12"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)
    compileOnly(libs.nova)
//    implementation(files("craftlib.jar"))
    implementation("io.github.classgraph:classgraph:4.8.179")
}

addon {
    name = project.name.replaceFirstChar(Char::uppercase)
    version = project.version.toString()
    main = "com.example.ExampleAddon" // TODO: Change this to your main class

    // output directory for the generated addon jar is read from the "outDir" project property (-PoutDir="...")
    val outDir = project.findProperty("outDir")
    if (outDir is String)
        destination.set(File(outDir))
}

afterEvaluate {
    tasks {
        jar { archiveClassifier = "" }

        addonJar { dependsOn(shadowJar) }

        shadowJar {
            archiveClassifier = ""
            enableRelocation = true
            relocationPrefix = "com.example.shaded"
//            minimize()
        }
    }
}
