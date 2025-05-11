plugins {
    kotlin("jvm") version "2.1.20"
    java
    id("io.quarkus")
    jacoco
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId = project.findProperty("quarkusPlatformGroupId") as? String ?: "io.quarkus.platform"
val quarkusPlatformArtifactId = project.findProperty("quarkusPlatformArtifactId") as? String ?: "quarkus-bom"
val quarkusPlatformVersion =
    project.findProperty("quarkusPlatformVersion") as? String ?: "0.0.0-SnapShot" // versão exemplo

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-opentelemetry")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-arc")

    implementation("io.quarkus:quarkus-messaging-kafka")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation("io.strikt:strikt-core:0.34.0")
    testImplementation("io.quarkus:quarkus-jdbc-h2")
    testImplementation("io.quarkus:quarkus-flyway")
    testImplementation("io.smallrye.reactive:smallrye-reactive-messaging-in-memory")

}

group = "br.com.fiap"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    systemProperty("quarkus.test.profile", "test")
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }
    sourceDirectories.setFrom(files("src/main/kotlin"))
    classDirectories.setFrom(fileTree("build/classes/main/kotlin"))
    executionData.setFrom(file("$buildDir/jacoco/test.exec"))
}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

