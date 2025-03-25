plugins {
    id("application")
    id("java")
    id("org.openjfx.javafxplugin") version "0.0.14"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // JavaFX (Versión 21)
    val javaFxVersion = "21"
    implementation("org.openjfx:javafx-controls:$javaFxVersion")
    implementation("org.openjfx:javafx-fxml:$javaFxVersion")
    implementation("org.openjfx:javafx-base:$javaFxVersion")
    implementation("org.openjfx:javafx-media:$javaFxVersion")
}

application {
    mainClass.set("org.example.EntryPoint")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.media")
}

tasks.withType<JavaExec> {
    modularity.inferModulePath.set(true)
}

tasks.test {
    useJUnitPlatform()
}
