import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev774"
    kotlin("plugin.serialization") version "1.6.10"
}

val appVersion : String by project
val exposedVersion : String by project
val h2Version : String by project
val koinVersion : String by project

group = "me.conha"
version = version


repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
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

    // Koin Core features
    implementation("io.insert-koin:koin-core:$koinVersion")
    // Koin Test features
    //testImplementation("io.insert-koin:koin-test:$koinVersion")
    // Jetpack Compose
    //implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    // logger
    implementation("org.slf4j:slf4j-simple:2.0.0")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.0")
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
            // runtime modules
            modules(
                "java.instrument",
                "java.net.http",
                "java.sql",
                "jdk.unsupported"
            )

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

            // Distributable info
            packageName = "Monster Lookup"
            packageVersion = appVersion
            description = "App for all your DnD monster look up needs!"


            windows {
                menuGroup = "Monster Lookup"
            }
        }
    }
}