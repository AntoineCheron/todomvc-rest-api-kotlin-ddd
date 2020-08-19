import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"

    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"

    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "9.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.11.1"
    id("info.solidsoft.pitest") version "1.4.7"
}

group = "com.github.antoinecheron"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    jcenter()
    maven { url = uri("https://repo.spring.io/milestone") }
}

buildscript {
    repositories {
        jcenter()
        mavenLocal()
    }

    val pitest = configurations.maybeCreate("pitest")

    dependencies {
        classpath("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.4.7")
        pitest("eu.stamp-project:descartes:1.2.5")
    }
}

val coroutinesVersion = "1.3.72"
val springVersion = "2.3.3.RELEASE"
val cucumberVersion = "6.5.0"
val kotestVersion = "4.2.0.RC2"

dependencies {

    // -------------- REST API DEPENDENCIES --------------

    implementation("org.springframework.boot:spring-boot-starter-webflux:$springVersion")

    // -------------- GraphQL DEPENDENCIES --------------

    implementation("com.graphql-java:graphql-java:15.0")

    // -------------- DATABASE DEPENDENCIES --------------

    implementation("org.springframework.boot.experimental:spring-boot-starter-data-r2dbc:0.1.0.M1")
    runtimeOnly("com.h2database:h2:1.4.199")
    runtimeOnly("io.r2dbc:r2dbc-h2:0.8.0.RC2")

    // -------------- COMMON DEPENDENCIES --------------

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$coroutinesVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.5")

    testImplementation("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.boot.experimental:spring-boot-test-autoconfigure-r2dbc:0.1.0.M2")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot.experimental:spring-boot-bom-r2dbc:0.1.0.M2")
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    withType<io.gitlab.arturbosch.detekt.Detekt> {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "1.8"
    }
}

detekt {
    failFast = true
    buildUponDefaultConfig = true // preconfigure defaults
    baseline = file("${rootProject.projectDir}/config/baseline.xml")

    reports {
        html {
            enabled = true
            destination = file("path/to/destination.html")
        }
    }
}

pitest {
    targetClasses.set(listOf("com.github.antoinecheron"))
    mutationEngine.set("descartes")
    pitestVersion.set("1.4.7")
}
