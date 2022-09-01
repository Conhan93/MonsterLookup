import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev774"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "me.conha"
version = "1.0"

val exposedVersion : String by project
val h2Version : String by project

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")



    // local storage - database
    implementation("com.h2database:h2:$h2Version")

    // local storage - orm
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            modules("java.instrument", "java.net.http", "java.sql", "jdk.unsupported")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MonsterLookupDnD"
            packageVersion = "1.0.0"
        }
    }
}