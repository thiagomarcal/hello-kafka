/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 */

plugins {

    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("org.jetbrains.kotlin.kapt") version "1.4.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.4.10"
    id("io.micronaut.application") version "1.2.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"

    // Apply the application plugin to add support for building a CLI application.
//    application
}

version = "0.2"
group = "hello.kafka"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.grpc:micronaut-grpc-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.kafka:micronaut-kafka")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("io.micronaut:micronaut-http-client")
    testRuntimeOnly("org.testcontainers:postgresql")
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("hello.kafka.*")
    }
}


application {
    // Define the main class for the application.
    mainClassName = "hello.kafka.AppKt"
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}