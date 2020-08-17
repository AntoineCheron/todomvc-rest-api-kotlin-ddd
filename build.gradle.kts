import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "com.github.antoinecheron"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

val coroutinesVersion = "1.3.72"
val springVersion = "2.3.3.RELEASE"

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


    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.boot.experimental:spring-boot-test-autoconfigure-r2dbc:0.1.0.M2")
    testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot.experimental:spring-boot-bom-r2dbc:0.1.0.M2")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}